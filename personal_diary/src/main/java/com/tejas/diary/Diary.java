package com.tejas.diary;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.tejas.person.Person;

@Entity
public class Diary {
	
	@Id
	private int id;
	private String text;
	private String date;
	
	
	public Diary() {
		this.text = new String();
	}
	
	public Diary(int id, String text, String date) {
		super();
		this.id = id;
		this.text = text;
		this.date = date;
	}

	
	public Content getDiaryContent() {
		Content content = new Content(this.text,this.date);
		return content;
	}

	public void setDiaryContent(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Diary [id=" + id + ", diaryContent=" + text + "]";
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
