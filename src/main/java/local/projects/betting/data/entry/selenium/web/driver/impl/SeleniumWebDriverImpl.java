package local.projects.betting.data.entry.selenium.web.driver.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import local.projects.betting.data.entry.selenium.web.driver.model.WebDriverEnum;

public class SeleniumWebDriverImpl {
  private static final Logger LOGGER = LoggerFactory.getLogger(SeleniumWebDriverImpl.class);
  
  protected WebDriver driver;
  
  public SeleniumWebDriverImpl(
      WebDriverEnum webDriver) {
    setWebDriver(0, 0, webDriver);
  }
  
  private void setWebDriver(int windowWidth, int windowHeight, WebDriverEnum webDriver) {
    String path = null;
    switch (webDriver) {
      case CHROME:
        path = "chromedriver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
        break;
      case PHANTOMJS:
    	  //Bellow given syntaxes will set browser proxy settings using DesiredCapabilities.
    	  
    	  String PROXY = "192.168.43.1:39910";
//    	  Proxy proxy = new Proxy();
//    	  proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY)
//    	    .setSocksProxy(PROXY);
//    	  DesiredCapabilities cap = new DesiredCapabilities();
//    	  cap.setCapability(CapabilityType.PROXY, proxy);
    	  
        path = "phantomjs-2.1.1-windows/bin/phantomjs.exe";
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path);
        driver = new PhantomJSDriver();
    }
    // driver.manage().window().setSize(new Dimension(windowWidth, windowHeight));
  }
}
