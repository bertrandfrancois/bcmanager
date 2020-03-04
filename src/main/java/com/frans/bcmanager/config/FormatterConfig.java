package com.frans.bcmanager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
public class FormatterConfig {

//    @Bean
//    public Formatter<LocalDate> localDateFormatter() {
//        return new Formatter<>() {
//            @Override
//            public LocalDate parse(String text, Locale locale) {
//                return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//            }
//
//            @Override
//            public String print(LocalDate object, Locale locale) {
//                return DateTimeFormatter.ofPattern("dd/MM/yyyy").format(object);
//            }
//        };
//    }
}
