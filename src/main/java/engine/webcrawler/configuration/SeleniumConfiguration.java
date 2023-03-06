package engine.webcrawler.configuration;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfiguration
{
	@PostConstruct
	void postConstruct()
	{
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
	}

	/*
	@Bean
	public ChromeDriver driver()
	{
		final ChromeOptions options=new ChromeOptions();
		options.addArguments("--headless");
		return new ChromeDriver(options);
	}

	 */
}
