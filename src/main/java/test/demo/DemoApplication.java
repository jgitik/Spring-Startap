package test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import quoters.TerminatorQuoter;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		context.getBean(TerminatorQuoter.class).SayQuote();

		//ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml")
		//SpringApplication.run(DemoApplication.class, args);
	}



}
