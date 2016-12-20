package ru.cpsmi.jee;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ProfilingAnnotationPostProcessor implements BeanPostProcessor {
    private Map<String, Class> map = new HashMap<>();
    private ProfilingController controller = new ProfilingController();

    public ProfilingAnnotationPostProcessor() throws Exception {
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        server.registerMBean(controller, new ObjectName("profiling", "name", "controller"));
    }

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Class beanClass = o.getClass();
        if (beanClass.isAnnotationPresent(Profiling.class)) {
            //map.put(s, beanClass);
        }
        return o;
    }


    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        Class beanClass = o.getClass();
        if (beanClass != null) {
            return Proxy.newProxyInstance(beanClass.getClassLoader(), beanClass.getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    if (controller.isEnabled()) {
                        System.out.println("Start logging");
                        long start = System.nanoTime();
                        Object retVal = method.invoke(o, args);
                        long end = System.nanoTime();
                        System.out.println("Eng logging" + (end - start));
                        return retVal;
                    } else {
                        return method.invoke(o, args);
                    }

                }
            });
        }
        return o;
    }

}
