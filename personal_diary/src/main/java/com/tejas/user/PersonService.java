package com.tejas.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
	public void addPerson(Person person) {
		personRepository.save(person);
	}
	
	public Person getPerson(String userName) {
		Person person = personRepository.findById(userName).orElseGet(()->{
			return null;
		});
		return person;
	}
	
	public boolean updatePerson(Person updatedPerson) {
		boolean isUpdated = false;
		Person person = personRepository.findById(updatedPerson.getUsername()).orElseGet(()->{
			return null;	
		});
		if(person != null ) {
			person.setName(updatedPerson.getName());
			person.setEmail(updatedPerson.getEmail());
			person.setContactNo(updatedPerson.getContactNo());
			person.setPassword(updatedPerson.getPassword());
			isUpdated = true;
		}
		return isUpdated;
	}
	
	public List<Person> getPersons() {
		List<Person> persons = new ArrayList<>();
		personRepository.findAll().forEach(persons::add);
		return persons;
	}
	
}
