package br.com.unicuritiba.pixanalyser.application.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class PixUtils {

    public static String generateEndToEndId(String ispb) {
        String prefix = "E";
        String date = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String uniqueId = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 20).toUpperCase();
        return prefix + ispb + date + uniqueId;
    }
}