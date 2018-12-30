package com.sail.qa;

import com.sail.qa.dao.QuestionDao;
import com.sail.qa.model.Question;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class QaApplicationTests {

	@Autowired
	QuestionDao questionDao = null;
	@Test
	public void contextLoads() {
		for(int i=0;i<20;i++){
			Question question = new Question();
			Date date = new Date();
			question.setTitle(String.format("TITLE{%d}",i));
			question.setContent(String.format("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa Content %d",i));
			date.setTime(date.getTime()+3600*i);
			question.setCreatedDate(date);
			question.setUserId(i+1);
			question.setCommentCount(i);
			questionDao.addQuestion(question);
		}
	}

}
