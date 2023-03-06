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
}
