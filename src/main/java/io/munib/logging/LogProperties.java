package io.munib.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.external-log")
public record LogProperties(
        String baseUrl,
        String path,
        long sleepMs,
        long timeoutMs
) {
}