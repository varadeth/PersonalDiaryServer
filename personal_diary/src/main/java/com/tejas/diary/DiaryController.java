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
		Diary diary = new Diary(id,text,content.getDate());
		diaryService.addContent(diary);
		return 200;
	}
	
	@GetMapping("/allPosts")
	public String getAllPosts() {
		List<Diary> list = diaryService.getAllPosts();
		String content = "";
		for(Diary d : list) {
			content += d.getId() + " : " + d.getDiaryContent() + "\n";
		}
		return content;
	}
	
	@GetMapping("/posts/{id}")
	public List<Content> getAllPostsOfUser(@PathVariable int id) {
		List<Diary> list = diaryService.getAllPosts();
		ArrayList<Content> contentList = new ArrayList<Content>();
		for(Diary d : list) {
			if(d.getId() == id) {
				String encrypted = d.getDiaryContent().getText();
				String decrypted="";
				try {
					decrypted = new String(decryptCipher.doFinal(Base64.getDecoder().decode(encrypted.getBytes())));
				} catch (IllegalBlockSizeException | BadPaddingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Decrypted : "+decrypted);
				Content c = d.getDiaryContent();
				c.setText(decrypted);
				contentList.add(c);
			}
		}
		return contentList;
	}
	
}
