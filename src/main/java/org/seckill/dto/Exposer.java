package org.seckill.dto;

/**
 * 暴露秒杀地址DTO
 * Created by destiny on 2016/5/28.
 */
public class Exposer {

    /**
     * 秒杀是否开始
     */
    private boolean exposed;

    /**
     * 一种加密措施
     */
    private String md5;

    /**
     * 主键id
     */
    private long seckillId;

    /**
     * 系统当前时间(毫秒)
     */
    private long now;

    /**
     * 系统开启时间
     */
    private long start;

    /**
     * 系统结束时间
     */
    private long end;

    /**
     * 构造方法1
     *
     * @param exposed   是否开启
     * @param md5       MD5加密
     * @param seckillId 主键
     */
    public Exposer(boolean exposed, String md5, long seckillId) {
        this.exposed = exposed;
        this.md5 = md5;
        this.seckillId = seckillId;
    }

    /**
     * 构造方法
     *
     * @param exposed 是否开启
     * @param now     系统当前时间
     * @param start   开始时间
     * @param end     结束时间
     */
    public Exposer(boolean exposed,long seckillId, long now, long start, long end) {
        this.exposed = exposed;
        this.seckillId = seckillId;
        this.now = now;
        this.start = start;
        this.end = end;
    }


    /**
     * 构造方法
     * @param exposed 是否开启
     * @param seckillId 主键
     */
    public Exposer(boolean exposed, long seckillId) {
        this.exposed = exposed;
        this.seckillId = seckillId;
    }

    /**
     * 默认构造方法
     */
    public Exposer() {
    }

    public boolean isExposed() {
        return exposed;
    }

    public void setExposed(boolean exposed) {
        this.exposed = exposed;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getNow() {
        return now;
    }

    public void setNow(long now) {
        this.now = now;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "Exposer{" +
                "exposed=" + exposed +
                ", md5='" + md5 + '\'' +
                ", seckillId=" + seckillId +
                ", now=" + now +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
