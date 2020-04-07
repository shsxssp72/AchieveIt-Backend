package com.april.achieveit_common.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class RedisCacheUtility
{
    public static abstract class AbstractRedisCacheService
    {
        protected static Map<String,ReentrantLock> reentrantLocks=new HashMap<>()
        {{
            for(Method method: AbstractRedisCacheService.class.getDeclaredMethods())
            {
                put(method.getName(),
                    new ReentrantLock());
            }
        }};
    }

    public interface AbstractQuery<T>
    {
        T Query();
    }

    @AllArgsConstructor
    public static class RedisCacheHelper<T>
    {
        private RedisTemplate<String,String> redisTemplate;
        private ObjectMapper objectMapper;
        private ReentrantLock reentrantLock;
        private long cacheValidTime;
        private long concurrentWaitTime;

        @SuppressWarnings(value={"all"})
        public T QueryUsingCache(String redisKey,AbstractQuery<T> query)
        {
            T result;
            try
            {
                if(!redisTemplate.hasKey(redisKey))
                {
                    if(reentrantLock.tryLock())
                    {
                        try
                        {
                            result=query.Query();
                            redisTemplate.opsForValue()
                                    .set(redisKey,
                                         objectMapper.writeValueAsString(result),
                                         Duration.ofMillis(cacheValidTime));
                        }
                        finally
                        {
                            reentrantLock.unlock();
                        }
                    }
                    else
                    {
                        TimeUnit.MILLISECONDS.sleep(concurrentWaitTime);
                        return QueryUsingCache(redisKey,
                                               query);
                    }
                }
                else
                {
                    String cached_result=redisTemplate.opsForValue()
                            .get(redisKey);
                    Class<T> entityClass=(Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
                    result=objectMapper.readValue(cached_result,
                                                  entityClass);
                }
            }
            catch(Exception e)
            {
                result=query.Query();
            }
            return result;
        }
    }
}