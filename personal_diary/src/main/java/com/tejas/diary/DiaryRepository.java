package com.tejas.diary;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface DiaryRepository extends CrudRepository<DiaryContent,String>{
	public ArrayList<String> findByUsername(String username);
}
