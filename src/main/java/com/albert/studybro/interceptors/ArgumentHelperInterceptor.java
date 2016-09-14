package com.albert.studybro.interceptors;

import com.albert.studybro.faces.util.Helper;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.Priority;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 *
 * @author espen1
 */
@Interceptor
@ArgumentHelper
@Priority(Interceptor.Priority.APPLICATION + 2)
public class ArgumentHelperInterceptor implements Serializable {

    @AroundInvoke
    public Object intercept(InvocationContext invocationContext) throws Exception {
        Object[] parameters = invocationContext.getParameters();
        try {
            Map<String, Object> objectMap = (Map<String, Object>) invocationContext.getContextData().get(
                    ObjectMapperInterceptor.objectMap);
            String[] resourceKeys = invocationContext.getMethod().getAnnotation(ArgumentHelper.class).resourceKeys();
            Object[] newParameters = new Object[parameters.length];
            int index = 0;
            for (String key : resourceKeys) {
                newParameters[index] = objectMap.get(Helper.sessionMap().get(Helper.getResourceName(key)));
                index ++;
            }
            invocationContext.setParameters(newParameters);
            return invocationContext.proceed();
        } catch (Exception e) {
            System.out.println("Exception in interceptor: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage("mainForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Interceptor error!", e.getMessage()));
            return null;
        }
    }
}
