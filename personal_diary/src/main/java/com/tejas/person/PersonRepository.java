package com.tejas.person;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends CrudRepository<Person, String> {
	
	@Query("select s from Person s where s.username = ?1")
    Person findByUsername(String username);
	
	@Query("select s from Person s where s.username = ?1 and s.password = ?2")
	Person findUserByUsernameAndPassword(String username,String password);
}
