package com.itau.test.util;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class CacheUtil {
    private final CacheManager cacheManager;
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheUtil.class);

    public CacheUtil(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    public <T> T getCachedResponse(String cacheKey, String id, Class<T> responseType) {
        Cache cache = cacheManager.getCache(cacheKey);
        if (cache != null) {
            String cachedJson = cache.get(id, String.class);
            if (cachedJson != null) {
                try {
                    return ObjectMapperUtil.objectMapper.readValue(cachedJson, responseType);
                } catch (JsonProcessingException e) {
                    LOGGER.warn("Error fetching from cache: {}. Error message: {}", id, e.getMessage());
                }
            }
        }
        return null;
    }

    public void cacheResponse(String cacheKey, String id, Object response) {
        Cache cache = cacheManager.getCache(cacheKey);
        if (cache != null) {
            try {
                String json = ObjectMapperUtil.objectMapper.writeValueAsString(response);
                cache.put(id, json);
            } catch (JsonProcessingException e) {
                LOGGER.warn("Error caching response: {}. Error message: {}", id, e.getMessage());
            }
        }
    }
}
