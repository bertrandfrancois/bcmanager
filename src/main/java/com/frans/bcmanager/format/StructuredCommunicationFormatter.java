package com.frans.bcmanager.format;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.util.Locale;

public class StructuredCommunicationFormatter implements Formatter<String> {

    @Override
    public String parse(String s, Locale locale) throws ParseException {
        return s.substring(3, 6) + s.substring(7, 11) + s.substring(12, 17);
    }

    @Override
    public String print(String s, Locale locale) {
        String part1 = s.substring(0, 3);
        String part2 = s.substring(3, 7);
        String part3 = s.substring(7, 12);

        return "+++" + part1 + "/" + part2 + "/" + part3 + "+++";
    }
}
