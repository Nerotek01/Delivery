package com.nerotek01.deliveryman;

import com.nerotek01.deliveryman.enums.DBType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DBTypeFallbackTest {

    @Test
    void knownTypesAreParsed() {
        assertEquals(DBType.MONGODB, DBType.valueOf("MONGODB"));
        assertEquals(DBType.MYSQL, DBType.valueOf("MYSQL"));
        assertEquals(DBType.SQL, DBType.valueOf("SQL"));
        assertEquals(DBType.FLATFILE, DBType.valueOf("FLATFILE"));
    }

    @Test
    void uppercaseNormalizationIsCallerResponsibility() {
        assertThrows(IllegalArgumentException.class, () -> DBType.valueOf("mongodb"));
    }

    @Test
    void unknownTypeThrows() {
        assertThrows(IllegalArgumentException.class, () -> DBType.valueOf("postgres"));
    }

    @Test
    void nullTypeThrowsNpe() {
        assertThrows(NullPointerException.class, () -> DBType.valueOf(null));
    }

    @Test
    void fallbackSimulatesConfigManagerBehavior() {
        DBType result = safeParseDbType("postgres");
        assertEquals(DBType.MONGODB, result);
    }

    @Test
    void fallbackOnEmptyString() {
        DBType result = safeParseDbType("");
        assertEquals(DBType.MONGODB, result);
    }

    @Test
    void fallbackOnNull() {
        DBType result = safeParseDbType(null);
        assertEquals(DBType.MONGODB, result);
    }

    @Test
    void validTypeIsPreserved() {
        assertEquals(DBType.MYSQL, safeParseDbType("MYSQL"));
        assertEquals(DBType.SQL, safeParseDbType("SQL"));
    }

    private DBType safeParseDbType(String raw) {
        if (raw == null) return DBType.MONGODB;
        try {
            return DBType.valueOf(raw.toUpperCase());
        } catch (IllegalArgumentException ex) {
            return DBType.MONGODB;
        }
    }
}
