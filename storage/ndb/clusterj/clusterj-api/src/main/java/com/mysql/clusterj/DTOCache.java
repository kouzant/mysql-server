package com.mysql.clusterj;

import java.util.List;

/**
 * Created by antonis on 5/25/16.
 */
public interface DTOCache {
    void registerType(Class type, int cacheSize);
    void deregisterType(Class type);
    <T> boolean put(Class<T> type, T element);
    List<Class> getNotFullTypes();
}
