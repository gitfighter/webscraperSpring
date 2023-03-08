/** main class for instructions
 * mvn clean compile assembly:single */

package engine.webcrawler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebscraperSpring
{

	public static void main(String[] args)
	{
				SpringApplication.run(WebscraperSpring.class, args);

		var router=new Router();
	}
}
