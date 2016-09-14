package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.interfaces.IHaveParent;
import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author espen1
 */
@Entity
@Table(name = "task")
public class Task implements Serializable, IHaveParent {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtask")
    private Integer idtask;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "name")
    private String name;
    @Size(max = 240)
    @Column(name = "description")
    private String description;
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "importance")
    private Integer importance;
    @Column(name = "joy")
    private Integer joy;
    @Column(name = "estimated_hours")
    private Integer estimatedHours;
    @Column(name = "estimated_minutes")
    private Integer estimatedMinutes;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtask")
    private List<WorkSession> workSessionList;
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User iduser;
    @JoinColumn(name = "idlifearea_category", referencedColumnName = "idlifearea_category")
    @ManyToOne(optional = false)
    private LifeAreaCategory idlifeareaCategory;
    @JoinColumn(name = "idtask_type", referencedColumnName = "idtask_type")
    @ManyToOne
    private TaskType idtaskType;
    @Column(name = "complete")
    private Boolean complete;
    @Transient
    private boolean editable = false;

    public Task() {
    }

    public Task(Integer idtask) {
        this.idtask = idtask;
    }

    public Task(Integer idtask, String name) {
        this.idtask = idtask;
        this.name = name;
    }

    public Task(User user, TaskType selectedTaskType, LifeAreaCategory selectedLifeAreaCategory, String taskName, String taskDescription, Date dueDate, int importance, int joy, int estimatedHours, int estimatedMinutes) {
        this.iduser = user;
        this.idtaskType = selectedTaskType;
        this.idlifeareaCategory = selectedLifeAreaCategory;
        this.name = taskName;
        this.description = taskDescription;
        this.dueDate = dueDate;
        this.importance = importance;
        this.joy = joy;
        this.estimatedHours = estimatedHours;
        this.estimatedMinutes = estimatedMinutes;
        complete = false;
    }

    public Integer getIdtask() {
        return idtask;
    }

    public void setIdtask(Integer idtask) {
        this.idtask = idtask;
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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getImportance() {
        return importance;
    }

    public void setImportance(Integer importance) {
        this.importance = importance;
    }

    public Integer getJoy() {
        return joy;
    }

    public void setJoy(Integer joy) {
        this.joy = joy;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(Integer estimatedMinutes) {
        this.estimatedMinutes = estimatedMinutes;
    }

    public List<WorkSession> getWorkSessionList() {
        return workSessionList;
    }

    public void setWorkSessionList(List<WorkSession> workSessionList) {
        this.workSessionList = workSessionList;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    public LifeAreaCategory getIdlifeareaCategory() {
        return idlifeareaCategory;
    }

    public void setIdlifeareaCategory(LifeAreaCategory idlifeareaCategory) {
        this.idlifeareaCategory = idlifeareaCategory;
    }

    public TaskType getIdtaskType() {
        return idtaskType;
    }

    public void setIdtaskType(TaskType idtaskType) {
        this.idtaskType = idtaskType;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtask != null ? idtask.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Task)) {
            return false;
        }
        Task other = (Task) object;
        if ((this.idtask == null && other.idtask != null) || (this.idtask != null && !this.idtask.equals(other.idtask))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.albert.studybro.domain.entities.Task[ idtask=" + idtask + " ]";
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public void removeFromParent() {
        iduser.getTaskList().remove(this);
    }

    @Override
    public void updateParent() {
        iduser.getTaskList().remove(this);
        iduser.getTaskList().add(this);
    }

    
}
