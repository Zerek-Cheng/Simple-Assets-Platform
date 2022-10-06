package cn.bukkit.sip.exception;

public class StorageNotExistException extends RestException {
    public StorageNotExistException() {
        this.setMessage("存储器不存在");
    }
}
