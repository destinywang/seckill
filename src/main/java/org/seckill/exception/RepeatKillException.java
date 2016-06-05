package org.seckill.exception;

/**
 * 重复秒杀异常（运行期异常）
 * Spring声明式事务管理，只接收运行期异常的回滚策略
 * 不会回滚非运行期异常
 * Created by destiny on 2016/5/28.
 */
public class RepeatKillException extends SeckillException{

    /**
     * 构造方法
     * @param message
     */
    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
