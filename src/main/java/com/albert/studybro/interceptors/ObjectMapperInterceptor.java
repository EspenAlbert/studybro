package com.albert.studybro.interceptors;

import com.albert.studybro.faces.util.MappingSaver;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;
import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author espen1
 */
@AddToObjectMapper
@Interceptor
@Priority(Interceptor.Priority.APPLICATION + 1)
public class ObjectMapperInterceptor implements Serializable {
    public final static String objectMap = "objectMap";

    @Inject
    MappingSaver ms;
    
    public ObjectMapperInterceptor() {
    }
    
    private void updateListEntries(List collection) {
        ms.updateListEntries(collection);
    }

    @AroundInvoke
    public Object intercept(InvocationContext invocationContext) throws Exception {
        Method m = invocationContext.getMethod();
        AddToObjectMapper addToObject = m.getAnnotation(AddToObjectMapper.class);
        ArgumentHelper argumentHelper = m.getAnnotation(ArgumentHelper.class);
        ArgumentHelperNoKey argumentHelper2 = m.getAnnotation(ArgumentHelperNoKey.class);
        if (argumentHelper != null || argumentHelper2 != null) {
            invocationContext.getContextData().put(objectMap, ms.getListEntry());
        }
        Object proceed =  invocationContext.proceed();
        if (argumentHelper == null && argumentHelper2 == null && addToObject != null) {
            updateListEntries((List) m.invoke(invocationContext.getTarget(), null));
        }
        return proceed;
        
    }
}
