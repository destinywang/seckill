package org.seckill.web;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.dto.SeckillResult;
import org.seckill.entity.Seckill;
import org.seckill.enums.SeckillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SeckillCloseException;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by destiny on 2016/5/29.
 */
@Controller
@RequestMapping(value = "/seckill")//模块/资源/{id}/细分
public class SeckillController {

    /**
     * slf4j的日志对象
     */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 从Spring容器中获取SeckillService
     */
    @Autowired
    private SeckillService seckillService;


    /**
     * 二级资源名为list
     * 该方法必须以GET方法调用
     *
     * @param model 数据模型，建议返回类型为String，参数为model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        //获取列表页
        List<Seckill> list = seckillService.getSeckillList();

        model.addAttribute("list", list);
        //list.jsp + model = ModelAndView
        //真实路径为/WEB-INF/jsp/list.jsp
        return "list";
    }


    /**
     * 由展示页面跳转到秒杀商品详情页面的方法
     *
     * @param seckillId 秒杀物品id
     * @param model     模型对象
     * @return 试图相对路径
     */
    @RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model) {
        /**
         * 当id不存在时，将请求冲顶线到list，让他回到列表页面
         */
        if (seckillId == null) {
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null) {
            return "forward:/seckill/list";
        }
        model.addAttribute("seckill", seckill);
        //真实路径为/WEB-INF/jsp/detail.jsp
        return "detail";
    }

    //ajax接口
    @RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> expose(@PathVariable("seckillId") Long seckillId) {
        SeckillResult<Exposer> result;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SeckillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }


    /**
     *
     * @param seckillId 主键ID
     * @param md5 md5加密串
     * @param phone 用户电话
     * @return json数据
     */
    @RequestMapping(value = "/{seckillId}/{md5}/execution", method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   /**
                                                    * 考虑到如果cookie中如果没有killPhone会报错，设置为false
                                                    */
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {
        //Spring valid
        if (phone == null) {
            return new SeckillResult<SeckillExecution>(false, "未注册");
        }

        SeckillResult<SeckillExecution> result;

        try {
            SeckillExecution execution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true, execution);
        } catch (RepeatKillException e1) {
            /**
             * 如果捕获到秒杀重复异常
             */
            logger.error(e1.getMessage(), e1);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (SeckillCloseException e2) {
            /**
             * 如果捕获到秒杀关闭异常
             */
            logger.error(e2.getMessage(), e2);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.END);
            return new SeckillResult<SeckillExecution>(false, execution);
        } catch (Exception e3) {
            /**
             * 其他所有的异常情况
             */
            logger.error(e3.getMessage(), e3);
            SeckillExecution execution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
            return new SeckillResult<SeckillExecution>(false, execution);
        }
    }


    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date now = new Date();
        return new SeckillResult(true, now.getTime());
    }

}
