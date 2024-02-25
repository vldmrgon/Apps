package com.company.apps.utils.parser;

import com.company.apps.exception.CsvProcessingException;
import org.springframework.core.io.ClassPathResource;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;

import java.util.function.Function;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import java.io.InputStreamReader;
import java.io.Reader;

@Log4j2
@UtilityClass
public class CsvParser {

    public <K, T> Map<K, T> parseCsvToMap(String filePath, CsvRecordMapper<T> recordMapper, Function<CSVRecord, K> keyMapper) {
        Map<K, T> resultMap = new HashMap<>();

        try (Reader reader = new InputStreamReader(new ClassPathResource(filePath).getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setIgnoreHeaderCase(true)
                     .setTrim(true)
                     .build()
             )
        ) {
            for (CSVRecord record : csvParser) {
                T obj = recordMapper.mapping(record);
                K key = keyMapper.apply(record);
                resultMap.put(key, obj);
            }
        } catch (Exception e) {
            throw new CsvProcessingException("Critical error encountered during CSV processing", e);
        }
        return resultMap;
    }

    public int parseIntOrDefault(String value, int defaultValue, CSVRecord record) {
        try {
            return Integer.parseInt(record.get(value));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String parseStringOrDefault(String value, String defaultValue, CSVRecord record) {
        try {
            String str = record.get(value);
            return str.isEmpty() ? defaultValue : str;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public LocalDate parseDateOrDefault(String value, LocalDate defaultValue, CSVRecord record, String pattern) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        try {
            return LocalDate.parse(record.get(value), formatter);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public String checkIDFields(String field, CSVRecord record) {
        try {
            return record.get(field);
        } catch (Exception e) {
            String newId = UUID.randomUUID().toString();
            log.warn("ID field is empty or null, generating new UUID: {}", newId);
            return newId;
        }
    }
}