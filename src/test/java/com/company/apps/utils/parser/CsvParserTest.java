package com.company.apps.utils.parser;

import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;
import org.mockito.Mock;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.apache.commons.csv.CSVRecord;
import com.company.apps.object.Mocks;

import java.time.LocalDate;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class CsvParserTest {

    @Mock
    private CSVRecord record;

    @Test
    void parseIntOrDefaultParserTest() {
        Mockito.when(record.get("ValidInteger")).thenReturn(String.valueOf(Mocks.WEIGHT));
        Mockito.when(record.get("InvalidInteger")).thenReturn("notAnInteger");

        Assertions.assertEquals(Mocks.WEIGHT, CsvParser.parseIntOrDefault("ValidInteger", 0, record));
        Assertions.assertEquals(Mocks.WEIGHT, CsvParser.parseIntOrDefault("InvalidInteger", Mocks.WEIGHT, record));
    }

    @Test
    void parseStringOrDefaultParserTest() {
        Mockito.when(record.get("NotEmptyString")).thenReturn(Mocks.NAME_FIRST);
        Mockito.when(record.get("EmptyString")).thenReturn("");

        Assertions.assertEquals(Mocks.NAME_FIRST, CsvParser.parseStringOrDefault("NotEmptyString", "Default", record));
        Assertions.assertEquals(Mocks.NAME_LAST, CsvParser.parseStringOrDefault("EmptyString", Mocks.NAME_LAST, record));
    }

    @Test
    void parseDateOrDefaultParserTest() {
        Mockito.when(record.get("ValidDate")).thenReturn("1960-01-01");
        Mockito.when(record.get("InvalidDate")).thenReturn("20200101");

        Assertions.assertEquals(Mocks.BIRTH_DATE, CsvParser.parseDateOrDefault("ValidDate", LocalDate.MIN, record, "yyyy-MM-dd"));
        Assertions.assertEquals(Mocks.CAREER_DEBUT, CsvParser.parseDateOrDefault("InvalidDate", Mocks.CAREER_DEBUT, record, "yyyy-MM-dd"));
    }

    @Test
    void checkIDFieldsParserTest() {
        Mockito.when(record.get("ExistingID")).thenReturn(Mocks.PLAYER_ID);
        Mockito.when(record.get("NonExistingID")).thenThrow(IllegalArgumentException.class);

        Assertions.assertEquals(Mocks.PLAYER_ID, CsvParser.checkIDFields("ExistingID", record));

        String generatedID = CsvParser.checkIDFields("NonExistingID", record);
        Assertions.assertDoesNotThrow(() -> UUID.fromString(generatedID));
    }
}