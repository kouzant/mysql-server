package com.mysql.clusterj.core.dtocache;

import com.mysql.clusterj.core.util.Logger;
import com.mysql.clusterj.core.util.LoggerFactoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by antonis on 5/25/16.
 */
public class DTOCacheImpl implements DTOCacheSPI {
    static final Logger logger = LoggerFactoryService.getFactory().getInstance(DTOCacheImpl.class);

    private final Map<Class, CacheEntry> cacheMap =
            new ConcurrentHashMap<Class, CacheEntry>();

    public DTOCacheImpl() {
    }

    public void registerType(Class type, int cacheSize) {
        if (!cacheMap.containsKey(type)) {
            cacheMap.put(type, new CacheEntry(cacheSize));
            //logger.info("Registered type: " + type.getName() + " to cache");
        } else {
            logger.info("Cache already contains type: " + type.getName());
        }
    }

    public void deregisterType(Class type) {
        if ((cacheMap.remove(type)) != null) {
            logger.info("Type: " + type.getName() + " removed from cache");
        } else {
            logger.info("Type: " + type.getName() + " was not registered to cache");
        }
    }

    public <T> boolean put(Class<T> type, T element) {
        CacheEntry cacheEntry = cacheMap.get(type);

        if (cacheEntry == null) {
            logger.info("Type: " + type.getName() + " is not registered to the cache");
            return false;
        }

        return cacheEntry.add(element);
    }

    public <T> T get(Class<T> type) {
        CacheEntry<T> cacheEntry = cacheMap.get(type);

        if (cacheEntry == null) {
            //logger.info("Type: " + type.getName() + " is not registered to the cache");
            return null;
        }

        return cacheEntry.get();
    }

    public List<Class> getNotFullTypes() {
        List<Class> notFullTypes =
                new ArrayList<Class>();
        for (Map.Entry<Class, CacheEntry> entry : cacheMap.entrySet()) {
            if (!entry.getValue().isFull()) {
                notFullTypes.add(entry.getKey());
            }
        }

        return notFullTypes;
    }
}
