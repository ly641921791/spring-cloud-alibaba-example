package nacos.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ly
 * @since 2019-04-11 11:28
 **/
@SpringBootApplication
public class ConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);
    }

    @RefreshScope
    @RestController
    static class DynamicConfig {

        @Value("${application.dynamic.config}")
        private String config;

        @GetMapping("/dynamic")
        public String dynamic(){
            return config;
        }

    }

}
