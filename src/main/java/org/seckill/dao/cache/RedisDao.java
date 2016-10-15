//package org.seckill.dao.cache;
//
//import com.dyuproject.protostuff.LinkedBuffer;
//import com.dyuproject.protostuff.ProtostuffIOUtil;
//import com.dyuproject.protostuff.runtime.RuntimeSchema;
//import org.seckill.entity.Seckill;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//
///**
// *
// * @author jesse
// */
//public class RedisDao {
//
//    private static final Logger LOG = LoggerFactory.getLogger(RedisDao.class.getName());
//
//    private final JedisPool jedisPool;
//    private RuntimeSchema<Seckill> schema = RuntimeSchema.createFrom(Seckill.class);
//
//    public RedisDao(String ip, int port) {
//        jedisPool = new JedisPool(ip, port);
//    }
//
//    public Seckill getSeckill(long seckillId) {
//        try {
//            Jedis jedis = jedisPool.getResource();
//            try {
//                String key = "seckill:" + seckillId;
//
//                //从字节数组到对象
//                byte[] bytes = jedis.get(key.getBytes());
//                if (bytes != null) {
//                    Seckill seckill = schema.newMessage();
//                    //反序列，赋值
//                    ProtostuffIOUtil.mergeFrom(bytes, seckill, schema);
//                    return seckill;
//                }
//
//            } finally {
//                jedis.close();
//            }
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//        }
//        return null;
//    }
//
//    public String putSeckill(Seckill seckill) {
//        try {
//            Jedis jedis = jedisPool.getResource();
//            try {
//                String key = "seckill:" + seckill.getSeckillId();
//                byte[] bytes = ProtostuffIOUtil.toByteArray(seckill, schema,
//                        LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
//                int timeout = 60 * 60; //一小时
//                String result = jedis.setex(key.getBytes(), timeout, bytes);
//                return result;
//            } finally{
//                jedis.close();
//            }
//        } catch (Exception e) {
//            LOG.error(e.getMessage(), e);
//        }
//        return null;
//    }
//}
