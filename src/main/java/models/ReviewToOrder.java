/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author bayan
 */
public class ReviewToOrder {
  private  int id;
    private int executrId;
    private int customerId; //personId
    private String review_text;
    private int  assessment;
    private Long createdDate;
    private java.util.ArrayList<AnswerToReview> answers;

    public ReviewToOrder(){};
    
    public ReviewToOrder(int id, int executrId, int customerId,  String review_text, int assessment) {
        this.id = id;
        this.executrId = executrId;
        this.customerId = customerId;
        this.review_text = review_text;
        this.assessment = assessment;
        this.createdDate = DataUtils.getCurentDateInLong();
        answers = new java.util.ArrayList<>();
    }

    public ReviewToOrder(int executrId, int customerId,  String review_text, int assessment) {
        this.executrId = executrId;
        this.customerId = customerId;
        this.review_text = review_text;
        this.assessment = assessment;
        this.createdDate = DataUtils.getCurentDateInLong();
        answers = new java.util.ArrayList<>();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getExecutrId() {
        return executrId;
    }

    public void setExecutrId(int executrId) {
        this.executrId = executrId;
    } 
    
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getReview_text() {
        return review_text;
    }

    public void setReview_text(String text) {
        this.review_text = text;
    }

    public int getAssessment() {
        return assessment;
    }

    public void setAssessment(int assessment) {
        this.assessment = assessment;
    }

 
    public Long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Long createdDate) {
        this.createdDate = createdDate;
    }

    public java.util.ArrayList<AnswerToReview> getAnswers() {
        return answers;
    }

    public void setAnswers(java.util.ArrayList<AnswerToReview> answers) {
        this.answers = answers;
    }
}
