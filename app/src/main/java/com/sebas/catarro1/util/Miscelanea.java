package com.sebas.catarro1.util;

import android.os.Bundle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by sgerman on 11/05/2015.
 */
public class Miscelanea {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd - MMMM - yyyy");

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
        if (null != o){
            result= (Integer) o;
        }
        return result;
    }
}
