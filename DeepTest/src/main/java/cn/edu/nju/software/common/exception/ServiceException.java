package cn.edu.nju.software.common.exception;

/**
 * Created by mengf on 2018/4/6 0006.
 */
public class ServiceException extends RuntimeException {

    private Integer code;

    public ServiceException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public ServiceException(String message) {
        super(message);
        this.code = -1;
    }


    public ServiceException(ExceptionEnum exceptionEnum){
        this(exceptionEnum.getMsg(),exceptionEnum.getCode());
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
