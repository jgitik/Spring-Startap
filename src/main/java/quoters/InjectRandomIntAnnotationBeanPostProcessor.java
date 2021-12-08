package quoters;

import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;
//чтобы расширить spring Нужно имплементировать BeanPostProcessor, который содержит 2 метода
public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {
    //если чужой интерфес не кидает эксепшен, то и мой не может кинуть
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields= bean.getClass().getDeclaredFields();
        for (Field field: fields){
            //проверяем есть ли данная аннотация у бина
           InjectRandomInt annotation=  field.getAnnotation(InjectRandomInt.class);
            if (annotation!=null){
                int max= annotation.max();
                int min= annotation.min();
                Random random= new Random();
                int i= min+random.nextInt(max-min);
                // Чтобы внедрить  random если данное поле private
                field.setAccessible(true);
                // Обычный рефлекшен, но трай/кетч внутри метода(не надо писать)
                // нужно указать для какого филда, для какого объекта использовать и значение
                 ReflectionUtils.setField(field,bean,i);


            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
