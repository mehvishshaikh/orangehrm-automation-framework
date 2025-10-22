package utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;
import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	
	public TestBase() {
		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir") + "/src/test/resources/configurationDetails/config.properties"
			);
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Loading config from: " + System.getProperty("user.dir") + "/src/test/resources/configurationDetails/config.properties");
	}
	
	public static void initialization() {
		String browserName = prop.getProperty("browser");
		
		if(browserName.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();

			// Add GitHub Actions compatible settings
			if (System.getenv("GITHUB_ACTIONS") != null) {
				System.out.println("⚙ Running in GitHub Actions - enabling headless mode");
				options.addArguments("--headless");
				options.addArguments("--no-sandbox");
				options.addArguments("--disable-dev-shm-usage");
				options.addArguments("--remote-allow-origins=*");
				options.addArguments("--disable-gpu");
				options.addArguments("--disable-extensions");
				options.addArguments("--window-size=1920,1080");
				//options.addArguments("--user-data-dir=/tmp/chrome-user-data-" + UUID.randomUUID());
			}

			driver = new ChromeDriver();
		}
		
		if(browserName.equalsIgnoreCase("firefox")) {
			driver = new FirefoxDriver();
		}
		
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.PAGE_LOAD_TIME));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.IMPLICIT_WAIT));
		
		driver.get(prop.getProperty("url"));
		
	}

	
}
