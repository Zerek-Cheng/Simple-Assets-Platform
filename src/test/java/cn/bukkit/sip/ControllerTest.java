package cn.bukkit.sip;

import cn.bukkit.sip.mock.user.WithSapMockUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.annotation.Resource;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SimpleAssetsPlatformApplication.class)
@WithSapMockUser
@AutoConfigureMockMvc
@WithMockUser
@Slf4j
public class ControllerTest {
    @Resource
    MockMvc mockMvc;
    @Resource
    ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void csrfToken() {
        ResultActions resultActions = mockMvc.perform(get("/test/csrf"))
                .andExpect(status().isOk());
        resultActions.andDo(result -> {
            String contentAsString = result.getResponse().getContentAsString();
            log.info(contentAsString);
            Assert.assertNotNull(contentAsString);
            JsonNode jsonNode = objectMapper.readTree(contentAsString);
            // read "data.token"
            Assert.assertNotNull(jsonNode.get("data").get("token"));
        });
        log.info("csrfToken test passed");
    }

    @SneakyThrows
    @Test
    public void mockUserTest() {
        ResultActions resultActions = mockMvc.perform(get("/test/user"))
                .andExpect(status().isOk());
        resultActions.andDo(result -> {
            String contentAsString = result.getResponse().getContentAsString();
            Assert.assertNotNull(contentAsString);
            JsonNode jsonNode = objectMapper.readTree(contentAsString);
            Assert.assertNotNull(UUID.fromString(jsonNode
                    .get("data")
                    .get("principal")
                    .get("id")
                    .asText()));
        });
        log.info("mockUserTest test passed");
    }

}
