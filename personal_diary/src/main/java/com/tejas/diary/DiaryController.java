package com.tejas.diary;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.tejas.authentication.config.JwtTokenUtil;
import com.tejas.person.PersonService;

@RestController
public class DiaryController {

	@Autowired
	private DiaryService diaryService;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private PersonService personService;
	Cipher encryptCipher;
	Cipher decryptCipher;
	private static byte[] key = {
            0x74, 0x68, 0x69, 0x73, 0x49, 0x73, 0x41, 0x53, 0x65, 0x63, 0x72, 0x65, 0x74, 0x4b, 0x65, 0x79
    };
	SecretKey secretKey;
	public DiaryController() {
		try {
			encryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			decryptCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			secretKey = new SecretKeySpec(key,"AES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
			decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
		}
		catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@PostMapping("/addContent")
	public int addContent(@RequestBody Content content, @RequestHeader(name="Authorization") String bearer) {
		System.out.println(content);
		Integer id = getIdFromUsername(getUsername(bearer.substring(7)));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		content.setDate(formatter.format(new Date()));
		String text = content.getText();
		try {
			text = Base64.getEncoder().encodeToString(encryptCipher.doFinal(text.getBytes()));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Diary diary = new Diary(id,content.getTitle(), text,content.getDate());
		diaryService.addContent(diary);
		return 200;
	}
	
	@GetMapping("/allPosts")
	public List<Diary> getAllPosts() {
		List<Diary> list = diaryService.getAllPosts();
		List<Diary> returnList = new ArrayList();		
		for(Diary d : list) {
			try {
				System.out.println(d);
				d.setText(new String(decryptCipher.doFinal(Base64.getDecoder().decode(d.getText().getBytes()))));
				returnList.add(d);
			}
			catch(BadPaddingException | IllegalBlockSizeException e) {
				e.printStackTrace();
			}
		}
		return returnList;
	}
	
	@GetMapping("/posts")
	public List<Diary> getAllPostsOfUser(@RequestHeader(name="Authorization") String header) {
		Integer id = getIdFromUsername(getUsername(header.substring(7)));
		List<Diary> list = diaryService.getAllPostsForUserById(id) ;
		for(Diary d : list) {
			if(d.getId() == id) {
				String encrypted = d.getText();
				String decrypted="";
				try {
					decrypted = new String(decryptCipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes())));
				} catch (IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				d.setText(decrypted);
			}
		}
		return list;
	}
	
	@GetMapping("post/{did}")
	public ResponseEntity<Diary> getPost(@PathVariable int did, @RequestHeader(name = "Authorization") String authHeader) {
		Integer id = getIdFromUsername(getUsername(authHeader.substring(7)));
		Diary d = diaryService.getPostById(id, did);
		if(d != null) {
			try {
				d.setText(new String(decryptCipher.doFinal(Base64.getDecoder().decode(d.getText().getBytes()))));
			} catch (IllegalBlockSizeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BadPaddingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return new ResponseEntity<Diary>(d, HttpStatus.ACCEPTED);
		}
		return new ResponseEntity(null, HttpStatus.BAD_REQUEST);
	}
	
	@PutMapping("update/{did}")
	public ResponseEntity<Integer> updatePost(@PathVariable int did, @RequestBody Diary content, @RequestHeader(name="Authorization") String authHeader) {
		String text = content.getText();
		Integer id = getIdFromUsername(getUsername(authHeader.substring(7)));
		try {
			text = Base64.getEncoder().encodeToString(encryptCipher.doFinal(text.getBytes()));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		content.setText(text);
		diaryService.updateContent(content);
		return new ResponseEntity<Integer>(200, HttpStatus.ACCEPTED);
	}
	
	public String getUsername(String authToken) {
		return jwtTokenUtil.getUsernameFromToken(authToken);
	}
	
	public Integer getIdFromUsername(String username) {
		return personService.getPerson(username).getId();
	}
}
