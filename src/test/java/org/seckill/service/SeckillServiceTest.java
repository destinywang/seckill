package org.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by destiny on 2016/5/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml", "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SeckillService seckillService;

    @Test
    public void testGetSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        //logger.info("list={}", seckillList);
        for (Seckill seckill : seckillList) {
            System.out.println(seckill);
        }

    }

    @Test
    public void testGetById() {
        long id = 1000L;
        Seckill seckill = seckillService.getById(id);
        logger.info("seckill={}", seckill);
//        System.out.println(seckill);
    }

    @Test
    public void testExportSeckillUrl() {
        long id = 1000;
        Exposer exposer = seckillService.exportSeckillUrl(id);
        System.out.println(exposer);
        logger.info("exposer={}", exposer);
        /**
         * Exposer{
         * exposed=true,
         * md5='bf8cf49ae0352c0d1481eaa8824146fa',
         * seckillId=1000,
         * now=0,
         * start=0,
         * end=0}
         */
    }

    @Test
    public void testExecuteSeckill() {
        long id = 1000;
        long phone = 22333333557L;
        String md5 = "bf8cf49ae0352c0d1481eaa8824146fa";
        SeckillExecution seckillExecution = seckillService.executeSeckill(id, phone, md5);

        System.out.println(seckillExecution);
    }
}
