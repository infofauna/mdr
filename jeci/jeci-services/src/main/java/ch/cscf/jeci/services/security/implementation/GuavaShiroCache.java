package ch.cscf.jeci.services.security.implementation;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Shiro cache implementation.
 * Simple wrapper/adapter around an instance of Guava's Cache.
 */
public class GuavaShiroCache<K,V> implements org.apache.shiro.cache.Cache<K,V> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Cache<K,V> internalCache = CacheBuilder.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .initialCapacity(100)
            .maximumSize(1000)
            .build();

    @Override
    public V get(K key) throws CacheException {
        try {
            return internalCache.getIfPresent(key);
        }catch(Exception e){
            return null;
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V tmp = get(key);
        internalCache.put(key, value);
        return tmp;
    }

    @Override
    public V remove(K key) throws CacheException {
        V tmp = get(key);
        internalCache.invalidate(key);
        return tmp;
    }

    @Override
    public void clear() throws CacheException {
        internalCache.invalidateAll();
    }

    @Override
    public int size() {
        long size = internalCache.size();
        if(size > Integer.MAX_VALUE) {
            logger.warn("The size of the underlying Guava cache is bigger thant the Integer max value ! The returned value will be wrong.");
        }
        return (int) size;
    }

    @Override
    public Set<K> keys() {
        return internalCache.asMap().keySet();
    }

    @Override
    public Collection<V> values() {
        return internalCache.asMap().values();
    }
}
