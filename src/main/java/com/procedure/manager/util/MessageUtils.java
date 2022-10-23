package com.procedure.manager.util;

import com.procedure.manager.domain.enumeration.ExceptionMessage;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageUtils {

    private final MessageSource messageSource;

    public MessageUtils(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMessage(ExceptionMessage message, Locale locale, String... params) {
        return messageSource.getMessage(message.toString(), params, locale);
    }

    public String getMessage(ExceptionMessage message, String... params) {
        return this.getMessage(message, LocaleContextHolder.getLocale(), params);
    }

}
