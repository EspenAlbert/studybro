package com.albert.studybro.domain.entities;

import com.albert.studybro.domain.entities.interfaces.IHaveParent;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import org.joda.time.DateTime;

/**
 *
 * @author espen1
 */
@Entity
@Table(name = "work_session")
public class WorkSession implements Serializable, IHaveParent {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idwork_session")
    private Integer idworkSession;
    @Column(name = "start_date_and_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDateAndTime;
    @Column(name = "end_date_and_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDateAndTime;
    @Size(max = 240)
    @Column(name = "notes")
    private String notes;
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    @ManyToOne(optional = false)
    private User iduser;
    @JoinColumn(name = "idtask", referencedColumnName = "idtask")
    @ManyToOne(optional = false)
    private Task idtask;

    public WorkSession() {
    }

    public WorkSession(Integer idworkSession) {
        this.idworkSession = idworkSession;
    }

    public WorkSession(Task task, DateTime startOnTask, DateTime finishWorkSession, User user, String notes) {
        idtask = task;
        startDateAndTime = startOnTask.toDate();
        endDateAndTime = finishWorkSession.toDate();
        iduser = user;
        this.notes = notes;
    }

    public Integer getIdworkSession() {
        return idworkSession;
    }

    public void setIdworkSession(Integer idworkSession) {
        this.idworkSession = idworkSession;
    }

    public Date getStartDateAndTime() {
        return startDateAndTime;
    }

    public void setStartDateAndTime(Date startDateAndTime) {
        this.startDateAndTime = startDateAndTime;
    }

    public Date getEndDateAndTime() {
        return endDateAndTime;
    }

    public void setEndDateAndTime(Date endDateAndTime) {
        this.endDateAndTime = endDateAndTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getIduser() {
        return iduser;
    }

    public void setIduser(User iduser) {
        this.iduser = iduser;
    }

    public Task getIdtask() {
        return idtask;
    }

    public void setIdtask(Task idtask) {
        this.idtask = idtask;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idworkSession != null ? idworkSession.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WorkSession)) {
            return false;
        }
        WorkSession other = (WorkSession) object;
        if ((this.idworkSession == null && other.idworkSession != null) || (this.idworkSession != null && !this.idworkSession.equals(
                other.idworkSession))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.albert.studybro.domain.entities.WorkSession[ idworkSession=" + idworkSession + " ]";
    }

    @Override
    public void removeFromParent() {
        int taskIndex = iduser.getTaskList().indexOf(idtask);
        iduser.getTaskList().get(taskIndex).getWorkSessionList().remove(this);
        idtask.getWorkSessionList().remove(this);
    }

    @Override
    public void updateParent() {
        int taskIndex = iduser.getTaskList().indexOf(idtask);
        iduser.getTaskList().get(taskIndex).getWorkSessionList().remove(this);
        iduser.getTaskList().get(taskIndex).getWorkSessionList().add(this);
        idtask.getWorkSessionList().remove(this);
        idtask.getWorkSessionList().add(this);
    }

}
