package com.common.exception.handler;

import com.common.exception.CustomException;
import com.enums.Result;
import com.enums.ResultCode;
import com.model.Email;
import com.service.IMailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;

/**
 * 全局异常处理器
 * @author pyy
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Autowired
    private IMailService mailService;

    @Value("${alarm.email}")
    private String[] email;

    /**
     * 处理自定义异常
     */
	@ExceptionHandler(CustomException.class)
	public Result handleException(CustomException e) {
        // 打印异常信息
        log.error("### 异常信息:{} ###", e.getMessage());
        return new Result(e.getRresultCode());
	}

    /**
     * 参数错误异常
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    public Result handleException(Exception e) {

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;
            BindingResult result = validException.getBindingResult();
            StringBuffer errorMsg = new StringBuffer();
            if (result.hasErrors()) {
                List<ObjectError> errors = result.getAllErrors();
                errors.forEach(p ->{
                    FieldError fieldError = (FieldError) p;
                    errorMsg.append(fieldError.getDefaultMessage()).append(",");
                    log.error("### 请求参数错误：{"+fieldError.getObjectName()+"},field{"+fieldError.getField()+ "},errorMessage{"+fieldError.getDefaultMessage()+"}"); });
            }
        } else if (e instanceof BindException) {
            BindException bindException = (BindException)e;
            if (bindException.hasErrors()) {
                log.error("### 请求参数错误: {}", bindException.getAllErrors());
            }
        }

        return new Result(ResultCode.PARAM_IS_INVALID);
    }

    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleOtherException(Exception e){
        //打印异常堆栈信息
        e.printStackTrace();
        // 打印异常信息
        log.error("### 不可知的异常:{} ###", e.getMessage());


        //产生异常，发送邮件服务
        StringWriter stringWriter = new StringWriter();
        e.printStackTrace(new PrintWriter(stringWriter));
        Email mail = new Email();
        mail.setEmail(email);
        mail.setSubject("异常告警邮件通知");
        mail.setContent(stringWriter.toString());
        // mailService.send(mail);//发送普通邮件
        mail.setTemplate("notifyEmail.ftl");
        HashMap<String, Object> mapParam = new HashMap<>(); //自定义模板参数，用于在ftl中接收展示
        mapParam.put("exceptionCause", e.getCause());
        mapParam.put("exceptionMessage", e.getMessage());
        mapParam.put("exceptionClass", e.getClass());
        mail.setKvMap(mapParam);
        mailService.sendFreemarker(mail);//发送模板邮件

        return new Result(ResultCode.SYSTEM_INNER_ERROR);
    }

}
