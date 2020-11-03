package ch.cscf.jeci.services.security.implementation;

import org.apache.shiro.cache.Cache;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.*;

public class TestGuavaShiroCache {

    private Cache<String, String> cache;

    @Before
    public void setupCache(){
        cache = new GuavaShiroCache<>();
    }


    @Test
    public void testGetNull() throws Exception {
        String val = cache.get("key");
        assertThat(val, is(nullValue()));
    }

    @Test
    public void testGetValue() throws Exception {
        cache.put("key", "value");
        String val = cache.get("key");
        assertThat(val, is("value"));
    }


    @Test
    public void testPutNoPreviousValue() throws Exception {
        String prev = cache.put("key", "value");
        assertThat(prev, is(nullValue()));
        assertThat(cache.get("key"), is("value"));
    }

    @Test
    public void testPutWithPreviousValue() throws Exception {
        cache.put("key", "toto");
        String prev = cache.put("key", "value");
        assertThat(prev, is("toto"));
        assertThat(cache.get("key"), is("value"));
    }


    @Test
    public void testRemoveNoPreviousValue() throws Exception {
        String prev = cache.remove("key");
        assertThat(prev, is(nullValue()));
        assertThat(cache.get("key"), is(nullValue()));
    }

    @Test
    public void testRemoveWithPreviousValue() throws Exception {
        cache.put("key", "value");
        String prev = cache.remove("key");
        assertThat(prev, is("value"));
        assertThat(cache.get("key"), is(nullValue()));
    }

    @Test
    public void testClear() throws Exception {
        cache.put("key", "value");
        cache.put("key2", "value2");
        cache.put("key3", "value3");
        cache.clear();
        assertThat(cache.get("key1"), is(nullValue()));
        assertThat(cache.get("key2"), is(nullValue()));
        assertThat(cache.get("key3"), is(nullValue()));
    }

    @Test
    public void testSize() throws Exception {
        assertThat(cache.size(), is(0));

        cache.put("key", "value");
        assertThat(cache.size(), is(1));

        cache.put("key", "valueother");
        assertThat(cache.size(), is(1));

        cache.put("key2", "value2");
        assertThat(cache.size(), is(2));

        cache.put("key3", "value3");
        assertThat(cache.size(), is(3));

        cache.remove("key2");
        assertThat(cache.size(), is(2));

        cache.clear();
        assertThat(cache.size(), is(0));

    }

    @Test
    public void testKeys() throws Exception {
        assertThat(cache.keys(), is(empty()));

        cache.put("key1", "value");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        assertThat(cache.keys(), contains("key1", "key2", "key3"));

        cache.clear();
        assertThat(cache.keys(), is(empty()));


    }

    @Test
    public void testValues() throws Exception {

        assertThat(cache.values(), is(empty()));

        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        assertThat(cache.values(), contains("value1", "value2", "value3"));

        cache.clear();
        assertThat(cache.values(), is(empty()));

    }
}