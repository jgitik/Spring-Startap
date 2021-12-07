package quoters;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// sourse- видна в сорсах, например Overrate
// class - сложная ненужная
// Рантайм в работе проги, как правило очень нужная
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectRandomInt {
    int min();
    int max();
}
