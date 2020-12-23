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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DiaryController {

	@Autowired
	private DiaryService diaryService;
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
	@PostMapping("/addContent/{id}")
	public int addContent(@PathVariable int id,@RequestBody Content content) {
		System.out.println(content);
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
	
	@GetMapping("/posts/{id}")
	public List<Diary> getAllPostsOfUser(@PathVariable int id) {
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
	
	@GetMapping("post/{id}/{did}")
	public ResponseEntity<Diary> getPost(@PathVariable int id, @PathVariable int did) {
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
	
}
