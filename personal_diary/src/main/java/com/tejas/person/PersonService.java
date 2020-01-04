package com.tejas.person;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepository;
	
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
		Person person = personRepository.findById(userName).orElseGet(()->{
			return null;
		});
		return person;
	}
	
	public boolean updatePerson(String username,Person updatedPerson) {
		boolean isUpdated = false;
		Person person = personRepository.findById(username).orElseGet(()->{
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
	
	public void deletePersons() {
		personRepository.deleteAll();
	}
}
