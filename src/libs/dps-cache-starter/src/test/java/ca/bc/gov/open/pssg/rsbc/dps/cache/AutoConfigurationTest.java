package ca.bc.gov.open.pssg.rsbc.dps.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AutoConfigurationTest {

    @Mock
    private RedisProperties redisProperties;

    private CacheAutoConfiguration autoConfiguration;

    @BeforeAll
    public void init() throws Exception {
        MockitoAnnotations.initMocks(this);
        autoConfiguration = new CacheAutoConfiguration();
        redisProperties = Mockito.mock(RedisProperties.class);
    }

    @Test
    public void stand_alone_input_should_generate_jedisConnectionFactory() {
        Mockito.when(redisProperties.getHost()).thenReturn("127.0.0.1");
        Mockito.when(redisProperties.getPort()).thenReturn(6379);
        Mockito.when(redisProperties.getPassword()).thenReturn("password");
        JedisConnectionFactory jedisConnectionFactory = autoConfiguration.jedisConnectionFactory(redisProperties);
        Assertions.assertNotNull(jedisConnectionFactory);
    }

    @Test
    public void cluster_input_should_generate_jedisConnectionFactory() {
        RedisProperties.Cluster cluster = Mockito.mock(RedisProperties.Cluster.class);
        Mockito.when(redisProperties.getCluster()).thenReturn(cluster);
        Mockito.when(redisProperties.getPassword()).thenReturn("password");
        JedisConnectionFactory jedisConnectionFactory = autoConfiguration.jedisConnectionFactory(redisProperties);
        Assertions.assertNotNull(jedisConnectionFactory);
    }

    @Test
    public void sentinel_input_should_generate_jedisConnectionFactory() {
        RedisProperties.Sentinel sentinel = Mockito.mock(RedisProperties.Sentinel.class);
        Mockito.when(sentinel.getMaster()).thenReturn("master");
        Mockito.when(redisProperties.getSentinel()).thenReturn(sentinel);
        Mockito.when(redisProperties.getPassword()).thenReturn("password");
        JedisConnectionFactory jedisConnectionFactory = autoConfiguration.jedisConnectionFactory(redisProperties);
        Assertions.assertNotNull(jedisConnectionFactory);
    }

    @Test
    public void correct_input_should_generate_jedisConnectionFactory() {
        Mockito.when(redisProperties.getHost()).thenReturn("127.0.0.1");
        Mockito.when(redisProperties.getPort()).thenReturn(6379);
        Mockito.when(redisProperties.getPassword()).thenReturn("password");
        JedisConnectionFactory jedisConnectionFactory = autoConfiguration.jedisConnectionFactory(redisProperties);
        Assertions.assertNotNull(jedisConnectionFactory);
    }

    @Test
    public void correct_input_should_return_cacheManager() {
        JedisConnectionFactory jedisConnectionFactory = Mockito.mock(JedisConnectionFactory.class);
        CacheManager cacheManager = autoConfiguration.cacheManager(jedisConnectionFactory);
        Assertions.assertNotNull(cacheManager);
    }

}
