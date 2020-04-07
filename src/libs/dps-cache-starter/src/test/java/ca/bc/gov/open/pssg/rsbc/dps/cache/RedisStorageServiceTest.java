package ca.bc.gov.open.pssg.rsbc.dps.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.RedisConnectionFailureException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RedisStorageServiceTest {

    private static final byte[] VALID = "valid".getBytes();
    private static final byte[] EXCEPTION_INPUT = "exception".getBytes();
    private static final String KEY = "key";
    private static final String MISSING_DOCUMENT = "MISSING_DOCUMENT";
    private static final String REDIS_CONNECTION_FAILURE_EXCEPTION = "RedisConnectionFailureException";

    @Mock
    private CacheManager cacheManager;

    @Mock
    private Cache cache;

    @Mock
    private Cache.ValueWrapper valueWrapper;

    private RedisStorageService sut;

    @BeforeAll
    public void Init() {

        MockitoAnnotations.initMocks(this);
        Mockito.when(valueWrapper.get()).thenReturn(VALID);
        Mockito.when(cacheManager.getCache(Keys.DPS_CACHE_NAME)).thenReturn(this.cache);
        Mockito.when(cache.get(KEY)).thenReturn(valueWrapper);
        Mockito.when(cache.get(MISSING_DOCUMENT)).thenReturn(null);
        Mockito.doNothing().when(this.cache).put(Mockito.anyString(), Mockito.eq(VALID));
        Mockito.doThrow(RedisConnectionFailureException.class).when(this.cache).put(Mockito.anyString(), Mockito.eq(EXCEPTION_INPUT));
        Mockito.doThrow(RedisConnectionFailureException.class).when(this.cache).get(Mockito.eq(REDIS_CONNECTION_FAILURE_EXCEPTION));
        Mockito.doThrow(RedisConnectionFailureException.class).when(this.cache).evict(Mockito.eq(REDIS_CONNECTION_FAILURE_EXCEPTION));
        this.sut = new RedisStorageService(cacheManager);

    }


    @Test
    public void putWithValidContentShouldNotThrowException() throws Exception {
        Assertions.assertDoesNotThrow(() -> sut.put(VALID) );
    }

    @Test
    public void putWithRedisConnectionFailureExceptionShouldThrowDpsRedisException() throws Exception {
        Assertions.assertThrows(DpsRedisException.class, () -> sut.put(EXCEPTION_INPUT));
    }

    @Test
    public void getWithExistingKeyShouldGetBytes() {
        byte[] result = sut.get(KEY);
        Assertions.assertEquals(new String(VALID), new String(result));
    }

    @Test
    public void getWithNonExistingKeyShouldReturnNull() {
        Assertions.assertNull(sut.get(MISSING_DOCUMENT));
    }


    @Test
    public void getWithRedisConnectionFailureExceptionShouldThrowDpsRedisException() throws Exception {
        Assertions.assertThrows(DpsRedisException.class, () -> sut.get(REDIS_CONNECTION_FAILURE_EXCEPTION));
    }

}
