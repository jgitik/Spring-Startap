package quoters;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingHandlerBeanPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<>();
    private ProfilingController controller = new ProfilingController();
    //включает Profiling


    public ProfilingHandlerBeanPostProcessor() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException {
        //Реализуем Java Management Extensions (JMX) для внешнего управления нашей программы
        MBeanServer platformMBeanServer = ManagementFactory.getPlatformMBeanServer();

        //  Получаем инстанс бинсервера, в котором  можно регистрировать бины
        //Подбробнее можно прочитать https://javascopes.com/java-management-extensions-812ae375/
        platformMBeanServer.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
        //регистрируем данный бин в мбинсервере(через него управляются ресурсы)
        // передаем ему контроллер, и создаем имя чтобы найти можно было его через JMX консоль
        //new ObjectName (под какой папочкой будет находится- домайн)
        //но в соответствии с соглашениями об именовании MBean он должен иметь имя пакета Java (позволяет избежать конфликтов имен)
        //имя- контроллер
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        //должны запомнить имена всех бинов(beanName) с которыми будем работать
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            map.put(beanName, beanClass);

        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class beanClass = map.get(beanName);
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {


                      //Здесь можно обернуть через лямду. Если забыл, можно посмотреть как она работает.
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (controller.isEnabled()) {
                                System.out.println("Профилирую");
                                long before = System.nanoTime();
                                Object retVal = method.invoke(bean, args);
                                long after = System.nanoTime();
                                System.out.println(after - before);
                                System.out.println("Закончил профилировать...");
                                return retVal;
                            } else return method.invoke(bean, args);
                            //мы считаем наносекунды если включен переключатель контроллера на енабле

                        }
                    }
            );
            //создаем объект из нового класса который на лету сгенерит сам
            //принимает ClassLoader при помощи которого класс новый загрузится в хиап
            //(beanClass.getClassLoader()- любой класс знает какой класлоадер его загрузил, простокопируем из него)
            //список интерфейсов которые будут у нового и старого класса(берем тоже из бина)
            //Инвокейшн Хендлер - вся логика которую мы хотим добавить в прокси
        }
        return bean;
    }
}
