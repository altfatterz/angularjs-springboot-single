package demo;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmbeddedMongoDBConfiguration {

    @Bean
    Mongo mongo() {
        return new Fongo("mongo").getMongo();
    }

}
