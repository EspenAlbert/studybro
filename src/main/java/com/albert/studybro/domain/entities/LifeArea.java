package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.interfaces.IHaveParent;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
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
@Table(name = "lifearea")
public class LifeArea implements Serializable, IHaveParent {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idlifearea")
    private Integer idlifearea;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 145)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @ManyToOne
    private User iduser;
    @OneToMany(mappedBy = "idlifearea")
    private List<LifeAreaCategory> lifeareaCategoryList;

    public LifeArea() {
    }

    public LifeArea(Integer idlifearea) {
        this.idlifearea = idlifearea;
    }


    public LifeArea(User user, String newName, String newDescription) {
        this.iduser = user;
        this.name = newName;
        this.description = newDescription;
    }

    public Integer getIdlifearea() {
        return idlifearea;
    }

    public void setIdlifearea(Integer idlifearea) {
        this.idlifearea = idlifearea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    public List<LifeAreaCategory> getLifeareaCategoryList() {
        return lifeareaCategoryList;
    }

    public void setLifeareaCategoryList(List<LifeAreaCategory> lifeareaCategoryList) {
        this.lifeareaCategoryList = lifeareaCategoryList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlifearea != null ? idlifearea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LifeArea)) {
            return false;
        }
        LifeArea other = (LifeArea) object;
        if ((this.idlifearea == null && other.idlifearea != null) || (this.idlifearea != null && !this.idlifearea.equals(other.idlifearea))) {
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
        this.iduser.getLifeareaList().remove(this);
    }

    @Override
    public void updateParent() {
        this.iduser.getLifeareaList().remove(this);
        this.iduser.getLifeareaList().add(this);
    }

}
