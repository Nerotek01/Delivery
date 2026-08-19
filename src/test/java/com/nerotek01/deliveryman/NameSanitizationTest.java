package com.nerotek01.deliveryman;

import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class NameSanitizationTest {

    private static final Pattern SAFE_NAME = Pattern.compile("[^A-Za-z0-9_]");

    private String sanitizeName(String name) {
        if (name == null) return "";
        String cleaned = SAFE_NAME.matcher(name).replaceAll("");
        return cleaned.isEmpty() ? name : cleaned;
    }

    @Test
    void alphanumericNameIsPreserved() {
        assertEquals("Steve", sanitizeName("Steve"));
        assertEquals("Player_01", sanitizeName("Player_01"));
    }

    @Test
    void nameWithSpaceIsStripped() {
        String result = sanitizeName("foo bar");
        assertFalse(result.contains(" "));
        assertEquals("foobar", result);
    }

    @Test
    void nameWithSemicolonIsStripped() {
        String result = sanitizeName("user;say");
        assertFalse(result.contains(";"));
        assertEquals("usersay", result);
    }

    @Test
    void nameWithPipeIsStripped() {
        String result = sanitizeName("a|b");
        assertFalse(result.contains("|"));
        assertEquals("ab", result);
    }

    @Test
    void nullNameReturnsEmpty() {
        assertEquals("", sanitizeName(null));
    }

    @Test
    void allSpecialCharNameFallsBackToOriginal() {
        String original = "!!!";
        assertEquals(original, sanitizeName(original));
    }

    @Test
    void patternBehaviorMatchesExpectation() {
        assertTrue(SAFE_NAME.matcher("a b").find());
        assertTrue(SAFE_NAME.matcher("a;b").find());
        assertFalse(SAFE_NAME.matcher("abc").find());
    }
}
