package com.albert.studybro.faces.util;

import com.albert.studybro.domain.entities.Task;
import com.albert.studybro.domain.entities.User;
import com.albert.studybro.domain.entities.WorkSession;
import com.albert.studybro.ejb.RequestHandlerLocal;
import com.albert.studybro.events.ActivityEvent;
import com.albert.studybro.interceptors.ArgumentHelperNoKey;
import com.albert.studybro.observers.ActivityLogger;
import com.albert.studybro.faces.util.Helper;
import java.io.IOException;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIPanel;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.facelets.FaceletContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author espen1
 */
@Named
@SessionScoped
public class ObjectEditor implements Serializable {

    private int idNumber;
    private ActivityEvent eventBeingEdited;
    @Inject
    RequestHandlerLocal requestHandler;
    @Inject
    ActivityLogger activityLogger;

    public ObjectEditor() {
        idNumber = 1;
    }

    public void createComponentFromActivityEvent(ActionEvent ae) {
        eventBeingEdited = (ActivityEvent) ae.getComponent().getAttributes().get("activeObject");
        Helper.sessionMap().put("activeObject", eventBeingEdited.getObject());
        includeCompositeComponent(eventBeingEdited.getEditComponent(), "editComponent" + idNumber);
        idNumber += 1;
    }

    @ArgumentHelperNoKey()
    public String createEditComponentPage(Object object, String editComponent) {
        Helper.sessionMap().put("activeObject", object);
        includeCompositeComponent(editComponent, "editComponent" + idNumber++);
        return "";
    }

    public String createEditComponentPageIdInParams(String editComponent) throws ClassNotFoundException {
        final String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("objectId");
        final String id2 = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(
                "objectType");
        Class<?> type = Class.forName(id2.substring(6));
        Object o = requestHandler.find(Integer.parseInt(id), type);
        User user = (User) Helper.sessionMap().get("user");
        if (o instanceof Task) {
            ((Task) o).setIduser(user);
        }

        Helper.sessionMap().put("activeObject", o);
        includeCompositeComponent(editComponent, "editComponent" + idNumber++);
        return "";

    }

    public void close(ActionEvent ae) {
        removeDialog(ae);
    }

    public void edit(ActionEvent ae) throws IOException {
        Object object = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("activeObject");
        requestHandler.edit(object);
        removeDialog(ae);
        refreshPage();
    }

    private void removeDialog(ActionEvent ae) {
        UIComponent dialog = ae.getComponent().getParent().getParent();
        final UIComponent parent = dialog.getParent();
        parent.getChildren().remove(dialog);
    }

    public void delete(ActionEvent ae) throws IOException {
        Object deleteObject = Helper.sessionMap().get("activeObject");
        System.out.println("Deleting :" + deleteObject);
        removeDialog(ae);
        requestHandler.delete(deleteObject);
        activityLogger.removeActitivtyEvent(eventBeingEdited);
        refreshPage();

    }

    private void refreshPage() throws IOException {
        final ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        HttpServletRequest servletRequest = (HttpServletRequest) externalContext.getRequest();
        externalContext.redirect(servletRequest.getContextPath() + FacesContext.getCurrentInstance().getViewRoot().getViewId());
    }

    public static void includeCompositeComponent(String resourceName, String id) {
        // Prepare.
        String libraryName = "emcomp";
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        FaceletContext faceletContext = (FaceletContext) context.getAttributes().get(FaceletContext.FACELET_CONTEXT_KEY);

        //Find parent
        UIComponent parent = context.getViewRoot().findComponent("formForDialog");
        // This basically creates <ui:component> based on <composite:interface>.
        Resource resource = application.getResourceHandler().createResource(resourceName, libraryName);
        UIComponent composite = application.createComponent(context, resource);
        composite.setId(id); // Mandatory for the case composite is part of UIForm! Otherwise JSF can't find inputs.

        // This basically creates <composite:implementation>.
        UIComponent implementation = application.createComponent(UIPanel.COMPONENT_TYPE);
        implementation.setRendererType("javax.faces.Group");
        composite.getFacets().put(UIComponent.COMPOSITE_FACET_NAME, implementation);

        // Now include the composite component file in the given parent.
        parent.getChildren().add(composite);
        parent.pushComponentToEL(context, composite); // This makes #{cc} available.
        try {
            faceletContext.includeFacelet(implementation, resource.getURL());
        } catch (IOException e) {
            throw new FacesException(e);
        } finally {
            parent.popComponentFromEL(context);
        }
    }

}
