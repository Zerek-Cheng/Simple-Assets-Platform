package cn.bukkit.sip.stronge;

import java.util.Map;

public interface SapStronge {
    /**
     * @return 存储器的名称
     */
    String getName();

    /**
     * 保存文件
     *
     * @param path 文件路径
     * @param data 文件数据
     * @return 文件对象
     */
    Object saveFile(String path, byte[] data);

    /**
     * 文件是否存在
     *
     * @param path 文件路径
     * @return 是否存在
     */
    boolean isExist(String path);

    /**
     * 获取文件
     *
     * @param path 文件路径
     * @return 文件数据
     */
    byte[] getFile(String path);

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 是否删除成功
     */
    boolean deleteFile(String path);

    /**
     * 获取存储配置
     *
     * @return 存储配置
     */
    Map<String, String> getConfig();
}
