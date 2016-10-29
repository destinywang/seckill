package org.seckill.service.impl;

import org.seckill.dao.SeckillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.exception.SeckillException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by destiny on 2016/5/28.
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    /**
     * slf4j提供的日志管理对象
     */
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 从Spring容器中获取DAO实例
     */
    @Autowired
    private SeckillDao seckillDao;

    /**
     * 从Spring容器中获取DAO实例
     */
    @Autowired
    private SuccessKilledDao successKilledDao;

    /**
     * MD5盐值字符串，用于混淆，越复杂越好
     */
    private final String slat = "312fd9#$R%^&ask6ld9hl98098c7a098fa22";

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }


    private String getMD5(long seckillId) {
        /**
         * 使用目标字符串+盐值字符串混合的方式去产生MD5字符串
         * 让恶意用户即使知道MD5解密算法，也无法准确拿到源字符串
         */
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());

        return md5;
    }

    /**
     * 返回秒杀信息
     *
     * @param seckillId
     * @return
     */
    public Exposer exportSeckillUrl(long seckillId) {
        // 优化点：缓存优化
        /**
         * get from cache
         * if null
         *      get from DB
         *      put cache
         * else
         *      get from cache
         * logic
         */
        Seckill seckill = seckillDao.queryById(seckillId);

        if (seckill == null) {
            return new Exposer(false, seckillId);
        }

        /**
         * 拿到开始时间、结束时间和当前时间
         */
        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();

        /**
         * 如果当前时间小于开始时间 或 当前时间大于结束时间
         */
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }

        /**
         * 转化特定字符串的过程，不可逆
         */
        String md5 = getMD5(seckillId);

        return new Exposer(true, md5, seckillId);
    }

    /**
     * 执行秒杀成功
     *
     * 使用注解控制事务方法的优点：
     * 1：开发团队达成一致约定，明确标注事务方法的编程风格
     * 2：保证事务方法的执行时间尽可能短，不要穿插其他的网络操作或者尽量剥离到事务方法之外
     * 3：不是所有的方法都需要事务，如只有一条修改的操作或者只读操作
     *
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SeckillException
     * @throws RepeatKillException
     * @throws SeckillCloseException
     */
    @Transactional
    public SeckillExecution executeSeckill(long seckillId, long userPhone, String md5)
            throws SeckillException, RepeatKillException, SeckillCloseException {

        /**
         * 当md5为null或与seckillId所计算的加密字符不相等时
         */
        if (md5 == null || !md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }
        /**
         * 执行秒杀逻辑：减库存，加购买行为
         */

        try {

            /*
            减库存
             */
            Date nowTime = new Date();
            int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
            if (updateCount <= 0) {
                /**
                 * 没有更新到记录，秒杀结束
                 */
                throw new SeckillCloseException("seckill is close");
            } else {
                /*
                记录购买行为
                 */
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                /*
                唯一：seckillId, userPhone(INSERT ignore)
                如果重复秒杀
                 */
                if (insertCount <= 0) {
                    throw new RepeatKillException("seckill repeat");
                } else {
                    SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    return new SeckillExecution(seckillId, SeckillStateEnum.SUCCESS, successKilled);
                }

            }
        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            /*
            所有编译器异常转化为运行期异常
             */
            throw new SeckillException("seckill inner error:" + e.getMessage());
        }
    }
}
