package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuccessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by destiny on 2016/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled(){
        long id = 1000L;
        long userPhone = 13572595506L;
        int insertCount = successKilledDao.insertSuccessKilled(id, userPhone);
        System.out.println("insertCount="+insertCount);
    }

    @Test
    public void insertSuccessKilledWithTime() {
        long id = 1002L;
        long userPhone = 15596825802L;
        Date createTime = new Date();
        createTime.setTime(createTime.getTime() / (1000 * 3600 * 24) * (1000 * 3600 * 24) - 1000 * 60 * 60 * 8);
        System.out.println(successKilledDao.insertSuccessKilledWithTime(id, userPhone, createTime));
    }

    @Test
    public void testQueryByIdWithSeckill(){
        long id = 1000L;
        long userPhone = 13572595506L;
        SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(id, userPhone);
        System.out.println(successKilled);
        System.out.println(successKilled.getSeckill());
    }
}
