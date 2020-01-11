package com.tejas.diary;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Diary {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String usrname;
	private String text;
	private String date;
	
	public Diary() {
		this.usrname = "";
		this.text = new String();
	}
	
	public Diary(String username, String text, String date) {
		super();
		this.usrname = username;
		this.text = text;
		this.date = date;
	}

	public Diary(int id, String username, String text, String date) {
		super();
		this.id = id;
		this.usrname = username;
		this.text = text;
		this.date = date;
	}
	
	public String getUsername() {
		return usrname;
	}

	public void setUsername(String username) {
		this.usrname = username;
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
		return "Diary [username=" + usrname + ", diaryContent=" + text + "]";
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
