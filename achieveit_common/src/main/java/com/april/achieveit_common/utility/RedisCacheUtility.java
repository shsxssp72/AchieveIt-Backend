package com.april.achieveit_common.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.Method;
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
        private static Logger logger=LoggerFactory.getLogger(RedisCacheHelper.class);
        private final RedisTemplate<String,String> redisTemplate;
        private final ObjectMapper objectMapper;
        private final ReentrantLock reentrantLock;
        private final long cacheValidTime;
        private final long concurrentWaitTime;

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
                    result=objectMapper.readValue(cached_result,
                                                  new TypeReference<T>()
                                                  {
                                                  });
                    logger.debug("Using cache.");
                }
            }
            catch(Exception e)
            {
                logger.warn("Cache penetrated. Cause: "+e.getMessage());
                result=query.Query();
            }
            return result;
        }
    }
}