package com.albert.studybro.converters;

import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.joda.time.DateTime;

/**
 *
 * @author espen1
 */

@FacesConverter("showTimeConverter")
public class DateTimeShowTimeConverter implements Converter {

    /*
     * <p>The message identifier of the Message to be created if the conversion
     * fails. The message format string for this message may optionally include
     * <code>{0}</code> and
     * <code>{1}</code> placeholders, which will be replaced by the object and
     * value.</p>
     */
    public static final String CONVERSION_ERROR_MESSAGE_ID = "ConversionError";

    public DateTimeShowTimeConverter() {
    }

    Map<String, Object> objects;
    
    /**
     * <p>Parses the CreditCardNumber and strips any blanks or
     * <code>"-"</code> characters from it.</p>
     */
    @Override
    public Object getAsObject(FacesContext context,
            UIComponent component, String newValue)
            throws ConverterException {
        return newValue;
    }

    /**
     * Formats the value by inserting space after every four characters for
     * better readability if they don't already exist. In the process converts
     * any
     * <code>"-"</code> characters into blanks for consistency.
     */
    @Override
    public String getAsString(FacesContext context,
            UIComponent component, Object value)
            throws ConverterException {

        DateTime dt;
        // Value must be of a type that can be cast to a DateTime.
        try {
            dt = (DateTime) value;
            return dt.getHourOfDay() + ":" + ((dt.getMinuteOfHour() < 10) ? "0" + dt.getMinuteOfHour() : dt.getMinuteOfHour());
            
        } catch (ClassCastException ce) {
            FacesMessage errMsg = new FacesMessage(CONVERSION_ERROR_MESSAGE_ID);
            FacesContext.getCurrentInstance().addMessage(null, errMsg);
            throw new ConverterException(errMsg.getSummary());
        }

    }
}

