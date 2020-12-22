package com.tejas.diary;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface DiaryRepository extends CrudRepository<Diary, Integer> {

	List<Diary> findAllDiaryById(Integer id);
	
}
