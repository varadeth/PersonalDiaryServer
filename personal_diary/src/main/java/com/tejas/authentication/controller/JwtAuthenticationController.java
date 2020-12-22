package com.tejas.authentication.controller;

import java.util.HashMap;

import org.bouncycastle.jcajce.provider.digest.SHA3;
import org.bouncycastle.util.encoders.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tejas.authentication.config.JwtTokenUtil;
import com.tejas.authentication.model.JwtRequest;
import com.tejas.authentication.model.JwtResponse;
import com.tejas.authentication.service.JwtUserDetailsService;
import com.tejas.person.Person;
import com.tejas.person.PersonService;

@RestController
@CrossOrigin
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	@Autowired
	private PersonService personService;
	
	@PostMapping(value="/authenticate") 
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		HashMap response = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		int status = Integer.parseInt(response.get("statusCode").toString());
		System.out.println("Status : "+status);
		if(status == 200) {
			final UserDetails userDetails = userDetailsService
					.loadUserByUsername(authenticationRequest.getUsername());
			Person person = personService.getPerson(authenticationRequest.getUsername());
			final String token = jwtTokenUtil.generateToken(userDetails);
			
			return new ResponseEntity<JwtResponse>(new JwtResponse(token, person.getId()), HttpStatus.OK);
		}
		throw new Exception("INVALID_CREDENTIALS");
	}
	
	@PostMapping(value = "/register")
	public ResponseEntity<?> saveUser(@RequestBody Person person) throws Exception {
		System.out.println("Register : "+1);
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
		byte[] digest = digestSHA3.digest(person.getPassword().getBytes());
		person.setPassword(Hex.toHexString(digest));
		int statusCode = userDetailsService.addPerson(person);
		HashMap<String, Object> response = new HashMap();
		HashMap<String, Object> error = new HashMap();
		String message = "";
		if(statusCode == 200) {
			message = "Signup Successful.";
			response.put("message", message);
			ResponseEntity<HashMap>xyz = new ResponseEntity<HashMap>(response, HttpStatus.OK);
			System.out.println(xyz);
			return new ResponseEntity<HashMap>(response, HttpStatus.OK);
		}
		else if(statusCode == 409) {
			message = "User already signed up. Please try Logging In";
			error.put("message", message);
			return new ResponseEntity<HashMap>(error, HttpStatus.CONFLICT);
		}
		else {
			message = "There was an error on the server and the request could not be completed.";
			error.put("message",message);
			return new ResponseEntity<HashMap>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private HashMap authenticate(String username, String password) throws Exception {
		SHA3.DigestSHA3 digestSHA3 = new SHA3.Digest512();
		byte[] digest = digestSHA3.digest(password.getBytes());
		String passwordFromFrontEnd = Hex.toHexString(digest);
		
		HashMap<String,Object> response = new HashMap();
		
		Person person = userDetailsService.getPerson(username);
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
