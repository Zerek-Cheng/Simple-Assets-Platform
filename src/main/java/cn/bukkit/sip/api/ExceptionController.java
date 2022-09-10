package cn.bukkit.sip.api;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.exception.WebException;
import cn.bukkit.sip.pojo.RestData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice(annotations = {Controller.class})
public class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public String exception(WebException e) {
        e.printStackTrace();
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseBody
    public RestData exception(RestException e) {
        e.printStackTrace();
        return RestData.builder().code(e.getCode()).message(e.getMessage()).data(e.getData()).build();
    }

    @ExceptionHandler
    @ResponseBody
    public RestData exception(Exception e) {
        e.printStackTrace();
        return RestData.builder().message(e.getLocalizedMessage()).build();
    }
}
