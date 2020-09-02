package com.zf.study.core.config;

import com.zf.study.core.utils.FstRedisSerializer;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class GlobalConfig {

	 /**
     * 线程池配置
     *
     * 
     */
    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();

        // 线程池维护线程的最少数量 默认1
        poolTaskExecutor.setCorePoolSize(80);

        // 线程池维护线程的最大数量
        poolTaskExecutor.setMaxPoolSize(100);

        // 线程池维护线程所允许的空闲时间 默认60
        poolTaskExecutor.setKeepAliveSeconds(60);

        // 线程池所使用的缓冲队列
        poolTaskExecutor.setQueueCapacity(200);

        // 线程池对拒绝任务（无线程可用）的处理策略，目前只支持AbortPolicy、CallerRunsPolicy；默认为后者
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        return poolTaskExecutor;
    }

    /**
     * Jedis pool
     */
    @Bean
    public JedisPool jedisPool(RedisProperties redisProperies) {
        return new JedisPool(this.poolConfig(), redisProperies.getHost(),
                redisProperies.getPort(), 2000, redisProperies.getPassword(), redisProperies.getDatabase());
    }

    /**
     * Load redis config information
     *
     * @return JedisPoolConfig
     */
    @Bean
    @ConfigurationProperties(prefix = "spring.redis")
    public JedisPoolConfig poolConfig() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();


        poolConfig.setMaxTotal(30);
        //maxIdle实际上是最大连接数
        poolConfig.setMaxIdle(30);

        //连接耗尽时是否阻塞, false报异常,ture阻塞直到超时, 默认true
        poolConfig.setBlockWhenExhausted(true);

        //设置的逐出策略类名, 默认DefaultEvictionPolicy(当连接超过最大空闲时间,或连接数超过最大空闲连接数)
        poolConfig.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");

        //是否启用pool的jmx管理功能, 默认true
        poolConfig.setJmxEnabled(true);

        //MBean ObjectName = new ObjectName("org.apache.commons.pool2:type=GenericObjectPool,name=" + "pool" + i); 默 认为"pool",.
        poolConfig.setJmxNamePrefix("pool");

        //是否启用后进先出, 默认true
        poolConfig.setLifo(true);

        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        poolConfig.setMaxWaitMillis(-1);

        //逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
        poolConfig.setMinEvictableIdleTimeMillis(1800000);

        //最小空闲连接数, 默认0
        poolConfig.setMinIdle(0);

        //每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        poolConfig.setNumTestsPerEvictionRun(3);

        //对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
        poolConfig.setSoftMinEvictableIdleTimeMillis(1800000);

        //在获取连接的时候检查有效性, 默认false
        poolConfig.setTestOnBorrow(false);

        //在空闲时检查有效性, 默认false
        poolConfig.setTestWhileIdle(false);

        //逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        poolConfig.setTimeBetweenEvictionRunsMillis(-1);

        return poolConfig;
    }

    /**
     * Jedis connection factory
     */
    @Bean(destroyMethod = "destroy")
    public JedisConnectionFactory connectionFactory(RedisProperties redisProperies) {
        JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder()
                .usePooling()
                .poolConfig(this.poolConfig())
                .and()
                .readTimeout(Duration.ofMillis(2000))
                .build();
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(redisProperies.getDatabase());
        redisStandaloneConfiguration.setPort(redisProperies.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperies.getPassword()));
        redisStandaloneConfiguration.setHostName(redisProperies.getHost());
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }

    /**
     * RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setEnableDefaultSerializer(false);
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new FstRedisSerializer());
        template.setHashValueSerializer(new FstRedisSerializer());
        return template;
    }
}
