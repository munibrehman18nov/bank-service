package io.munib.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;
import java.util.Map;

@ConfigurationProperties(prefix = "app")
public record FxRates(Map<String, BigDecimal> fx) {
}