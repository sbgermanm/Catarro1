package com.sebas.catarro1.util;

import android.os.Bundle;
import android.text.StaticLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by sgerman on 11/05/2015.
 * <p/>
 * The following pattern letters are defined (all other characters from 'A' to 'Z' and from 'a' to 'z' are reserved):
 * <p/>
 * <p/>
 * <p/>
 * Letter
 * <p/>
 * Date or Time Component
 * <p/>
 * Presentation
 * <p/>
 * Examples
 * <p/>
 * G  Era designator  Text  AD
 * y  Year  Year  1996; 96
 * Y  Week year  Year  2009; 09
 * M  Month in year (context sensitive)  Month  July; Jul; 07
 * L  Month in year (standalone form)  Month  July; Jul; 07
 * w  Week in year  Number  27
 * W  Week in month  Number  2
 * D  Day in year  Number  189
 * d  Day in month  Number  10
 * F  Day of week in month  Number  2
 * E  Day name in week  Text  Tuesday; Tue
 * u  Day number of week (1 = Monday, ..., 7 = Sunday)  Number  1
 * a  Am/pm marker  Text  PM
 * H  Hour in day (0-23)  Number  0
 * k  Hour in day (1-24)  Number  24
 * K  Hour in am/pm (0-11)  Number  0
 * h  Hour in am/pm (1-12)  Number  12
 * m  Minute in hour  Number  30
 * s  Second in minute  Number  55
 * S  Millisecond  Number  978
 * z  Time zone  General time zone  Pacific Standard Time; PST; GMT-08:00
 * Z  Time zone  RFC 822 time zone  -0800
 * X  Time zone  ISO 8601 time zone  -08; -0800; -08:00
 * Pattern letters are usually repeated, as their number determines the exact presentation: Text: For formatting, if the number of pattern letters is 4 or more, the full form is used; otherwise a short or abbreviated form is used if available. For parsing, both forms are accepted, independent of the number of pattern letters.
 * <p/>
 * <p/>
 * Number: For formatting, the number of pattern letters is the minimum number of digits, and shorter numbers are zero-padded to this amount. For parsing, the number of pattern letters is ignored unless it's needed to separate two adjacent fields.
 * <p/>
 * <p/>
 * Year: If the formatter's Calendar is the Gregorian calendar, the following rules are applied.
 * ?For formatting, if the number of pattern letters is 2, the year is truncated to 2 digits; otherwise it is interpreted as a number.
 * ?For parsing, if the number of pattern letters is more than 2, the year is interpreted literally, regardless of the number of digits. So using the pattern "MM/dd/yyyy", "01/11/12" parses to Jan 11, 12 A.D.
 * ?For parsing with the abbreviated year pattern ("y" or "yy"), SimpleDateFormat must interpret the abbreviated year relative to some century. It does this by adjusting dates to be within 80 years before and 20 years after the time the SimpleDateFormat instance is created. For example, using a pattern of "MM/dd/yy" and a SimpleDateFormat instance created on Jan 1, 1997, the string "01/11/12" would be interpreted as Jan 11, 2012 while the string "05/04/64" would be interpreted as May 4, 1964. During parsing, only strings consisting of exactly two digits, as defined by Character.isDigit(char), will be parsed into the default century. Any other numeric string, such as a one digit string, a three or more digit string, or a two digit string that isn't all digits (for example, "-1"), is interpreted literally. So "01/02/3" or "01/02/003" are parsed, using the same pattern, as Jan 2, 3 AD. Likewise, "01/02/-3" is parsed as Jan 2, 4 BC.
 * Otherwise, calendar system specific forms are applied. For both formatting and parsing, if the number of pattern letters is 4 or more, a calendar specific long form is used. Otherwise, a calendar specific short or abbreviated form is used.
 * <p/>
 * If week year 'Y' is specified and the calendar doesn't support any  week years, the calendar year ('y') is used instead. The support of week years can be tested with a call to getCalendar().isWeekDateSupported().
 * <p/>
 * <p/>
 * Month: If the number of pattern letters is 3 or more, the month is interpreted as text; otherwise, it is interpreted as a number.
 * ?Letter M produces context-sensitive month names, such as the embedded form of names. If a DateFormatSymbols has been set explicitly with constructor SimpleDateFormat(String, DateFormatSymbols) or method setDateFormatSymbols(DateFormatSymbols), the month names given by the DateFormatSymbols are used.
 * ?Letter L produces the standalone form of month names.
 * <p/>
 * <p/>
 * General time zone: Time zones are interpreted as text if they have names. For time zones representing a GMT offset value, the following syntax is used:      GMTOffsetTimeZone:
 * GMT Sign Hours : Minutes
 * Sign: one of
 * + -
 * Hours:
 * Digit
 * Digit Digit
 * Minutes:
 * Digit Digit
 * Digit: one of
 * 0 1 2 3 4 5 6 7 8 9
 * Hours must be between 0 and 23, and Minutes must be between 00 and 59. The format is locale independent and digits must be taken from the Basic Latin block of the Unicode standard.
 * For parsing, RFC 822 time zones are also accepted.
 * <p/>
 * <p/>
 * <p/>
 * RFC 822 time zone: For formatting, the RFC 822 4-digit time zone format is used:      RFC822TimeZone:
 * Sign TwoDigitHours Minutes
 * TwoDigitHours:
 * Digit Digit
 * TwoDigitHours must be between 00 and 23. Other definitions are as for general time zones.
 * For parsing, general time zones are also accepted.
 * <p/>
 * ISO 8601 Time zone: The number of pattern letters designates the format for both formatting and parsing as follows:      ISO8601TimeZone:
 * OneLetterISO8601TimeZone
 * TwoLetterISO8601TimeZone
 * ThreeLetterISO8601TimeZone
 * OneLetterISO8601TimeZone:
 * Sign TwoDigitHours
 * Z
 * TwoLetterISO8601TimeZone:
 * Sign TwoDigitHours Minutes
 * Z
 * ThreeLetterISO8601TimeZone:
 * Sign TwoDigitHours : Minutes
 * Z
 * Other definitions are as for general time zones or RFC 822 time zones.
 * For formatting, if the offset value from GMT is 0, "Z" is produced. If the number of pattern letters is 1, any fraction of an hour is ignored. For example, if the pattern is "X" and the time zone is "GMT+05:30", "+05" is produced.
 * <p/>
 * For parsing, "Z" is parsed as the UTC time zone designator. General time zones are not accepted.
 * <p/>
 * If the number of pattern letters is 4 or more, IllegalArgumentException is thrown when constructing a SimpleDateFormat or applying a pattern.
 * <p/>
 * SimpleDateFormat also supports localized date and time pattern strings. In these strings, the pattern letters described above may be replaced with other, locale dependent, pattern letters. SimpleDateFormat does not deal with the localization of text other than the pattern letters; that's up to the client of the class.
 * Examples
 * The following examples show how date and time patterns are interpreted in the U.S. locale. The given date and time are 2001-07-04 12:08:56 local time in the U.S. Pacific Time time zone.
 * <p/>
 * <p/>
 * Date and Time Pattern
 * <p/>
 * Result
 * <p/>
 * "yyyy.MM.dd G 'at' HH:mm:ss z"  2001.07.04 AD at 12:08:56 PDT
 * "EEE, MMM d, ''yy"  Wed, Jul 4, '01
 * "h:mm a"  12:08 PM
 * "hh 'o''clock' a, zzzz"  12 o'clock PM, Pacific Daylight Time
 * "K:mm a, z"  0:08 PM, PDT
 * "yyyyy.MMMMM.dd GGG hh:mm aaa"  02001.July.04 AD 12:08 PM
 * "EEE, d MMM yyyy HH:mm:ss Z"  Wed, 4 Jul 2001 12:08:56 -0700
 * "yyMMddHHmmssZ"  010704120856-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSZ"  2001-07-04T12:08:56.235-0700
 * "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"  2001-07-04T12:08:56.235-07:00
 * "YYYY-'W'ww-u"  2001-W27-3
 */
public class Miscelanea {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy");
    private static SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sdfFechaHora = new SimpleDateFormat("dd - MMMM - yyyy HH:mm");


    public static String dameFechaComoString(Long fecha) {
        Date aux = new Date(fecha);
        return sdf.format(aux);
    }

    public static Date dameStringComoFecha(String fecha) throws ParseException {
        return sdf.parse(fecha);
    }


    public static String dameFechaComoString(int year, int monthOfYear, int dayOfMonth) {
        String result;

        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.YEAR, year);
        calender.set(Calendar.MONTH, monthOfYear);
        calender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        Date fecha = calender.getTime();
        result = sdf.format(fecha);


        return result;
    }

    public static Integer BundleGetInteger(Bundle bundle, String key) {
        Integer result = null;
        Object o = bundle.get(key);
        if (null != o) {
            result = (Integer) o;
        }
        return result;
    }

    public static String dameFechaPasadaComprensible(Long fecha) {
        String aux = "";
        Date hoy = new Date();
        long diasEntreFechas = dameDiferenciaEnDiasEntreFechas(hoy.getTime(), fecha);

        if (diasEntreFechas == 0) aux = "Hoy";
        if (diasEntreFechas == 1) aux = "Ayer";
        if ((diasEntreFechas > 1) && (diasEntreFechas < 8))
            aux = "Hace " + diasEntreFechas + " días";
        if (diasEntreFechas > 7) aux = Miscelanea.dameFechaComoString(fecha);
        return aux;

    }

    private static long dameDiferenciaEnDiasEntreFechas(long fechaMayor, long fechaMenor) {
        long diff = fechaMayor - fechaMenor;
        return diff / (24 * 60 * 60 * 1000);
    }


    public static int getEdad(Long fechaNacimiento) {

        Date hoy = new Date();
        Calendar calHoy = Calendar.getInstance();
        calHoy.setTime(hoy);

        Calendar calNac = Calendar.getInstance();
        calNac.setTimeInMillis(fechaNacimiento);

        int annoNac = calNac.get(Calendar.YEAR);
        int annoHoy = calHoy.get(Calendar.YEAR);

        int anos = annoHoy - annoNac;

        int diaAnoNac = calNac.get(Calendar.DAY_OF_YEAR);
        int diaAnoHoy = calHoy.get(Calendar.DAY_OF_YEAR);

        if (diaAnoHoy < diaAnoNac) anos--;

        return anos;
    }


    public static Bundle dameFechaInicial() {
        Date aux = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(aux);
        Bundle fecha = new Bundle();
        fecha.putInt("dia", cal.get(Calendar.DAY_OF_MONTH));
        fecha.putInt("mes", cal.get(Calendar.MONTH));
        fecha.putInt("anno", cal.get(Calendar.YEAR));
        fecha.putInt("hora", cal.get(Calendar.HOUR_OF_DAY));
        fecha.putInt("minutos", cal.get(Calendar.MINUTE));


        return fecha;
    }


    public static Bundle dameFecha(Date aux) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aux);
        Bundle fecha = new Bundle();
        fecha.putInt("dia", cal.get(Calendar.DAY_OF_MONTH));
        fecha.putInt("mes", cal.get(Calendar.MONTH));
        fecha.putInt("anno", cal.get(Calendar.YEAR));

        return fecha;
    }

    public static Bundle dameFechaCortaComoBundle(String aux) throws ParseException {
        Date date = dameStringComoFecha(aux);
        return dameFecha(date);
    }


    public static String dameHoraComoString(int hora, int minutos) {
        String result;

        Calendar calender = Calendar.getInstance();
        calender.set(Calendar.HOUR_OF_DAY, hora);
        calender.set(Calendar.MINUTE, minutos);
        Date fecha = calender.getTime();
        result = sdfHora.format(fecha);
        return result;
    }

    public static String dameHoraComoString(Long fecha) {
        return sdfHora.format(new Date(fecha));
    }

    public static Date dameStringFechaHoraComoFecha(String fechaCatarro) throws ParseException {
        return sdfFechaHora.parse(fechaCatarro);
    }

    public static Bundle dameHoraComoBundle(String aux) throws ParseException {
        Date date = dameStringHoraComoFecha(aux);
        return dameHora(date);
    }

    private static Date dameStringHoraComoFecha(String aux) throws ParseException {
        return sdfHora.parse(aux);
    }

    public static Bundle dameHora(Date aux) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(aux);
        Bundle fecha = new Bundle();
        fecha.putInt("hora", cal.get(Calendar.HOUR_OF_DAY));
        fecha.putInt("minutos", cal.get(Calendar.MINUTE));

        return fecha;
    }

}
