/***************************************************************************************
*    Title:         CompanyReviewEntity.java
*    Purpose:       LIST OF COMPANY REVIEWS PROVIDED BY CAMPUS USERS
*    Author:        TAN CHIN WEE
*    Credits:       CHEN MENG, NIGEL LEE TJON YI, TAN CHIN WEE, ZHU XINYI
*    Date:          31 JANUARY 2018
*    Code version:  1.0
*    Availability:  RESTRICTED
*
***************************************************************************************/
package unifyentities.voices;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;
import unifyentities.voices.CompanyEntity;

@Entity(name = "CompanyReview")
public class CompanyReviewEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long reviewID;
    private String reviewTitle;
    private Double reviewRating;
    private String reviewPros;
    private String reviewCons;
    private String reviewEmpType;
    private int reviewThumbsUp;
    private String reviewSalaryRange;
    private CompanyEntity reviewedCompany = new CompanyEntity();

    @Temporal(TemporalType.DATE)
    private Date reviewDate;

    /* FOREIGN KEY */
    private String reviewPosterID;

    @PrePersist
    public void creationDate() { this.reviewDate = new Date(); }

    public CompanyReviewEntity() { this.setReviewID(System.nanoTime()); }

    public void create(String reviewTitle, double reviewRating, String reviewPros, String reviewCons, String reviewEmpType, String reviewSalaryRange, CompanyEntity reviewedCompany, String reviewPosterID) {
      this.reviewTitle = reviewTitle;
      this.reviewRating = reviewRating;
      this.reviewPros = reviewPros;
      this.reviewCons = reviewCons;
      this.reviewEmpType = reviewEmpType;
      this.reviewThumbsUp = 0;
      this.reviewSalaryRange = reviewSalaryRange;
      this.reviewedCompany = reviewedCompany;
      this.reviewPosterID = reviewPosterID;
      creationDate();
    }

    /* GETTER METHODS */
    public Long getReviewID() { return reviewID; }
    public String getReviewTitle() { return reviewTitle; }
    public Double getReviewRating() { return reviewRating; }
    public String getReviewPros() { return reviewPros; }
    public String getReviewCons() { return reviewCons; }
    public String getReviewEmpType() { return reviewEmpType; }
    public int getReviewThumbsUp() { return reviewThumbsUp; }
    public String getReviewSalaryRange() { return reviewSalaryRange; }
    public Date getReviewDate() { return reviewDate; }
    public String getReviewPosterID() { return reviewPosterID; }
    @ManyToOne
    public CompanyEntity getReviewedCompany() { return reviewedCompany; }

    /* SETTER METHODS */
    public void setReviewID(Long reviewID) { this.reviewID = reviewID; }
    public void setReviewTitle(String reviewTitle) { this.reviewTitle = reviewTitle; }
    public void setReviewRating(Double reviewRating) { this.reviewRating = reviewRating; }
    public void setReviewPros(String reviewPros) { this.reviewPros = reviewPros; }
    public void setReviewCons(String reviewCons) { this.reviewCons = reviewCons; }
    public void setReviewEmpType(String reviewEmpType) { this.reviewEmpType = reviewEmpType; }
    public void setReviewThumbsUp(int reviewThumbsUp) { this.reviewThumbsUp = reviewThumbsUp; }
    public void setReviewSalaryRange(String reviewSalaryRange) { this.reviewSalaryRange = reviewSalaryRange; }
    public void setReviewDate(Date reviewDate) { this.reviewDate = reviewDate; }
    public void setReviewPosterID(String reviewPosterID) { this.reviewPosterID = reviewPosterID; }
    public void setReviewedCompany(CompanyEntity reviewedCompany) { this.reviewedCompany = reviewedCompany; }
}