package io.munib.service.impl;

import io.munib.logging.LogProperties;
import io.munib.service.LoggerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoggerServiceImpl implements LoggerService {

    private final WebClient webClient;
    private final LogProperties props;

    @Override
    public void log(String message) {
        try {
            webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path(props.path())
                            .queryParam("sleep", props.sleepMs())
                            .build())
                    .retrieve()
                    .onStatus(HttpStatusCode::isError,
                            resp -> Mono.error(new IllegalStateException("External logging failed")))
                    .toBodilessEntity()
                    .timeout(Duration.ofMillis(props.timeoutMs()))
                    .block();
        } catch (Exception e) {
            log.error("External logging call failed", e);
        }
    }
}