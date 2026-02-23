package io.munib.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {
    public static long toMinorUnits(BigDecimal amount) {
        int scale = 2;
        if (amount == null) throw new IllegalArgumentException("Amount cannot be null");

        BigDecimal minor = amount.multiply(BigDecimal.TEN.pow(scale));

        minor = minor.setScale(0, RoundingMode.HALF_UP);

        return minor.longValueExact();
    }

    public static BigDecimal toMajorUnits(long minorUnits) {
        int scale = 2;
        return BigDecimal.valueOf(minorUnits)
                .divide(BigDecimal.TEN.pow(scale), scale, RoundingMode.HALF_UP);
    }

}
