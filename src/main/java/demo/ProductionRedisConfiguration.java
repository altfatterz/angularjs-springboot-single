package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

import java.net.URI;
import java.net.URISyntaxException;

@Profile("production")
@Configuration
public class ProductionRedisConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductionRedisConfiguration.class);
    @Bean
    public JedisConnectionFactory connectionFactory() throws URISyntaxException {
        JedisConnectionFactory redis = new JedisConnectionFactory();
        String redisUrl = System.getenv("REDISCLOUD_URL");

        LOGGER.info("REDISCLOUD_URL:" + redisUrl);

        URI redisUri = new URI(redisUrl);

        LOGGER.info("hostname:" + redisUri.getHost());
        LOGGER.info("port:" + redisUri.getPort());
        LOGGER.info("userinfo:" + redisUri.getUserInfo());

        redis.setHostName(redisUri.getHost());
        redis.setPort(redisUri.getPort());
        redis.setPassword(redisUri.getUserInfo().split(":",2)[1]);
        return redis;
    }

    static class Initializer  extends AbstractHttpSessionApplicationInitializer {
        public Initializer() {
            super(ProductionRedisConfiguration.class);
        }
    }

}
