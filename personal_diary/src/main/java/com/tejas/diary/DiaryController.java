package com.tejas.diary;

import java.util.ArrayList;

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
	
	@GetMapping("/user/{username}/content")
	public ArrayList<String> getAllContent(@PathVariable String username) {
		return diaryService.getAllContent(username);
	}
	
	@PostMapping("/user/{username}")
	public void addDiaryContent(@PathVariable String username, @RequestBody DiaryContent diaryContent) {
		
	}
}
