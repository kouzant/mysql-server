package com.mysql.clusterj.core.dtocache;

import com.mysql.clusterj.DTOCache;

/**
 * Created by antonis on 5/25/16.
 */
public interface DTOCacheSPI extends DTOCache {
    <T> T get(Class<T> type);
}
