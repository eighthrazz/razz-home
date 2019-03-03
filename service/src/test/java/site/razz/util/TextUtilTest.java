package com.razz.util;

import java.text.ParseException;
import org.junit.Test;

import static org.junit.Assert.*;

public class TextUtilTest {

  @Test
  public void dateToLong() throws ParseException {
    final String dtg = "January 2, 2019 12:50PM";
    final String dtgFormat = "MMMMM d, yyyy hh:mma";
    final long dtgLong = 1546455000000L;
    assertTrue(TextUtil.dateToLong(dtg, dtgFormat) == dtgLong);
  }
}
