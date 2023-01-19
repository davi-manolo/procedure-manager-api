package com.procedure.manager.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
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

    public static String convertNumberToMonth(int numberMonth) {
        String month = DateFormatSymbols.getInstance(new Locale("pt", "BR")).getMonths()[numberMonth -1];
        return StringUtils.capitalize(month);
    }

}
