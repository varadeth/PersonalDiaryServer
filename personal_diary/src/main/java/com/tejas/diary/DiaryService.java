package com.tejas.diary;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {
	
	@Autowired
	private DiaryRepository diaryRepository;
	
	public ArrayList<String> getAllContent(String username) {
		return diaryRepository.findByUsername(username);
	}
	
}
