/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.altius.common.utils;

import cc.altius.utils.DateUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 *
 * @author sagar
 */
public class CommonUtils {

    private static String YMDHM = "yyyy-MM-dd HH:mm";

    public static String dateConverte_IST_To_PST(String inputDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        Date startDateObject = dateFormat.parse(inputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        String startPSTDate = dateFormat.format(startDateObject);
        return startPSTDate;
    }

    public static String dateConverte_PST_To_IST(String inputDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        Date startDateObject = dateFormat.parse(inputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        String startPSTDate = dateFormat.format(startDateObject);
        return startPSTDate;
    }

    public static String dateConverte_IST_To_EST(String inputDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        Date startDateObject = dateFormat.parse(inputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        String startPSTDate = dateFormat.format(startDateObject);
        return startPSTDate;

    }

    public static String dateConverte_EST_To_IST(String inputDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        Date startDateObject = dateFormat.parse(inputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        String startPSTDate = dateFormat.format(startDateObject);
        return startPSTDate;

    }

    public static String dateConverte_EST_To_PST(String inputDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        Date startDateObject = dateFormat.parse(inputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        String startPSTDate = dateFormat.format(startDateObject);
        return startPSTDate;


    }

    public static String dateConverte_PST_To_EST(String inputDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        Date startDateObject = dateFormat.parse(inputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        String startPSTDate = dateFormat.format(startDateObject);
        return startPSTDate;
    }

    public static Date dateConverte_PST_To_IST(Date inputDate) throws ParseException {
        if (inputDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(YMDHM);
        String strInputDate = df.format(inputDate);
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        Date startDateObject = dateFormat.parse(strInputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        String startPSTDate = dateFormat.format(startDateObject);
        Date outputDate = df.parse(startPSTDate);
        return outputDate;
    }

    public static Date dateConverte_IST_To_PST(Date inputDate) throws ParseException {
        if (inputDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(YMDHM);
        String strInputDate = df.format(inputDate);
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        Date startDateObject = dateFormat.parse(strInputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        String startPSTDate = dateFormat.format(startDateObject);
        Date outputDate = df.parse(startPSTDate);
        return outputDate;
    }

    public static Date dateConverte_IST_To_EST(Date inputDate) throws ParseException {
        if (inputDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(YMDHM);
        String strInputDate = df.format(inputDate);
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        Date startDateObject = dateFormat.parse(strInputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        String startPSTDate = dateFormat.format(startDateObject);
        Date outputDate = df.parse(startPSTDate);
        return outputDate;

    }

    public static Date dateConverte_EST_To_IST(Date inputDate) throws ParseException {
        if (inputDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(YMDHM);
        String strInputDate = df.format(inputDate);
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        Date startDateObject = dateFormat.parse(strInputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.IST));
        String startPSTDate = dateFormat.format(startDateObject);
        Date outputDate = df.parse(startPSTDate);
        return outputDate;

    }

    public static Date dateConverte_EST_To_PST(Date inputDate) throws ParseException {
        if (inputDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(YMDHM);
        String strInputDate = df.format(inputDate);
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        Date startDateObject = dateFormat.parse(strInputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        String startPSTDate = dateFormat.format(startDateObject);
        Date outputDate = df.parse(startPSTDate);
        return outputDate;


    }

    public static Date dateConverte_PST_To_EST(Date inputDate) throws ParseException {
        if (inputDate == null) {
            return null;
        }
        DateFormat df = new SimpleDateFormat(YMDHM);
        String strInputDate = df.format(inputDate);
        DateFormat dateFormat = new SimpleDateFormat(YMDHM);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.PST));
        Date startDateObject = dateFormat.parse(strInputDate);
        dateFormat.setTimeZone(TimeZone.getTimeZone(DateUtils.EST));
        String startPSTDate = dateFormat.format(startDateObject);
        Date outputDate = df.parse(startPSTDate);
        return outputDate;


    }
}
