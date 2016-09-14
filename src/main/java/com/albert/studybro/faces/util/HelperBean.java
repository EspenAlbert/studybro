package com.albert.studybro.faces.util;

import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;


@Named
@ApplicationScoped
public class HelperBean {

    public HelperBean() {
    }

    public synchronized List getSmallerList(List list, int number) {
        ArrayList returnList = new ArrayList();
        for (int i = 0; i < number; i++) {
            if(i < list.size()) returnList.add(list.get(i));
        }
        return returnList;
    }
    public String reloadPage() {
        return FacesContext.getCurrentInstance().getViewRoot().getViewId();
    }
}
