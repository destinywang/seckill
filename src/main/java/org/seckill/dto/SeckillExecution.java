package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;

/**
 * 封装秒杀执行后的结果
 *
 * Created by destiny on 2016/5/28.
 */
public class SeckillExecution {

    /**
     * 主键
     */
    private long seckillId;

    /**
     * 状态
     */
    private int state;

    /**
     * 状态的标识
     */
    private String stateInfo;

    /**
     * 秒杀成功对象
     */
    private SuccessKilled successKilled;

    /**
     * 秒杀成功时，需要初始化所有的信息
     *
     * @param seckillId        主键
     * @param seckillStateEnum 状态枚举
     * @param successKilled    秒杀成功对象
     */
    public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
        this.successKilled = successKilled;
    }

    /**
     * 秒杀失败时，除了秒杀成功对象，其余都要初始化
     *
     * @param seckillId        主键
     * @param seckillStateEnum 状态枚举
     */
    public SeckillExecution(long seckillId, SeckillStateEnum seckillStateEnum) {
        this.seckillId = seckillId;
        this.state = seckillStateEnum.getState();
        this.stateInfo = seckillStateEnum.getStateInfo();
    }

    /**
     * 默认构造函数
     */
    public SeckillExecution() {
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public String toString() {
        return "SeckillExecution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
