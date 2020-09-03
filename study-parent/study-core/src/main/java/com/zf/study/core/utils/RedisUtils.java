package com.zf.study.core.utils;

import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {

    @Resource
    private JedisPool jedisPool;

    /**
     * 基础命令(例如:删除Key,设置过期时间等)
     */
    @Resource
    protected RedisTemplate<String, Object> redisTemplate;



    /***************jedis方式*****************/
    /**
     * 字符串
     * @param key
     * @param value
     * @return
     */
    public String  set(String key,String value){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.set(key, value);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public String get(String key){
        Jedis jedis = jedisPool.getResource();
        try {
           return jedis.get(key);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * Hash
     * @param key
     * @param filed
     * @param value
     * @return
     */
    public Long hset(String key,String filed,String value){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.hset(key,filed,value);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    public String hget(String key,String field){
        Jedis jedis = jedisPool.getResource();
        try {
           return jedis.hget(key,field);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * List
     * @return
     */
    /**
     * 在列表最左边(列表头)添加元素
     * @return
     */
    public Long lpush(String key,String ... values){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpush(key,values);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 返回并删除列表头部元素
     * @param key
     * @return
     */
    public String lpop(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lpop(key);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 在列表最右边(队尾)插入元素
     * @param key
     * @param values
     * @return
     */
    public Long rpush(String key,String ... values){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpush(key,values);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 返回并删除列表尾部元素
     * @param key
     * @return
     */
    public String rpop(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.rpop(key);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 返回列表中[start,end]中的元素
     * @param key
     * @param start
     * @param end
     * @return
     */
    public  List<String> lrange(String key,long start,long end){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.lrange(key, start, end);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * Set集合中添加元素
     * @param key
     * @param members
     * @return
     */
    public Long sadd(String key,String ... members){
        Jedis jedis = jedisPool.getResource();
        try {
           return jedis.sadd(key,members);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * Sorted Set
     * redis正是通过分数来为集合中的成员进行从小到大的排序。
     * @param key
     * @param score 分数
     * @param value
     * @return
     */
    public Long zadd(String key,double score,String value){
        Jedis jedis = jedisPool.getResource();
        try {
          return  jedis.zadd(key,score,value);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 原子操作 增1
     * @param key
     * @return
     */
    public Long incr(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incr(key);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 以increment的步长原子递增
     * @param key
     * @param increment
     * @return
     */
    public Long incrBy(String key,long increment){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.incrBy(key,increment);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 原子递减1
     * @param key
     * @return
     */
    public Long decr(String key){
        Jedis jedis = jedisPool.getResource();
        try {
            return jedis.decr(key);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 以decrement的步长原子递减
     * @param key
     * @param decrement
     * @return
     */
    public Long decrBy(String key,long decrement){
        Jedis jedis = jedisPool.getResource();
        try {
           return jedis.decrBy(key,decrement);
        } finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }
    /********************redisTemplate*************************/

    /**
     * 字符串操作
     *
     */

    public void set2(String key,String value){
        redisTemplate.opsForValue().set(key,value);
    }

    /**
     *
     * @param key
     * @param value
     * @param timeout 过期时间  (秒)
     */
    public void set2(String key,Object value,long timeout){
        redisTemplate.opsForValue().set(key,value,timeout, TimeUnit.SECONDS);
    }


    public Object get2(String key){
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * 操作对象
     * @param key
     * @param value
     */
    public void setObject(String key,Object value,long expireTime){
        redisTemplate.opsForValue().set(key,value,expireTime,TimeUnit.SECONDS);
    }

    public Object getObject(String key){
        return redisTemplate.opsForValue().get(key);
    }


    /**
     * Hash
     */

    public void hset2(String key,String field,String value){
        redisTemplate.opsForHash().put(key,field,value);
    }

    public String hget2(String key,String field){
        return (String) redisTemplate.opsForHash().get(key,field);
    }

    /**
     * 原子递增
     * @param key
     * @param increment
     * @return
     */
    public Long increment(String key,long increment){
        return redisTemplate.opsForValue().increment(key,increment);
    }

    /**
     * 原子递减
     * @param key
     * @param decrement
     * @return
     */
    public Long decrement(String key,long decrement){
        return redisTemplate.opsForValue().decrement(key,decrement);
    }

    /**
     * 当key不存在时才能放成功
     * @param key
     * @param value
     * @param expireTime 过期时间
     * @return
     */
    public Boolean setIfAbsent(String key,Object value,long expireTime){
       return redisTemplate.opsForValue().setIfAbsent(key,value,expireTime,TimeUnit.SECONDS);
    }

}
