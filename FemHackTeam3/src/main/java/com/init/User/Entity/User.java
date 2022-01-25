package com.init.User.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="user")
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_user")
    private int idUser;

    @Column(name="name")
    private String name;

    @Column(name="email")
    private String email;

    @Column(name="score")
    private int score;

    @JsonIgnore
    @OneToMany(mappedBy="user",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<Question> questions;

    public User() {
    }

    public User(int idUser, String name, String email, int score) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.score = score;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("User [idUser=").append(idUser).append(", name=").append(name).append(", email=").append(email)
                .append(", score=").append(score).append(", questions=").append(questions).append("]");
        return builder.toString();
    }
    
	public void addQuestion(Question theQuestion) {
		
		if(questions==null) questions=new ArrayList<>();
				
		questions.add(theQuestion);
				
		theQuestion.setUser(this);
				
				}
}