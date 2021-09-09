package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIPServiceTests {

  @Test
  public void testMyIp() {
    String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("95.55.85.178");

    System.out.println(geoIP);
    assert(geoIP.contains("RU"));
  }

  @Test
  public void testInvalidIp() {
    String geoIP = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("95.55.85.xxx");

    System.out.println(geoIP);
    assert(geoIP.contains("RU"));
  }
}
