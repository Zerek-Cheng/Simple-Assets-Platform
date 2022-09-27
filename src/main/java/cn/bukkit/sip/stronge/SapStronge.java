package cn.bukkit.sip.stronge;

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
     */
    void saveFile(String path, byte[] data);

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
     */
    void deleteFile(String path);
}
