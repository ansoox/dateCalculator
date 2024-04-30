package com.example.datecalculator.logger;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RussianDateFormatter extends Formatter {
    private SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss");

    @Override
    public String format(LogRecord logRecord) {
        return String.format("[%s] %-1s - %s%n",
                format.format(new Date(logRecord.getMillis())),
                logRecord.getLevel().getLocalizedName(),
                formatMessage(logRecord));
    }
}
