package com.razz.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class TextUtil {

  public static String encode(String text) throws UnsupportedEncodingException {
    return URLEncoder.encode(text, "UTF-8");
  }

  public static String toString(InputStream inputStream) {
    return toString(inputStream, System.lineSeparator());
  }

  public static String toString(InputStream inputStream, String delimiter) {
    final String result =
        new BufferedReader(new InputStreamReader(inputStream))
            .lines()
            .collect(Collectors.joining(delimiter));
    return result;
  }

  public static String find(String regex, int group, String source, String _default) {
    try {
      return find(Pattern.compile(regex), group, source, _default);
    } catch (Exception e) {
    }
    return _default;
  }

  public static String find(Pattern pattern, int group, String source, String _default) {
    try {
      final Matcher matcher = pattern.matcher(source);
      if (matcher.find()) {
        return matcher.group(group);
      }
    } catch (Exception e) {
    }
    return _default;
  }

  public static long dateToLong(String dtg, String dtgFormat) throws ParseException {
    final SimpleDateFormat parser = new SimpleDateFormat(dtgFormat);
    final Date date = parser.parse(dtg);
    return date.getTime();
  }
}
