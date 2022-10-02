package cn.bukkit.sip.exception;

public class StrongeNotExistException extends RestException {
    public StrongeNotExistException() {
        this.setMessage("存储器不存在");
    }
}
