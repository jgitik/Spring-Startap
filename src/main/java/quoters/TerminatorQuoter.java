package quoters;

import javax.annotation.PostConstruct;
import java.sql.SQLOutput;


@Profiling
//Если у данного метода есть такая аннтация, то выполнялось бы профилирование
//Т.е в лог выводилось время работы методов класса
public class TerminatorQuoter implements Quoter {
    @InjectRandomInt(min = 2, max = 7)
    private int repeat;

    private String message;

    public TerminatorQuoter() {
        System.out.println("Отработал конструктор терминатора");
        System.out.println("Phase1");
    }

    public void setMessage(String message) {
        this.message = message;
    }

   //@PostConstruct - не можем поставить потому что ее обрабатывает BeanPostProcessor,
    // а в нем еще нет переменной repeat
@PostConstruct
    public void init(){
        //переменная repeat создается спрингом, поэтому можем ее вызвать в инит методе
        System.out.println(repeat);
        System.out.println("phase2");
    }

    @Override
    @PostProxy
    //Данная аннотация должна запуститься когда весь спринг уже настроен, методы все сгенерировались
    public void SayQuote() {
        System.out.println("Phase3");
        System.out.println(repeat);
        for (int i = 0; i < repeat; i++) {
            System.out.println("message" + message);
        }
    }
}
