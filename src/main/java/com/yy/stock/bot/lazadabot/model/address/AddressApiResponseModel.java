/**
 * Copyright 2023 json.cn
 */
package com.yy.stock.bot.lazadabot.model.address;

/**
 * Auto-generated: 2023-02-14 12:38:42
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class AddressApiResponseModel<T> {

    private T module;
    private boolean success;
    private String errorCode;
    private boolean retry;
    private boolean repeated;
    private boolean notSuccess;

    public T getModule() {
        return this.module;
    }

    public void setModule(T getByPostCodeRespModule) {
        this.module = getByPostCodeRespModule;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public boolean getRetry() {
        return retry;
    }

    public void setRetry(boolean retry) {
        this.retry = retry;
    }

    public boolean getRepeated() {
        return repeated;
    }

    public void setRepeated(boolean repeated) {
        this.repeated = repeated;
    }

    public boolean getNotSuccess() {
        return notSuccess;
    }

    public void setNotSuccess(boolean notSuccess) {
        this.notSuccess = notSuccess;
    }

}