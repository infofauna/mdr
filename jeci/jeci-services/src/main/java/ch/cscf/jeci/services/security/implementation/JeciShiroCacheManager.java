package ch.cscf.jeci.services.security.implementation;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by henryp on 08/04/15.
 */
@Service
public class JeciShiroCacheManager implements CacheManager {

    private Map<String, Cache> caches = new HashMap<>();

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        Cache cache = caches.get(name);

        if(cache == null){
            cache = new GuavaShiroCache<K,V>();
            caches.put(name, cache);
        }

        return cache;
    }

}
