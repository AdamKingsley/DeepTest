package cn.edu.nju.software.common.exception;

import cn.edu.nju.software.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by mengf on 2018/4/6 0006.
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandle {

    /**
     * 判断错误是否是已定义的已知错误，不是则由未知错误代替，同时记录在log中
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public Result exceptionGet(Exception e) {
        e.printStackTrace();

        //自定义异常 ServiceException
        if (e instanceof ServiceException) {
            ServiceException exception = (ServiceException) e;
            return Result.error().errorCode(exception.getCode().toString()).errorMessage(e.getMessage());
        }
         //other异常
        return Result.error().errorCode("-1").message("系统内部异常:" + e.getClass()).errorMessage(e.getMessage());
    }
}
