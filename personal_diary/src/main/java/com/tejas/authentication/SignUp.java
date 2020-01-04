package com.tejas.authentication;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;

import com.tejas.person.Person;
import com.tejas.person.PersonService;

@RestController
public class SignUp {
	
	@Autowired
	private PersonService personService;
	
	@PostMapping("/signup")
	public Map onSignup(@RequestBody Person person) {
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
		byte[] digest = digestSHA3.digest(person.getPassword().getBytes());
		person.setPassword(Hex.toHexString(digest));
		int statusCode = personService.addPerson(person);
		HashMap<String, Object> response = new HashMap();
		String message = "";
		if(statusCode == 200) {
			message = "Signup Successful.";
		}
		else if(statusCode == 409) {
			message = "User already signed up. Please try Logging In";
		}
		else {
			message = "There was an error on the server and the request could not be completed.";
		}
		response.put("statusCode", statusCode);
		response.put("message",message);
		return response;
	}
	
}
