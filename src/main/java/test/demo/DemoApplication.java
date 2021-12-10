package test.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import quoters.Quoter;
import quoters.TerminatorQuoter;

@SpringBootApplication
public class DemoApplication {
	//visualVM - можно запустить чтобы управлять догированием.
	public static void main(String[] args) throws InterruptedException {
		ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
		//context.getBeanDefinitionNames() - так мы можем посмтреть все бины
		/*
		while (true) {
			Thread.sleep(100);
			//context.getBean(TerminatorQuoter.class).SayQuote();
			// если мы вызовем бин по классу, то наша прокси поменяет название класса(придумает его само)
			//поэтому при работу через прокси надоставить через интерфейс. Тогда подойдет старый бин и новый
			context.getBean(Quoter.class).SayQuote();
		}

		 */

		//ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml")
		//SpringApplication.run(DemoApplication.class, args);
	}



}
