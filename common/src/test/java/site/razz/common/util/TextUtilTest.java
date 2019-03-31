package site.razz.common.util;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.util.TimeZone;
import org.junit.Test;

public class TextUtilTest {

  @Test
  public void dateToLong() throws ParseException {
    final String dtg = "January 2, 2019 12:50PM";
    final String dtgFormat = "MMMMM d, yyyy hh:mma";
    final TimeZone timeZone = TimeZone.getTimeZone("America/Chicago");
    final long dtgLong = 1546455000000L;
    assertEquals(TextUtil.dateToLong(dtg, dtgFormat, timeZone), dtgLong);
  }
}
