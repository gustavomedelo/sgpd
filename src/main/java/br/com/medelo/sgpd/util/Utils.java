package br.com.medelo.sgpd.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;

@UtilityClass
public class Utils {

    public static boolean nonNullAndNonEmpty(String field) {
        return nonNull(field) && !field.isEmpty();
    }

    public static String formatLocalDateToString(LocalDate date) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dateFormat.format(date);
    }

    public static LocalDate formatStringToLocalDate(String date) {
        return LocalDate.parse(date);
    }
}
