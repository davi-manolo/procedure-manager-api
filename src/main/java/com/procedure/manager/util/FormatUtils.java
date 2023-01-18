package com.procedure.manager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FormatUtils {

    public static String convertToMonetaryValue(BigDecimal value) {
        DecimalFormatSymbols dfs = new DecimalFormatSymbols(Locale.US);
        dfs.setDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("R$ ###,##0.00");
        formatter.setDecimalFormatSymbols(dfs);
        return formatter.format(value);
    }

}
