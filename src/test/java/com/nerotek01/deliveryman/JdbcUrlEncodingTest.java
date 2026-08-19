package com.nerotek01.deliveryman;

import org.junit.jupiter.api.Test;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JdbcUrlEncodingTest {

    private String buildJdbcUrl(String host, int port, String database, boolean useSSL) {
        String encodedHost = URLEncoder.encode(host, StandardCharsets.UTF_8);
        String encodedDatabase = URLEncoder.encode(database, StandardCharsets.UTF_8);
        return String.format("jdbc:mysql://%s:%d/%s?useSSL=%s&characterEncoding=utf8&autoReconnect=true&useUnicode=true",
                encodedHost, port, encodedDatabase, useSSL);
    }

    @Test
    void normalDatabaseNameIsMostlyPreserved() {
        String url = buildJdbcUrl("127.0.0.1", 3306, "DeliveryMan", false);
        assertTrue(url.contains("/DeliveryMan?useSSL=false"));
    }

    @Test
    void injectionAttemptInDatabaseNameIsNeutralized() {
        String malicious = "db?useSSL=false&allowLoadLocalInfile=true";
        String url = buildJdbcUrl("127.0.0.1", 3306, malicious, false);
        assertFalse(url.contains("allowLoadLocalInfile=true"));
        assertTrue(url.startsWith("jdbc:mysql://127.0.0.1:3306/"));
    }

    @Test
    void specialCharactersArePercentEncoded() {
        String url = buildJdbcUrl("127.0.0.1", 3306, "db?&=", false);
        assertTrue(url.contains("%3F"));
        assertTrue(url.contains("%26"));
        assertTrue(url.contains("%3D"));
    }

    @Test
    void hostIsAlsoEncoded() {
        String url = buildJdbcUrl("host/with/slash", 3306, "db", false);
        assertFalse(url.contains("host/with/slash"));
        assertTrue(url.contains("host%2Fwith%2Fslash"));
    }

    @Test
    void useSSLFlagIsReflected() {
        String url = buildJdbcUrl("127.0.0.1", 3306, "DeliveryMan", true);
        assertTrue(url.contains("useSSL=true"));
    }
}
