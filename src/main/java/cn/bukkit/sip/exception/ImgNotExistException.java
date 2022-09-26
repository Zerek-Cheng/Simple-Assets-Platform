package cn.bukkit.sip.exception;

public class ImgNotExistException extends RestException {
    public ImgNotExistException() {
        this.setMessage("图片不存在");
    }
}
