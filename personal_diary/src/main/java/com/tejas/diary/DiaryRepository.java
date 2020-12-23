package com.tejas.diary;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DiaryRepository extends CrudRepository<Diary, Integer> {

	List<Diary> findAllDiaryById(Integer id);
	
	@Query(value = "select * from Diary d where d.id= ?1 and d.did=?2", nativeQuery=true)
	Diary findByIdAndDid(Integer id, Integer did);
	
}
