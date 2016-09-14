package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.interfaces.IHaveParent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 *
 * @author espen1
 */
@Entity
@Table(name = "task_type")
public class TaskType implements Serializable, IHaveParent {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtask_type")
    private Integer idtaskType;
    @Size(max = 45)
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "idtaskType")
    private List<Task> taskList;
    @JoinTable(name = "user_task_type", joinColumns = {
        @JoinColumn(name = "idtask_type", referencedColumnName = "idtask_type")}, inverseJoinColumns = {
        @JoinColumn(name = "iduser", referencedColumnName = "iduser")})
    @ManyToMany
    private List<User> userList;

    public TaskType() {
    }

    public TaskType(Integer idtaskType) {
        this.idtaskType = idtaskType;
    }

    public TaskType(User user, String newName) {
        userList = new ArrayList<>();
        userList.add(user);
        this.name = newName;
    }

    public Integer getIdtaskType() {
        return idtaskType;
    }

    public void setIdtaskType(Integer idtaskType) {
        this.idtaskType = idtaskType;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtaskType != null ? idtaskType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TaskType)) {
            return false;
        }
        TaskType other = (TaskType) object;
        if ((this.idtaskType == null && other.idtaskType != null) || (this.idtaskType != null && !this.idtaskType.equals(other.idtaskType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return name;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    //Needs to be refactored in case of common task types between users...
    @Override
    public void removeFromParent() {
        for(User u: userList) {
            u.getTaskTypeList().remove(this);
        }
    }

    @Override
    public void updateParent() {
        for(User u: userList) {
            u.getTaskTypeList().remove(this);
            u.getTaskTypeList().add(this);
        }
    }

}
