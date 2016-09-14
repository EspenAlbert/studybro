package com.albert.studybro.interceptors;

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
@ArgumentHelperNoKey
@Priority(Interceptor.Priority.APPLICATION + 3)
public class ArgumentHelperNoKeyInterceptor implements Serializable {

    @AroundInvoke
    public Object intercept(InvocationContext invocationContext) throws Exception {
        Object[] parameters = invocationContext.getParameters();
        boolean skip = true;
        for (Object o : parameters) {
            if (o == null) {
                skip = false;
                break;
            }
        }
        if (!skip) {
            try {
                String key = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
                        "objectString");
                Object[] newParameters = new Object[parameters.length];
                Map<String, Object> map = (Map<String, Object>) invocationContext.getContextData().get(
                        ObjectMapperInterceptor.objectMap);
                newParameters[0] = map.get(key);
                newParameters[1] = parameters[1];
                invocationContext.setParameters(newParameters);
                return invocationContext.proceed();
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage("mainForm", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Interceptor error!", e.getMessage()));
                return null;
            }
        } 
        return invocationContext.proceed();
    }
}
