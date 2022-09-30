package cn.bukkit.sip;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ClassUtil;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;


@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
@EnableCaching(proxyTargetClass = true)
@EnableAsync(proxyTargetClass = true)
@EnableRedisHttpSession
@Slf4j
public class SimpleImgPlatformApplication implements EnvironmentPostProcessor {

    public static void main(String[] args) {
        SpringApplication.run(SimpleImgPlatformApplication.class, args);
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    Jackson2JsonRedisSerializer<Object> jsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.registerModules(new JavaTimeModule());
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 自定义cacheManager缓存管理器
     */
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory factory, Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ZERO)
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisSerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                .disableCachingNullValues();

        return RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .withInitialCacheConfigurations(getRedisCacheConfigurationMap(jsonRedisSerializer))
                .build();
    }

    /**
     * 自定义带缓存时间的cacheManager缓存管理器
     */
    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap(Jackson2JsonRedisSerializer<Object> jsonRedisSerializer) {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        List<Class<?>> clsList = ClassUtil.scanPackage("cn.bukkit.sip").stream().toList();
        if (CollUtil.isEmpty(clsList)) {
            return redisCacheConfigurationMap;
        }
        clsList.forEach(cls -> {
            Method[] methods = cls.getDeclaredMethods();
            for (Method method : methods) {
                Cacheable cacheable = method.getAnnotation(Cacheable.class);
                if (Objects.isNull(cacheable)) {
                    continue;
                }
                String[] split = cacheable.key().replace("'", "").split("/");
                if (split.length >= 2) {
                    Arrays.stream(cacheable.value()).forEach(v -> redisCacheConfigurationMap.put(v,
                            RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
                                            RedisSerializationContext.SerializationPair.fromSerializer(jsonRedisSerializer))
                                    .entryTtl(Duration.ofSeconds(Integer.parseInt(split[1]))))
                    );
                }
            }
        });

        return redisCacheConfigurationMap;
    }

    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return interceptor;
    }

    /**
     * 动态加载配置文件
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        System.out.println("SimpleImgPlatformApplication Environment PostProcessor");
        MutablePropertySources propertySources = environment.getPropertySources();
        System.getenv().forEach((k, v) -> {
            propertySources.addFirst(new MapPropertySource(k, Map.of(k, v)));
            if (Optional.ofNullable(environment.getProperty("spring.profiles.active")).orElse("").startsWith("dev"))
                System.out.printf("SimpleImgPlatformApplication.postProcessEnvironment: %s=%s\r\n", k, v);
        });
    }

    /**
     * 解决上传文件大小限制
     */
    @Bean
    public ServletWebServerFactory tomcatEmbedded() {
        TomcatServletWebServerFactory tomcat = new org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory();
        tomcat.addConnectorCustomizers(connector -> {
            if ((connector.getProtocolHandler() instanceof AbstractHttp11Protocol<?>)) {
                ((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
            }
        });
        return tomcat;

    }
}
