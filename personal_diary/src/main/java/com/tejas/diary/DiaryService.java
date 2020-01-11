package com.tejas.diary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiaryService {

	@Autowired
	private DiaryRepository diaryRepository;
	
	public DiaryService() {
		// TODO Auto-generated constructor stub
	}
	
	public void addContent(Diary content) {
		Diary d = diaryRepository.save(content);
		System.out.println(d);
	}
	
	public List<Diary> getAllPosts() {
		List<Diary> posts = new ArrayList<Diary>();
		diaryRepository.findAll().forEach(posts::add);
		return posts;
	}

}
