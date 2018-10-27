
   ***BeanPostProcessor***
      ***InstantiationAwareBeanPostProcessor***
         ***SmartInstantiationAwareBeanPostProcessor***
            **InstantiationAwareBeanPostProcessorAdapter**


public interface BeanPostProcessor {
    Object postProcessBeforeInitialization(Object var1, String var2) throws BeansException;

    Object postProcessAfterInitialization(Object var1, String var2) throws BeansException;
}

public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {
    Object postProcessBeforeInstantiation(Class<?> var1, String var2) throws BeansException;

    boolean postProcessAfterInstantiation(Object var1, String var2) throws BeansException;

    PropertyValues postProcessPropertyValues(PropertyValues var1, PropertyDescriptor[] var2, Object var3, String var4) throws BeansException;
}

public interface SmartInstantiationAwareBeanPostProcessor extends InstantiationAwareBeanPostProcessor {
    Class<?> predictBeanType(Class<?> var1, String var2) throws BeansException;

    Constructor<?>[] determineCandidateConstructors(Class<?> var1, String var2) throws BeansException;

    Object getEarlyBeanReference(Object var1, String var2) throws BeansException;
}

public abstract class InstantiationAwareBeanPostProcessorAdapter implements SmartInstantiationAwareBeanPostProcessor {
    public InstantiationAwareBeanPostProcessorAdapter() {
    }

    public Class<?> predictBeanType(Class<?> beanClass, String beanName) {
        return null;
    }

    public Constructor<?>[] determineCandidateConstructors(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    public Object getEarlyBeanReference(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        return true;
    }

    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        return pvs;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
