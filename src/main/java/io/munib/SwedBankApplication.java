package io.munib;

import io.munib.config.FxRates;
import io.munib.logging.LogProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableConfigurationProperties({FxRates.class, LogProperties.class})
public class SwedBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwedBankApplication.class, args);
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
}
