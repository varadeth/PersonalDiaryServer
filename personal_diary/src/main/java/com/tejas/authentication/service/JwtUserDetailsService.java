package com.tejas.authentication.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tejas.person.Person;
import com.tejas.person.PersonRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Person person = personRepository.findByUsername(username);
		System.out.println(username);
		if(person == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		} 
		return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(),
				new ArrayList<>());
	}
	
	public int addPerson(Person person) {
		Person previousPerson = getPerson(person.getUsername());
		if(previousPerson != null) {
			return 409;
		}
		Person response = personRepository.save(person);
		if(response != null) {
			return 200;
		}
		return 500;
	}
	
	public Person getPerson(String userName) {
		Person person = personRepository.findByUsername(userName);
		return person;
	}
	
	public Person getPersonByLogin(String username, String password) {
		Person person = null;
		person = personRepository.findUserByUsernameAndPassword(username,password);
		return person;
	}
	

}
