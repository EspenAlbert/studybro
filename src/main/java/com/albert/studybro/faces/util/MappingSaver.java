package com.albert.studybro.faces.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author espen1
 */
@Named
@SessionScoped
public class MappingSaver implements Serializable{

    public MappingSaver() {
        listEntry = new HashMap<>();
    }
    
    private HashMap<String, Object> listEntry;

    public void updateListEntries(List collection) {
        for (Object la : collection) {
            if (listEntry.containsKey(la.toString())) {
                continue;
            }
            listEntry.put(la.toString(), la);
        }
    }

    public HashMap<String, Object> getListEntry() {
        return listEntry;
    }
}
