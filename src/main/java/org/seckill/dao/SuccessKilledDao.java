package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuccessKilled;
import org.springframework.stereotype.Repository;

import java.util.Date;

/**
 * Created by destiny on 2016/5/26.
 */
@Repository
public interface SuccessKilledDao {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的结果集行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 仅供测试
     *
     * @param seckillId
     * @param userPhone
     * @param createTime
     * @return
     */
    @Deprecated
    int insertSuccessKilledWithTime(@Param("seckillId") long seckillId,
                                    @Param("userPhone") long userPhone,
                                    @Param("createTime") Date createTime);

    /**
     * 根据id查询successKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);
}
