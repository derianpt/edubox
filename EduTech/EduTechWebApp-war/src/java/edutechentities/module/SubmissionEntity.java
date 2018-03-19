/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edutechentities.module;

import commoninfraentities.UserEntity;
import edutechentities.common.AttachmentEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Derian
 */
@Entity(name="Submission")
@XmlRootElement
public class SubmissionEntity implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private LocalDateTime openDate;
    private LocalDateTime closeDate;
    @OneToMany
    private Collection<AttachmentEntity> submissions;
    @OneToOne
    private ModuleEntity module;
    @OneToOne
    private UserEntity createdBy;
    private LocalDateTime createdAt; 
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SubmissionEntity)) {
            return false;
        }
        SubmissionEntity other = (SubmissionEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "edutechentities.module.SubmissionEntity[ id=" + id + " ]";
    }
    
}