package local.projects.betting.data.entry.selenium.web.driver.impl;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractSeleniumWebDriverDataEntryImpl {
  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractSeleniumWebDriverDataEntryImpl.class);
  
  protected WebDriver driver;
  protected Map<String, String> leagues;
  protected Map<Integer, Object[]> data;
  
  private RemoteWebDriver getWebDriver(int windowWidth, int windowHeight, String webDriver) {
    RemoteWebDriver driver = null;
    String path = null;
    switch (webDriver) {
      case "CHROME":
        path = "/Users/antonio/Desktop/chromedriver/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", path);
        driver = new ChromeDriver();
        break;
      case "PHANTOM_JS":
        // DesiredCapabilities caps = DesiredCapabilities.phantomjs();
        // caps.setCapability(
        // PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
        // phantomJsBinaryPath);
        path = "/Users/antonio/Downloads/phantomjs-2.1.1-windows/bin/phantomjs.exe";
        System.setProperty(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, path);
        driver = new PhantomJSDriver();
    }
    // driver.manage().window().setSize(new Dimension(windowWidth, windowHeight));
    return driver;
  }
}
