package com.tejas.diary;

import java.util.ArrayList;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class DiaryContent {
	String username;
	ArrayList<String> content;
}
