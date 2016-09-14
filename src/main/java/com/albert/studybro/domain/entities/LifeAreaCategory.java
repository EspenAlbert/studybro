package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.interfaces.IHaveParent;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author espen1
 */
@Entity
@Table(name = "lifearea_category")
public class LifeAreaCategory implements Serializable, IHaveParent {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlifearea_category")
    private Integer idlifeareaCategory;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idlifeareaCategory")
    private List<Task> taskList;
    @JoinColumn(name = "idlifearea", referencedColumnName = "idlifearea")
    @ManyToOne
    private LifeArea idlifearea;

    public LifeAreaCategory() {
    }

    public LifeAreaCategory(Integer idlifeareaCategory) {
        this.idlifeareaCategory = idlifeareaCategory;
    }

    public LifeAreaCategory(String newName, LifeArea lifearea) {
        this.name = newName;
        this.idlifearea = lifearea;
    }

    public Integer getIdlifeareaCategory() {
        return idlifeareaCategory;
    }

    public void setIdlifeareaCategory(Integer idlifeareaCategory) {
        this.idlifeareaCategory = idlifeareaCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public LifeArea getIdlifearea() {
        return idlifearea;
    }

    public void setIdlifearea(LifeArea idlifearea) {
        this.idlifearea = idlifearea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlifeareaCategory != null ? idlifeareaCategory.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LifeAreaCategory)) {
            return false;
        }
        LifeAreaCategory other = (LifeAreaCategory) object;
        if ((this.idlifeareaCategory == null && other.idlifeareaCategory != null) || (this.idlifeareaCategory != null && !this.idlifeareaCategory.equals(other.idlifeareaCategory))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public void removeFromParent() {
        idlifearea.getLifeareaCategoryList().remove(this);
    }

    @Override
    public void updateParent() {
        idlifearea.getLifeareaCategoryList().remove(this);
        idlifearea.getLifeareaCategoryList().add(this);
    }

}
