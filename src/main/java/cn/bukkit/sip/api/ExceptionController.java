package cn.bukkit.sip.api;

import cn.bukkit.sip.exception.RestException;
import cn.bukkit.sip.exception.WebException;
import cn.bukkit.sip.pojo.RestData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;

import javax.validation.ValidationException;
import java.io.PrintWriter;
import java.io.StringWriter;

@Slf4j
@ControllerAdvice(annotations = {Controller.class, RestController.class})
public class ExceptionController {

    @ExceptionHandler
    @ResponseBody
    public String exception(WebException e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        log.debug(sw.toString());
        return e.getMessage();
    }

    @ExceptionHandler
    @ResponseBody
    public RestData exception(RestException e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        log.debug(sw.toString());
        return RestData.builder().code(e.getCode()).message(e.getMessage()).data(e.getData()).build();
    }

    @ExceptionHandler
    @ResponseBody
    public RestData ValidationException(ValidationException e) {
        return this.exception(RestException.builder().code(-1).message(e.getMessage()).build());
    }

    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    public RestData MultipartException(MultipartException e) {
        e.printStackTrace();
        return RestData.builder().code(-1).message(e.getLocalizedMessage()).build();
    }

    @ExceptionHandler
    @ResponseBody
    public RestData exception(Exception e) {
        e.printStackTrace();
        return RestData.builder().code(-1).message(e.getLocalizedMessage()).build();
    }
}
