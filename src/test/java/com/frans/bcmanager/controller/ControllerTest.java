package com.frans.bcmanager.controller;

import com.gargoylesoftware.htmlunit.WebClient;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerTest {

    @Autowired
    protected WebClient webClient;

    @BeforeEach
    public void setUp() {
        Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
        //
        //        webClient.setIncorrectnessListener(new IncorrectnessListener() {
        //            @Override
        //            public void notify(String message, Object origin) {
        //
        //            }
        //        });
        //        webClient.setCssErrorHandler(new CSSErrorHandler() {
        //            @Override
        //            public void warning(CSSParseException exception) throws CSSException {
        //
        //            }
        //
        //            @Override
        //            public void error(CSSParseException exception) throws CSSException {
        //
        //            }
        //
        //            @Override
        //            public void fatalError(CSSParseException exception) throws CSSException {
        //
        //            }
        //        });
        //
        //        webClient.setJavaScriptErrorListener(new JavaScriptErrorListener() {
        //            @Override
        //            public void scriptException(HtmlPage page, ScriptException scriptException) {
        //
        //            }
        //
        //            @Override
        //            public void timeoutError(HtmlPage page, long allowedTime, long executionTime) {
        //
        //            }
        //
        //            @Override
        //            public void malformedScriptURL(HtmlPage page, String url, MalformedURLException malformedURLException) {
        //
        //            }
        //
        //            @Override
        //            public void loadScriptError(HtmlPage page, URL scriptUrl, Exception exception) {
        //
        //            }
        //        });
        //
        //        webClient.setHTMLParserListener(new HTMLParserListener() {
        //            @Override
        //            public void error(String message, URL url, String html, int line, int column, String key) {
        //
        //            }
        //
        //            @Override
        //            public void warning(String message, URL url, String html, int line, int column, String key) {
        //
        //            }
        //        });
        //    }
    }
}
