package org.seckill.dto;

/**
 * Created by destiny on 2016/5/29.
 */

/**
 * 所有的ajax请求返回类型，封装json结果
 * @param <T>
 */
public class SeckillResult<T> {

    /**
     * 是否成功
     */
    private boolean success;

    /**
     * 数据（成功时使用）
     */
    private T data;

    /**
     * 错误信息（失败时使用）
     */
    private String error;

    /**
     * 如果为真，需要数据
     * @param success
     * @param data
     */
    public SeckillResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    /**
     * 如果为假，需要错误信息
     * @param success
     * @param error
     */
    public SeckillResult(boolean success, String error) {
        this.success = success;
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
