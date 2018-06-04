package cn.edu.nju.software.common.result;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by mengf on 2018/5/29 0029.
 */
public class Result {

    private boolean success = true;
    private Object data;
    private String errorCode = "";
    private String message = "";
    private String errorMessage = "";
    private List<String> errorMessageList;
    private Result(boolean success) {
        this.success = success;
    }

    public static Result success() {
        return new Result(true);
    }

    public static Result error() {
        return new Result(false);
    }

    public Result withData(Object data) {
        this.data = data;
        return this;
    }

    public Result message(String message) {
        this.message = message;
        return this;
    }

    public Result errorCode(String errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public Result errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Result errorMessageList(List<String> errorMessages) {
        this.errorMessageList = errorMessages;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<String> getErrorMessageList() {
        return errorMessageList;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", data=" + data +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + message + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorMessageList=" + errorMessageList +
                '}';
    }


}
