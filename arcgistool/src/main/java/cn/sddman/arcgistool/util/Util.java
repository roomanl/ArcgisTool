package cn.sddman.arcgistool.util;

import android.content.Context;
import android.util.TypedValue;

import java.math.BigDecimal;
import java.text.NumberFormat;

import cn.sddman.arcgistool.R;
import cn.sddman.arcgistool.common.Variable;

public class Util {

    public static int valueToSp(Context context,int value){
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics());
    }

    public static String forMatDouble(double num){
        //NumberFormat numberFormat=NumberFormat.getInstance();
        //numberFormat.setMaximumFractionDigits(2);
        BigDecimal b = new BigDecimal(num);
        double df = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return df+"";
        //return numberFormat.format(num);
    }

    public static double lengthChange(double length, Variable.Measure type){
        if(type==Variable.Measure.M) return length;
        if(type==Variable.Measure.KM || type==Variable.Measure.KIM)
            return mToKm(length);
        return 0;
    }

    public static double mToKm(double length){
        return length/1000;
    }

    public static String lengthEnameToCname(Variable.Measure type){
        switch (type){
            case M:
                return "米";
            case KM:
                return "千米";
            case KIM:
                return "公里";
            case M2:
                return "平方米";
            case KM2:
                return "平方千米";
            case HM2:
                return "公顷";
            case A2:
                return "亩";
        }
        return null;
    }
    public static double areaChange(double area, Variable.Measure type){
        switch (type){
            case M2:
                return area;
            case KM2:
                return m2ToKm2(area);
            case HM2:
                return m2ToHm2(area);
            case A2:
                return m2ToA2(area);
        }
        return 0;
    }

    public static double m2ToKm2(double area){
        return area/1000;
    }
    public static double m2ToHm2(double area){
        return area/10000;
    }

    public static double m2ToA2(double area){
        return area/666.67;
    }


}
