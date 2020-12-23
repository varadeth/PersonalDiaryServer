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
	
	public int addContent(Diary content) {
		Diary d = diaryRepository.save(content);
		System.out.println(d.toString());
		return d.getDid();
	}
	
	public List<Diary> getAllPosts() {
		List<Diary> posts = new ArrayList<Diary>();
		diaryRepository.findAll().forEach(posts::add);
		return posts;
	}
	
	public List<Diary> getAllPostsForUserById(int id) {
		return diaryRepository.findAllDiaryById(id);
	}
	
	public Diary getPostById(int id, int did) {
		return diaryRepository.findByIdAndDid(id, did);
	}

}
