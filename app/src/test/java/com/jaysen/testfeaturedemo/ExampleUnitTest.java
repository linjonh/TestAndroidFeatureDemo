package com.jaysen.testfeaturedemo;

import org.junit.Test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
//        assertEquals(4, 2 + 2);
        long    val    = 1000;
        String parsed = parsePayment(val);
        System.out.println("parsedP: " + parsed);
    }

    /**
     * 格式化金额，只保留有效位
     * <p>
     * 例如：1.0-->1; 1.12-->1.12
     */
    public static String parsePayment(double payment) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(payment);
    }

    @Test
    public void testTimeFormat(){
        System.out.println(getDateTime("/Date(1498017601000+0800)/"));
    }
    /**
     * "/Date(1459506444000+0800)/"  转换成  "yyyy/MM/dd"
     *
     * @param dateTime "/Date(1459506444000+0800)/"的时间戳
     * @return
     */
    private String getDateTime(String dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日 E", Locale.getDefault());
        Calendar         calendar   = Calendar.getInstance(Locale.getDefault());
        try {
            dateTime = dateTime.substring(dateTime.indexOf("(") + 1, dateTime.indexOf("+"));
            calendar.setTimeInMillis(Long.decode(dateTime));
            dateTime = dateFormat.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }
}