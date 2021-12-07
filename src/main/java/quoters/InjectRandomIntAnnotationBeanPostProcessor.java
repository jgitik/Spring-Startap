package quoters;

import org.apache.el.util.ReflectionUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Random;

public class InjectRandomIntAnnotationBeanPostProcessor implements BeanPostProcessor {
    //если чужой интерфес не кидает эксепшен, то и мой не может кинуть
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields= bean.getClass().getDeclaredFields();
        for (Field field: fields){
           InjectRandomInt annotation=  field.getAnnotation(InjectRandomInt.class);
            if (annotation!=null){
                int max= annotation.max();
                int min= annotation.min();
                Random random= new Random();
                int i= min+random.nextInt(max-min);
                field.setAccessible(true); // Чтобы внедрить random
                ReflectionUtils.setField(field,bean,i);
                // Обычный рефлекшен, но трай/кетч внутри метода(не надо писать)
                // нужно указать для какого филда, для какого объекта использовать и значение

            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return BeanPostProcessor.super.postProcessAfterInitialization(bean, beanName);
    }
}
