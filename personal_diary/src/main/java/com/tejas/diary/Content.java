package com.tejas.diary;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Content {

	
	private String text;
	private String date;
	public Content() {
		super();
	}
	public Content(String text) {
		super();
		this.text = text;
	}
	public Content(String text, String date) {
		this.text = text;
		this.date = date;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	@Override
	public String toString() {
		return "Content [content=" + text + ", date=" + date + "]";
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
