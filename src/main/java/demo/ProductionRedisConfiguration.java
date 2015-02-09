package demo;

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

    @Bean
    public JedisConnectionFactory connectionFactory() throws URISyntaxException {
        JedisConnectionFactory redis = new JedisConnectionFactory();
        String redisUrl = System.getenv("REDISCLOUD_URL");

        URI redisUri = new URI(redisUrl);
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
