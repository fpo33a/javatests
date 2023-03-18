// example of injection of dependency
// the object "ts0" is created through usage of @AutoInject annotation
// once instanciated we can call method 'printSomething' of that TestClass0 object

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class AnotherMain {

    // will be instanciated by injection dependency using @AutoInject annotation
    private SampleClass sampleClass;

    private int val;

    public int getVal() {
        return this.val;
    }

    public void setVal(int v) {
        this.val = v;
    }


    // this is a read method
    public SampleClass getSampleClass() {
        return this. sampleClass;
    }

    // this is a write method
    @AutoInjectDependence
    public void setSampleClass(SampleClass ts) {
        this.sampleClass = ts;
    }


    public static void main(String[] args) {
        // Create instance
        AnotherMain main = new AnotherMain();
        injectDependencies(main).getSampleClass().doPrint();
    }


    public static <T> T injectDependencies(final T obj) {
        try {
            Class clazz = obj.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                Method readMethod = pd.getReadMethod();
                Method writeMethod = pd.getWriteMethod();

                if (writeMethod == null) {
                    continue;
                }

                // invoke the read method ( getSampleClass in this case )
                Object allocatedSampleVal = pd.getReadMethod().invoke(obj);

                // if object not yet created ( this is the case at first call ), invoke setSampleClass with a newInstance of class (this fuunction has 1 arg )
                if (allocatedSampleVal == null) {
                    for (Annotation annotation : writeMethod.getAnnotations()) {
                        // if write method has annotation AutoInject
                        if (annotation instanceof AutoInjectDependence) {
                            Class propertyType = pd.getPropertyType();
                            writeMethod.invoke(obj, propertyType.newInstance());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
