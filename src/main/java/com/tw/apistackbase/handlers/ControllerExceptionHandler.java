package com.tw.apistackbase.handlers;

import com.tw.apistackbase.errors.CustomError;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public CustomError handleNotFoundException(NotFoundException e){
        CustomError customError = new CustomError();
        customError.setErrorCode(HttpStatus.NOT_FOUND.value());
        customError.setMessage(e.getMessage());

        return customError;
    }
}
