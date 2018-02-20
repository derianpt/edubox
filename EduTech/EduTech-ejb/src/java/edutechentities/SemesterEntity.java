/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edutechentities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Derian
 */
@Entity(name="Semester")
public class SemesterEntity implements Serializable {
    @Id @GeneratedValue
    private Long id;
    private String title;
    //private Collection<ScheduleEntity> keyEvents;
    @OneToMany(mappedBy = "semester")
    private List<ModuleEntity> modules;
    private Boolean activeStatus;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<ModuleEntity> getModules() {
        return modules;
    }
    public void setModules(List<ModuleEntity> modules) {
        this.modules = modules;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Boolean getActiveStatus() {
        return activeStatus;
    }
    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SemesterEntity)) {
            return false;
        }
        SemesterEntity other = (SemesterEntity) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edutechentities.SemesterEntity[ id=" + getId() + " ]";
    }
    
}