package com.example.datecalculator.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RussianDateFormatter extends Formatter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

    @Override
    public String format(LogRecord record) {
        return String.format("[%s] %-1s - %s%n",
                DATE_FORMAT.format(new Date(record.getMillis())),
                record.getLevel().getLocalizedName(),
                formatMessage(record));
    }
}
