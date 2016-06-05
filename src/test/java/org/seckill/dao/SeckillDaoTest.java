package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.Seckill;
import org.springframework.test.annotation.SystemProfileValueSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by destiny on 2016/5/27.
 * <p>
 * 配置Spring和Junit整合，junit启动时加载SpringIoC容器
 * <p>
 * spring-test,junit
 */
// 告诉Junit，Spring配置文件，在加载的时候读取spring/spring-dao.xml的内容，
// 完成测试类中spring和MyBatis的整合
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {
    //注入Dao实现类依赖
    //再次之前Dao的实现类已经由MyBatis初始化完成并放在了Spring容器中

    //虽然报错，但是可以直接使用
    @Resource
    private SeckillDao seckillDao;

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }


    @Test
    public void testQueryAll() throws Exception {
        //Java没有保存形参的记录
        List<Seckill> seckillList = seckillDao.queryAll(0, 100);
        for (Seckill seckill: seckillList) {
            System.out.println(seckill);
        }
    }


    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(1000L, killTime);
        System.out.println("updateCount="+updateCount);
    }
}
