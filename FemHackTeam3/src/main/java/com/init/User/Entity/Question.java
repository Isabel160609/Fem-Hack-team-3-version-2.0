package com.init.User.Entity;



import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="question")
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="questions")
    private String questions;

    @Column(name="answers")
    private String[] answers=new String[4];
//    @JsonIgnore
//    @OneToOne(mappedBy="question",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    private Answers answers;

    @Column(name="solutions")
    private int solutions;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name= "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    public Question() {
    }

    public Question(String questions, String[]answers) {
        this.questions = questions;
        this.answers = answers;
    }

    
    public Question(int id, String questions, String[] answers, int solutions, User user) {
		this.id = id;
		this.questions = questions;
		this.answers = answers;
		this.solutions = solutions;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[]answers) {
        this.answers = answers;
    }

    public int getSolutions() {
        return solutions;
    }

    public void setSolutions(int solutions) {
        this.solutions = solutions;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

	@Override
	public String toString() {
		return "Question [id=" + id + ", questions=" + questions + ", answers="
				+ Arrays.toString(answers) + ", solutions=" + solutions + ", user=" + user + "]";
	}

}
