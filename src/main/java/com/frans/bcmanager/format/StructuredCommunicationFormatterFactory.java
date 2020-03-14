package com.frans.bcmanager.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StructuredCommunicationFormatterFactory implements
                                                     AnnotationFormatterFactory<StructuredCommunication> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<>(Arrays.asList(String.class));
    }

    @Override
    public Printer<?> getPrinter(StructuredCommunication annotation, Class<?> fieldType) {
        return getStructuredCommunicationFormatter(annotation, fieldType);
    }

    @Override
    public Parser<?> getParser(StructuredCommunication annotation, Class<?> fieldType) {
        return getStructuredCommunicationFormatter(annotation, fieldType);
    }

    private StructuredCommunicationFormatter getStructuredCommunicationFormatter(StructuredCommunication annotation,
                                                                                 Class<?> fieldType) {
        StructuredCommunicationFormatter formatter = new StructuredCommunicationFormatter();
        return formatter;
    }
}
