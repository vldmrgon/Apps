package com.company.apps.utils.parser;

import org.apache.commons.csv.CSVRecord;

@FunctionalInterface
public interface CsvRecordMapper<T> {
    T mapping(CSVRecord record);
}
