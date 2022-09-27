package cn.bukkit.sip.stronge;

public interface NetStronge extends SapStronge {
    /**
     * 获取接口网关地址
     *
     * @return 接口网关地址
     */
    String getGateway();

    /**
     * 获取http客户端
     *
     * @return http客户端
     */
    Object getHttpClient();
}
