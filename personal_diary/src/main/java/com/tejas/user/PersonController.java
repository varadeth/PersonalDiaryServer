package com.tejas.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@GetMapping("/user/{userName}")
	public Person getPerson(@PathVariable String userName) {
		System.out.println(userName);
		return personService.getPerson(userName);
	}
	
	@PostMapping("/user")
	public void addPerson(@RequestBody Person user) {
		System.out.println(user);
		personService.addPerson(user);
	}
	
	@GetMapping("/users") 
	public List<Person> getPersons() {
		return personService.getPersons();
	}
	
}
