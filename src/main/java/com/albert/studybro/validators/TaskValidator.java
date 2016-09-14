package com.albert.studybro.validators;

import com.albert.studybro.faces.util.Helper;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author espen1
 */
@FacesValidator("taskValidator")
public class TaskValidator implements Validator {

    public TaskValidator() {
    }

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        Object selectTaskType = Helper.sessionMap().get(Helper.getResourceName("selectedTaskType"));
        if (selectTaskType == null) {
            throw new ValidatorException(new FacesMessage("You have to select task type!"));
        }
        Object selectedLifeAreaCategory = Helper.sessionMap().get(Helper.getResourceName("selectedLifeAreaCategory"));
        if (selectedLifeAreaCategory == null) {
            throw new ValidatorException(new FacesMessage("You have to select life area category!"));
        }
        Object selectedLifeArea = Helper.sessionMap().get(Helper.getResourceName("selectedLifeAreaCategory"));
        if (selectedLifeArea == null) {
            throw new ValidatorException(new FacesMessage("You have to select life area!"));
        }
        System.out.println("Validation success!");
    }
}
