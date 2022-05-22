package bdtc.lab2.helpers;

import bdtc.lab2.controller.model.NewsEventStat;
import bdtc.lab2.model.NewsEventStatEntity;
import bdtc.lab2.model.NewsInteractionEntity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class MapComputeTaskArg {
    private List<NewsInteractionEntity> arg;
    private Method method;
    private Object object;

    public void invoke(List<NewsEventStatEntity> newsEventStatsList) throws InvocationTargetException, IllegalAccessException {
        Object[] parameters = new Object[1];
        parameters[0] = newsEventStatsList;
        method.invoke(object,parameters);
    }
    public MapComputeTaskArg(List<NewsInteractionEntity> arg, Method method, Object object){
        this.arg = arg;
        this.method = method;
        this.object = object;
    }

    public MapComputeTaskArg(Method method, Object object){
        this.method = method;
        this.object = object;
    }

    public List<NewsInteractionEntity> getArg() {
        return arg;
    }

    public void setArg(List<NewsInteractionEntity> arg) {
        this.arg = arg;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
