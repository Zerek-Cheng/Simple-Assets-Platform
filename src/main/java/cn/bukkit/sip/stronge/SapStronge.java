package cn.bukkit.sip.stronge;

import cn.bukkit.sip.config.StrongeConfig;

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
     * @return 如果 isOrigin() 为 false 则返回 byte[]，否则返回外部地址
     */
    Object getFile(String path);

    /**
     * 删除文件
     *
     * @param path 文件路径
     * @return 是否删除成功
     */
    boolean deleteFile(String path);

    /**
     * 是否为外部存储器
     */
    default boolean isOrigin() {
        return false;
    }

    /**
     * 传入StrongeConfig初始化存储器
     */
    default void init(String name, StrongeConfig config) {
    }
}
