package com.frans.bcmanager.service;

import java.util.Random;

import static java.lang.String.valueOf;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class StructuredCommunicationFactory {

    public static String create() {

        StringBuilder stringBuilder = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 10; i++) {
            stringBuilder.append(rand.nextInt(10));
        }
        long controlNumber = Long.parseLong(stringBuilder.toString()) % 97;
        if (controlNumber == 0) {
            stringBuilder.append("97");
        } else {
            stringBuilder.append(leftPad(valueOf(controlNumber), 2, "0"));
        }
        String structuredCommunication = stringBuilder.toString();
        if (structuredCommunication.length() != 12) {
            throw new UnsupportedOperationException(structuredCommunication + " is invalid");
        }
        return structuredCommunication;
    }
}
