package com.tejas.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;


import java.util.HashMap;
import java.util.Map;
import com.tejas.person.Person;
import com.tejas.person.PersonService;

@RestController
public class Login {
	
	@Autowired
	private PersonService personService;
	
	/*
	 * Method name: onLogin
	 * Returns: json
	 * Parameters: Username and Password in object
	 */
	@PostMapping("/login")
	public Map onLogin(@RequestBody Authentication authData) {
		
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
		byte[] digest = digestSHA3.digest(authData.getPassword().getBytes());
		String passwordFromFrontEnd = Hex.toHexString(digest);
		
		HashMap<String,Object> response = new HashMap();
		
		Person person = personService.getPerson(authData.getUsername());
		Person authenticatedPerson = null;
		int statusCode;
		String message = "";
		if(person == null) {
			statusCode = 404;
			message = "No such user found.";
		}
		else if(person.getPassword().equals(passwordFromFrontEnd)) {
			statusCode = 200;
			message = "Login successful.";
			authenticatedPerson = person;
			authenticatedPerson.setPassword("");
		}
		else {
			statusCode = 401;
			message = "Invalid Username or Password.";
		}
		response.put("statusCode", statusCode);
		response.put("message",message);
		response.put("user",authenticatedPerson);
		return response;
	}
}
