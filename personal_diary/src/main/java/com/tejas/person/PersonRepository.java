package com.tejas.person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {
	
	@Query("select s from Person s where s.username = ?1")
    Person findByUsername(String username);
}
