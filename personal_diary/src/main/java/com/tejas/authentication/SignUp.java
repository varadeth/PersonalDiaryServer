package com.tejas.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tejas.person.Person;
import com.tejas.person.PersonService;

@RestController
public class SignUp {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping("/signup")
	public void onSignup(@RequestBody Person person) {
		personService.addPerson(person);
	}
	
}
