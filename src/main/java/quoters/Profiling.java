package quoters;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
//необхоидимо чтобы Из BeanFactory Bean першел в BeanPostProcessor, сработал метод postProcessorBeforeInt
//После этого вернулся в BeanFactory и подменил Объект в BeanFactory другим, если включен булеан
//Новый созданный класс должен имплементировать те же самые интерфейсы что и подменяемый класс
//Dynamic Proxy
public @interface Profiling {
}
