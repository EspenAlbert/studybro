package com.albert.studybro.domain.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author espen1
 */
@Entity
@Table(name = "user")
public class User implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iduser")
    private Integer iduser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "username")
    private String username;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 45)
    @Column(name = "email")
    private String email;
    @Size(max = 45)
    @Column(name = "password")
    private String password;
    @Size(max = 45)
    @Column(name = "salt")
    private String salt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iduser")
    private List<WorkSession> workSessionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iduser")
    private List<Task> taskList;
    @OneToMany(mappedBy = "iduser")
    private List<LifeArea> lifeareaList;
    @ManyToMany(mappedBy = "userList")
    private List<TaskType> taskTypeList;

    public User() {
    }

    public User(Integer iduser) {
        this.iduser = iduser;
    }

    public User(Integer iduser, String username) {
        this.iduser = iduser;
        this.username = username;
    }

    public User(String username, String password) {
        this.username= username;
        this.password = password;
    }

    public Integer getIduser() {
        return iduser;
    }

    public void setIduser(Integer iduser) {
        this.iduser = iduser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public List<WorkSession> getWorkSessionList() {
        return workSessionList;
    }

    public void setWorkSessionList(List<WorkSession> workSessionList) {
        this.workSessionList = workSessionList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public List<LifeArea> getLifeareaList() {
        return lifeareaList;
    }

    public void setLifeareaList(List<LifeArea> lifeareaList) {
        this.lifeareaList = lifeareaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iduser != null ? iduser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.iduser == null && other.iduser != null) || (this.iduser != null && !this.iduser.equals(other.iduser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.albert.studybro.domain.entities.User[ iduser=" + iduser + " ]";
    }

    public List<TaskType> getTaskTypeList() {
        return taskTypeList;
    }

    public void setTaskTypeList(List<TaskType> taskTypeList) {
        this.taskTypeList = taskTypeList;
    }

}
