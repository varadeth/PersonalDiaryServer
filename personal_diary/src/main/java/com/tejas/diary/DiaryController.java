package com.tejas.diary;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public DiaryController() {
	}
	
	@PostMapping("/addContent/{username}")
	public int addContent(@PathVariable String username,@RequestBody Content content) {
		System.out.println(content);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		content.setDate(formatter.format(new Date()));
		Diary diary = new Diary(username,content.getText(),content.getDate());
		System.out.println(content);
		diaryService.addContent(diary);
		return 200;
	}
	
	@GetMapping("/allPosts")
	public String getAllPosts() {
		List<Diary> list = diaryService.getAllPosts();
		String content = "";
		for(Diary d : list) {
			content += d.getUsername() + " : " + d.getDiaryContent() + "\n";
		}
		return content;
	}
	
	@GetMapping("/posts/{username}")
	public List<Content> getAllPostsOfUser(@PathVariable String username) {
		List<Diary> list = diaryService.getAllPosts();
		ArrayList<Content> contentList = new ArrayList<Content>();
		for(Diary d : list) {
			if(d.getUsername().equals(username)) {
				contentList.add(d.getDiaryContent());
			}
		}
		return contentList;
	}
}
