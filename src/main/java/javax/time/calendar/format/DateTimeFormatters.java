/*
 * Copyright (c) 2008-2010, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package javax.time.calendar.format;

import java.util.Locale;

import javax.time.calendar.ISOChronology;
import javax.time.calendar.format.DateTimeFormatterBuilder.FormatStyle;
import javax.time.calendar.format.DateTimeFormatterBuilder.SignStyle;
import javax.time.calendar.format.DateTimeFormatterBuilder.TextStyle;

/**
 * Provides common implementations of <code>DateTimeFormatter</code>.
 * <p>
 * DateTimeFormatters is a utility class.
 * All formatters returned are immutable and thread-safe.
 *
 * @author Stephen Colebourne
 */
public final class DateTimeFormatters {

    /**
     * Private constructor since this is a utility class
     */
    private DateTimeFormatters() {
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses a local date without an offset,
     * such as '2007-12-03'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-MM-dd</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoLocalDate() {
        return ISO_LOCAL_DATE;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_LOCAL_DATE;
    static {
        ISO_LOCAL_DATE = new DateTimeFormatterBuilder()
            .appendValue(ISOChronology.yearRule(), 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(ISOChronology.monthOfYearRule(), 2)
            .appendLiteral('-')
            .appendValue(ISOChronology.dayOfMonthRule(), 2)
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses an offset date with an offset,
     * such as '2007-12-03+01:00'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-MM-ddZZ</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     * <p>
     * The offset will print and parse an offset with seconds even though that
     * is not part of the ISO-8601 standard.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoOffsetDate() {
        return ISO_OFFSET_DATE;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_OFFSET_DATE;
    static {
        ISO_OFFSET_DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendOffsetId()
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses a date, with the
     * offset and zone if available, such as '2007-12-03', '2007-12-03+01:00'
     * or '2007-12-03+01:00[Europe/Paris]'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-MM-dd[ZZ['['{ZoneId}']']]</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     * <p>
     * The offset will print and parse an offset with seconds even though that
     * is not part of the ISO-8601 standard.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoDate() {
        return ISO_DATE;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_DATE;
    static {
        ISO_DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .optionalStart()
            .appendOffsetId()
            .optionalStart()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO time formatter that prints/parses a local time, without an offset
     * such as '10:15:30'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>HH:mm[:ss[.S]]</code>
     * <p>
     * The seconds will be printed if present in the Calendrical, thus a LocalTime
     * will always print the seconds.
     * The nanoseconds will be printed if non-zero.
     * If non-zero, the minimum number of fractional second digits will printed.
     *
     * @return the ISO time formatter, never null
     */
    public static DateTimeFormatter isoLocalTime() {
        return ISO_LOCAL_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_LOCAL_TIME;
    static {
        ISO_LOCAL_TIME = new DateTimeFormatterBuilder()
            .appendValue(ISOChronology.hourOfDayRule(), 2)
            .appendLiteral(':')
            .appendValue(ISOChronology.minuteOfHourRule(), 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(ISOChronology.secondOfMinuteRule(), 2)
            .optionalStart()
            .appendFraction(ISOChronology.nanoOfSecondRule(), 0, 9)
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO time formatter that prints/parses a local time, with an offset
     * such as '10:15:30+01:00'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>HH:mm[:ss[.S]]ZZ</code>
     * <p>
     * The seconds will be printed if present in the Calendrical, thus an OffsetTime
     * will always print the seconds.
     * The nanoseconds will be printed if non-zero.
     * If non-zero, the minimum number of fractional second digits will printed.
     * <p>
     * The offset will print and parse an offset with seconds even though that
     * is not part of the ISO-8601 standard.
     *
     * @return the ISO time formatter, never null
     */
    public static DateTimeFormatter isoOffsetTime() {
        return ISO_OFFSET_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_OFFSET_TIME;
    static {
        ISO_OFFSET_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_TIME)
            .appendOffsetId()
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO time formatter that prints/parses a time, with the
     * offset and zone if available, such as '10:15:30', '10:15:30+01:00'
     * or '10:15:30+01:00[Europe/Paris]'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>HH:mm[:ss[.S]][ZZ['['{ZoneId}']']]</code>
     * <p>
     * The seconds will be printed if present in the Calendrical, thus a LocalTime
     * will always print the seconds.
     * The nanoseconds will be printed if non-zero.
     * If non-zero, the minimum number of fractional second digits will printed.
     * <p>
     * The offset will print and parse an offset with seconds even though that
     * is not part of the ISO-8601 standard.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoTime() {
        return ISO_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_TIME;
    static {
        ISO_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_TIME)
            .optionalStart()
            .appendOffsetId()
            .optionalStart()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses a local date without an offset,
     * such as '2007-12-03T10:15:30'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-MM-dd'T'HH:mm[:ss[.S]]</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     * <p>
     * The seconds will be printed if present in the Calendrical, thus a LocalDateTime
     * will always print the seconds.
     * The nanoseconds will be printed if non-zero.
     * If non-zero, the minimum number of fractional second digits will printed.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoLocalDateTime() {
        return ISO_LOCAL_DATE_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_LOCAL_DATE_TIME;
    static {
        ISO_LOCAL_DATE_TIME = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(ISO_LOCAL_DATE)
            .appendLiteral('T')
            .append(ISO_LOCAL_TIME)
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses an offset date with an offset,
     * such as '2007-12-03T10:15:30+01:00'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-MM-dd'T'HH:mm[:ss[.S]]ZZ</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     * <p>
     * The seconds will be printed if present in the Calendrical, thus an OffsetDateTime
     * will always print the seconds.
     * The nanoseconds will be printed if non-zero.
     * If non-zero, the minimum number of fractional second digits will printed.
     * <p>
     * The offset will print and parse an offset with seconds even though that
     * is not part of the ISO-8601 standard.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoOffsetDateTime() {
        return ISO_OFFSET_DATE_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_OFFSET_DATE_TIME;
    static {
        ISO_OFFSET_DATE_TIME = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE_TIME)
            .appendOffsetId()
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses an offset date with a zone,
     * such as '2007-12-03T10:15:30+01:00[Europe/Paris]'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-MM-dd'T'HH:mm[:ss[.S]]ZZ[{ZoneId}]</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     * <p>
     * The seconds will be printed if present in the Calendrical, thus an OffsetDateTime
     * will always print the seconds.
     * The nanoseconds will be printed if non-zero.
     * If non-zero, the minimum number of fractional second digits will printed.
     * <p>
     * The offset will print and parse an offset with seconds even though that
     * is not part of the ISO-8601 standard.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoZonedDateTime() {
        return ISO_ZONED_DATE_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_ZONED_DATE_TIME;
    static {
        ISO_ZONED_DATE_TIME = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE_TIME)
            .appendOffsetId()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses a date, with the
     * offset and zone if available, such as '2007-12-03T10:15:30',
     * '2007-12-03T10:15:30+01:00' or '2007-12-03T10:15:30+01:00[Europe/Paris]'.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-MM-dd'T'HH:mm[:ss[.S]][ZZ['['{ZoneId}']']]</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     * <p>
     * The seconds will be printed if present in the Calendrical, thus a ZonedDateTime
     * will always print the seconds.
     * The nanoseconds will be printed if non-zero.
     * If non-zero, the minimum number of fractional second digits will printed.
     * <p>
     * The offset will print and parse an offset with seconds even though that
     * is not part of the ISO-8601 standard.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter isoDateTime() {
        return ISO_DATE_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_DATE_TIME;
    static {
        ISO_DATE_TIME = new DateTimeFormatterBuilder()
            .append(ISO_LOCAL_DATE_TIME)
            .optionalStart()
            .appendOffsetId()
            .optionalStart()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses a date without an offset.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-DDD</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     *
     * @return the ISO ordinal date formatter, never null
     */
    public static DateTimeFormatter isoOrdinalDate() {
        return ISO_ORDINAL_DATE;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_ORDINAL_DATE;
    static {
        ISO_ORDINAL_DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ISOChronology.yearRule(), 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral('-')
            .appendValue(ISOChronology.dayOfYearRule(), 3)
            .optionalStart()
            .appendOffsetId()
            .optionalStart()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses a date without an offset.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyy-Www-D</code>
     * <p>
     * The year will print 4 digits, unless this is insufficient, in which
     * case the full year will be printed together with a positive/negative sign.
     *
     * @return the ISO week date formatter, never null
     */
    public static DateTimeFormatter isoWeekDate() {
        return ISO_WEEK_DATE;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter ISO_WEEK_DATE;
    static {
        ISO_WEEK_DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ISOChronology.weekBasedYearRule(), 4, 10, SignStyle.EXCEEDS_PAD)
            .appendLiteral("-W")
            .appendValue(ISOChronology.weekOfWeekBasedYearRule(), 2)
            .appendLiteral('-')
            .appendValue(ISOChronology.dayOfWeekRule(), 1)
            .optionalStart()
            .appendOffsetId()
            .optionalStart()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the ISO date formatter that prints/parses a date without an offset.
     * <p>
     * This is the ISO-8601 extended format:<br />
     * <code>yyyyMMdd</code>
     * <p>
     * The year is limited to printing and parsing 4 digits, as the lack of
     * separators makes it impossible to parse more than 4 digits.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter basicIsoDate() {
        return BASIC_ISO_DATE;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter BASIC_ISO_DATE;
    static {
        BASIC_ISO_DATE = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendValue(ISOChronology.yearRule(), 4)
            .appendValue(ISOChronology.monthOfYearRule(), 2)
            .appendValue(ISOChronology.dayOfMonthRule(), 2)
            .optionalStart()
            .appendOffset("Z", false, false)
            .optionalStart()
            .appendLiteral('[')
            .appendZoneId()
            .appendLiteral(']')
            .toFormatter();
    }

    //-----------------------------------------------------------------------
    /**
     * Returns the RFC-1123 date-time formatter.
     * <p>
     * This is the RFC-1123 format: EEE, dd MMM yyyy HH:mm:ss Z.
     * This is the updated replacement for RFC-822 which had a two digit year.
     * <p>
     * The year will print 4 digits, and only the range 0000 to 9999 is supported.
     *
     * @return the ISO date formatter, never null
     */
    public static DateTimeFormatter rfc1123() {
        return RFC_1123_DATE_TIME;
    }

    /** Singleton date formatter. */
    private static final DateTimeFormatter RFC_1123_DATE_TIME;
    static {
        RFC_1123_DATE_TIME = new DateTimeFormatterBuilder()
            .appendText(ISOChronology.dayOfWeekRule(), TextStyle.SHORT)
            .appendLiteral(", ")
            .appendValue(ISOChronology.dayOfMonthRule(), 2)
            .appendLiteral(' ')
            .appendText(ISOChronology.monthOfYearRule(), TextStyle.SHORT)
            .appendLiteral(' ')
            .appendValue(ISOChronology.yearRule(), 4, 4, SignStyle.NOT_NEGATIVE)
            .appendLiteral(' ')
            .appendValue(ISOChronology.hourOfDayRule(), 2)
            .appendLiteral(':')
            .appendValue(ISOChronology.minuteOfHourRule(), 2)
            .appendLiteral(':')
            .appendValue(ISOChronology.secondOfMinuteRule(), 2)
            .appendLiteral(' ')
            .appendOffset("Z", false, false)
            .toFormatter()
            .withLocale(Locale.ENGLISH);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a locale specific date format, which is typically of full length.
     * <p>
     * This returns a formatter that will print/parse a full length date format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate full
     * length date format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the full date formatter, never null
     */
    public static DateTimeFormatter fullDate(Locale locale) {
        return date(FormatStyle.FULL, locale);
    }

    /**
     * Returns a locale specific date format, which is typically of long length.
     * <p>
     * This returns a formatter that will print/parse a long length date format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate long
     * length date format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the long date formatter, never null
     */
    public static DateTimeFormatter longDate(Locale locale) {
        return date(FormatStyle.LONG, locale);
    }

    /**
     * Returns a locale specific date format of medium length.
     * <p>
     * This returns a formatter that will print/parse a medium length date format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate medium
     * length date format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the medium date formatter, never null
     */
    public static DateTimeFormatter mediumDate(Locale locale) {
        return date(FormatStyle.MEDIUM, locale);
    }

    /**
     * Returns a locale specific date format of short length.
     * <p>
     * This returns a formatter that will print/parse a short length date format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate short
     * length date format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the short date formatter, never null
     */
    public static DateTimeFormatter shortDate(Locale locale) {
        return date(FormatStyle.SHORT, locale);
    }

    /**
     * Returns a locale specific date format.
     * <p>
     * This returns a formatter that will print/parse a date.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate
     * date format for that new locale.
     *
     * @param dateStyle  the formatter style to obtain, not null
     * @param locale  the locale to use, not null
     * @return the date formatter, never null
     */
    public static DateTimeFormatter date(FormatStyle dateStyle, Locale locale) {
        DateTimeFormatter.checkNotNull(dateStyle, "Date style must not be null");
        return new DateTimeFormatterBuilder().appendLocalized(dateStyle, null).toFormatter(locale);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a locale specific time format, which is typically of full length.
     * <p>
     * This returns a formatter that will print/parse a full length time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate full
     * length time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the full time formatter, never null
     */
    public static DateTimeFormatter fullTime(Locale locale) {
        return time(FormatStyle.FULL, locale);
    }

    /**
     * Returns a locale specific time format, which is typically of long length.
     * <p>
     * This returns a formatter that will print/parse a long length time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate long
     * length time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the long time formatter, never null
     */
    public static DateTimeFormatter longTime(Locale locale) {
        return time(FormatStyle.LONG, locale);
    }

    /**
     * Returns a locale specific time format of medium length.
     * <p>
     * This returns a formatter that will print/parse a medium length time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate medium
     * length time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the medium time formatter, never null
     */
    public static DateTimeFormatter mediumTime(Locale locale) {
        return time(FormatStyle.MEDIUM, locale);
    }

    /**
     * Returns a locale specific time format of short length.
     * <p>
     * This returns a formatter that will print/parse a short length time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate short
     * length time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the short time formatter, never null
     */
    public static DateTimeFormatter shortTime(Locale locale) {
        return time(FormatStyle.SHORT, locale);
    }

    /**
     * Returns a locale specific time format.
     * <p>
     * This returns a formatter that will print/parse a time.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate
     * time format for that new locale.
     *
     * @param timeStyle  the formatter style to obtain, not null
     * @param locale  the locale to use, not null
     * @return the time formatter, never null
     */
    public static DateTimeFormatter time(FormatStyle timeStyle, Locale locale) {
        DateTimeFormatter.checkNotNull(timeStyle, "Time style must not be null");
        return new DateTimeFormatterBuilder().appendLocalized(null, timeStyle).toFormatter(locale);
    }

    //-----------------------------------------------------------------------
    /**
     * Returns a locale specific date-time format, which is typically of full length.
     * <p>
     * This returns a formatter that will print/parse a full length date-time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate full
     * length date-time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the full date-time formatter, never null
     */
    public static DateTimeFormatter fullDateTime(Locale locale) {
        return dateTime(FormatStyle.FULL, locale);
    }

    /**
     * Returns a locale specific date-time format, which is typically of long length.
     * <p>
     * This returns a formatter that will print/parse a long length date-time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate long
     * length date-time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the long date-time formatter, never null
     */
    public static DateTimeFormatter longDateTime(Locale locale) {
        return dateTime(FormatStyle.LONG, locale);
    }

    /**
     * Returns a locale specific date-time format of medium length.
     * <p>
     * This returns a formatter that will print/parse a medium length date-time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate medium
     * length date-time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the medium date-time formatter, never null
     */
    public static DateTimeFormatter mediumDateTime(Locale locale) {
        return dateTime(FormatStyle.MEDIUM, locale);
    }

    /**
     * Returns a locale specific date-time format of short length.
     * <p>
     * This returns a formatter that will print/parse a short length date-time format.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate short
     * length date-time format for that new locale.
     *
     * @param locale  the locale to use, not null
     * @return the short date-time formatter, never null
     */
    public static DateTimeFormatter shortDateTime(Locale locale) {
        return dateTime(FormatStyle.SHORT, locale);
    }

    /**
     * Returns a locale specific date-time format, which is typically of short length.
     * <p>
     * This returns a formatter that will print/parse a date-time.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate
     * date-time format for that new locale.
     *
     * @param dateTimeStyle  the formatter style to obtain, not null
     * @param locale  the locale to use, not null
     * @return the date-time formatter, never null
     */
    public static DateTimeFormatter dateTime(FormatStyle dateTimeStyle, Locale locale) {
        DateTimeFormatter.checkNotNull(dateTimeStyle, "Date-time style must not be null");
        return new DateTimeFormatterBuilder().appendLocalized(dateTimeStyle, dateTimeStyle).toFormatter(locale);
    }

    /**
     * Returns a locale specific date, time or date-time format.
     * <p>
     * This returns a formatter that will print/parse a date, time or date-time.
     * The exact format pattern used varies by locale, which is determined from the
     * locale on the formatter. That locale is initialized by method.
     * If a new formatter is obtained using {@link DateTimeFormatter#withLocale(Locale)}
     * then it will typically change the pattern in use to the appropriate
     * format for that new locale.
     *
     * @param dateStyle  the date formatter style to obtain, not null
     * @param timeStyle  the time formatter style to obtain, not null
     * @param locale  the locale to use, not null
     * @return the date, time or date-time formatter, never null
     */
    public static DateTimeFormatter dateTime(FormatStyle dateStyle, FormatStyle timeStyle, Locale locale) {
        DateTimeFormatter.checkNotNull(dateStyle, "Date style must not be null");
        DateTimeFormatter.checkNotNull(timeStyle, "Time style must not be null");
        return new DateTimeFormatterBuilder().appendLocalized(dateStyle, timeStyle).toFormatter(locale);
    }

}