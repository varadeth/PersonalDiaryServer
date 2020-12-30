package com.tejas.diary;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.tejas.person.Person;

@Entity
public class Diary {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int did;

	private int id;
	private String title;
	@Column(length = 100000000)
	private String text;
	private String date;

	public Diary(int id, String title, String text, String date) {
		super();
		this.id = id;
		this.title = title;
		this.text = text;
		this.date = date;
	}

	@Override
	public String toString() {
		return "Diary [did=" + did + ", Title=" + title +", id=" + id + ", diaryContent=" + text + "]";
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Diary() {
		super();
	}

	public int getDid() {
		return did;
	}

	public void setDid(int did) {
		this.did = did;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

}
