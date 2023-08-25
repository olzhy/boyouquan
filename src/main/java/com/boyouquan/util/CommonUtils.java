package com.boyouquan.util;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    private static final SimpleDateFormat MORE_COMMON_DATE_PATTERN = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat MORE_COMMON_DATE_HOUR_PATTERN = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    private static final SimpleDateFormat SITEMAP_DATE_PATTERN = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'+08:00'");

    public static String dateSitemapFormatStr(Date date) {
        return SITEMAP_DATE_PATTERN.format(date);
    }

    public static String dateCommonFormatDisplay(Date date) {
        return MORE_COMMON_DATE_PATTERN.format(date);
    }

    public static String dateHourCommonFormatDisplay(Date date) {
        return MORE_COMMON_DATE_HOUR_PATTERN.format(date);
    }

    public static String getBlogCannotBeAccessedInfo(Date date) {
        if (null == date) {
            return "";
        }

        long now = System.currentTimeMillis();
        long past = date.getTime();

        final long oneDay = 24 * 60 * 60 * 1000;
        final long oneYear = 365 * oneDay;

        long timeDiff = now - past;
        int years = (int) (timeDiff / oneYear);

        String detectedAtDateStr = CommonUtils.dateCommonFormatDisplay(date);
        String timePastStr = getTimePastStr(timeDiff);
        if (years >= 2) {
            return String.format("自「%s」第一次检测到你无法访问以来，到现在%s，我确信你已经陨落在网络的海洋里了！我依旧会为你保留这个页面，来记录你曾经发布过的那些点点滴滴，以证明你真的在这个世界存在过。", detectedAtDateStr, timePastStr);
        }

        return String.format("自「%s」第一次检测到你无法访问以来，到现在%s，但你仍未恢复正常。接下来，我依旧会像往常一样，每天向你发射一次心跳，希望能得到你归来的讯息，如果整整两年都没有等到你归来，那么我也就死心了，我会确信你真的已经陨落在网络的海洋里了。", detectedAtDateStr, timePastStr);
    }

    private static String getTimePastStr(long timeDiff) {
        final long oneDay = 24 * 60 * 60 * 1000;
        final long oneMonth = 30 * oneDay;
        final long oneYear = 365 * oneDay;

        if (timeDiff > oneYear) {
            int years = (int) (timeDiff / oneYear);
            return String.format("已过了%d年", years);
        } else if (timeDiff > oneMonth) {
            int months = (int) (timeDiff / oneMonth);
            return String.format("已过了%d个月", months);
        } else if (timeDiff > oneDay) {
            int days = (int) (timeDiff / oneDay);
            return String.format("已过了%d天", days);
        }

        return "";
    }

    public static String dateFriendlyDisplay(Date date) {
        if (null == date) {
            return "";
        }

        final long halfAHour = 30 * 60 * 1000;
        final long oneHour = 60 * 60 * 1000;
        final long oneDay = 24 * oneHour;
        final long tenDay = 10 * oneDay;

        long now = System.currentTimeMillis();
        long past = date.getTime();

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
        return MORE_COMMON_DATE_PATTERN.format(past);
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

    public static String trimFeedURLSuffix(String blogAddress) {
        if (blogAddress.endsWith("/feed.xml")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/feed.xml".length());
        } else if (blogAddress.endsWith("/feed/")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/feed/".length());
        } else if (blogAddress.endsWith("/feed")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/feed".length());
        } else if (blogAddress.endsWith("/atom.xml")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/atom.xml".length());
        } else if (blogAddress.endsWith("/index.xml")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/index.xml".length());
        } else if (blogAddress.endsWith("/rss.xml")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/rss.xml".length());
        } else if (blogAddress.endsWith("/rss/")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/rss/".length());
        } else if (blogAddress.endsWith("/rss")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/rss".length());
        } else if (blogAddress.endsWith("/feed.php")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/feed.php".length());
        } else if (blogAddress.endsWith("/Feed.php")) {
            blogAddress = blogAddress.substring(0, blogAddress.length() - "/Feed.php".length());
        }

        return blogAddress;
    }

    public static String getDomain(String address) {
        // scheme
        if (address.startsWith("https://")) {
            address = address.substring("https://".length());
        } else if (address.startsWith("http://")) {
            address = address.substring("http://".length());
        }

        // tail
        if (address.endsWith("/")) {
            address = address.substring(0, address.length() - 1);
        }
        return address;
    }

    public static String parseAndTruncateHtml2Text(String html, int length) {
        if (StringUtils.isBlank(html)) {
            return "...";
        }

        String text = Jsoup.parse(html).text();
        if (text.length() <= length) {
            return text;
        } else {
            text = text.substring(0, length);
            text += "...";
        }
        return text;
    }

}
