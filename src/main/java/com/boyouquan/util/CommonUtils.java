package com.boyouquan.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    public static String dateFriendlyDisplay(Date date) {
        final long halfAHour = 30 * 60 * 1000;
        final long oneHour = 60 * 60 * 1000;
        final long oneDay = 24 * oneHour;
        final long tenDay = 10 * oneDay;

        SimpleDateFormat commonPattern = new SimpleDateFormat("yyyy年M月d日");
        long now = System.currentTimeMillis();
        long past = date.getTime();
        if (past >= now) {
            return commonPattern.format(past);
        }

        long timeDiff = now - past;
        if (timeDiff < halfAHour) {
            return "刚刚";
        } else if (timeDiff < oneHour) {
            return "半小时前";
        } else if (timeDiff < oneDay) {
            int hours = (int) (timeDiff / oneHour);
            return String.format("%d小时前", hours);
        } else if (timeDiff < tenDay) {
            int days = (int) (timeDiff / oneDay);
            return String.format("%d天前", days);
        }
        return commonPattern.format(past);
    }

    public static String md5(String str) {
        StringBuilder md5 = new StringBuilder();
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = messageDigest.digest();
            for (byte b : bytes) {
                md5.append(byteToHex(b));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5.toString();
    }

    private static String byteToHex(byte b) {
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() < 2) {
            hex = "0" + hex;
        }
        return hex;
    }

}
