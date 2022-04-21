package com.mo.timall.Utils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value=Exception.class)
    public String defaultErrorHandler(Model model, Exception e)throws Exception{
        String errorMessage=(e!=null?e.getMessage():"Unknown error");
        model.addAttribute("errorMessage",errorMessage);
        return "errorPage";
    }
}
