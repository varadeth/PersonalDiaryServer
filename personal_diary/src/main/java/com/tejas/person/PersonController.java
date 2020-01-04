package com.tejas.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@PutMapping("/user/{username}")
	public boolean updatePerson(@PathVariable String username,@RequestBody Person person) {
		return personService.updatePerson(username, person);
	}
	
	@GetMapping("/users") 
	public List<Person> getPersons() {
		return personService.getPersons();
	}
	
}
