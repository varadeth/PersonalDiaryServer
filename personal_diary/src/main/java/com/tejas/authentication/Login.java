package com.tejas.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tejas.person.Person;
import com.tejas.person.PersonService;

@RestController
public class Login {
	
	@Autowired
	private PersonService personService;
	
	@GetMapping("/login/{username}")
	public Person onLogin(@PathVariable String username) {
		Person authenticatedPerson = personService.getPerson(username);
		return authenticatedPerson;
	}
}
