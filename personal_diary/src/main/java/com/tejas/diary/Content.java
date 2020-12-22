package com.tejas.diary;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Content {

	private String title;
	private String text;
	private String date;
	public Content() {
		super();
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Content(String title, String text, String date) {
		System.out.println("text : " + text);
		this.text = text;
		this.date = date;
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Content [title=" + title + ", content=" + text + ", date=" + date + "]";
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
