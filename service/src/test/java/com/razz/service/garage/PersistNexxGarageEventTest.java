package com.razz.service.garage;

import org.junit.Assert;

public class PersistNexxGarageEventTest {

  @org.junit.Test
  public void parse() throws Exception {
    final String message = "Garage 1 has been closed on January 2, 2019 at 12:50PM.";
    final NexxGarageEvent nge = PersistNexxGarageEvent.parse(message);
    Assert.assertEquals(nge.getName(), "Garage 1");
    Assert.assertEquals(nge.getState(), "closed");
    Assert.assertTrue(nge.getDate() == 1546455000000L);
  }
}
