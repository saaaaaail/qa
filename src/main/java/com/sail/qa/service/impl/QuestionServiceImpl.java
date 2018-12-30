package com.sail.qa.service.impl;

import com.sail.qa.dao.QuestionDao;
import com.sail.qa.model.Question;
import com.sail.qa.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    QuestionDao questionDao=null;

    @Autowired
    SensitiveServiceImpl sensitiveService = null;

    @Override
    public int addQuestion(Question question) {
        //敏感词过滤
        //过滤html标签
        //内容
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setContent(sensitiveService.filter(question.getContent()));
        //标题
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setTitle(sensitiveService.filter(question.getTitle()));
        System.out.println("question.getTitle(): "+question.getTitle());
        return questionDao.addQuestion(question);
    }

    @Override
    public Question selectById(int id) {
        return questionDao.selectById(id);
    }

    @Override
    public List<Question> getLatestQuestions(int userId,int offset,int limit){
        return questionDao.selectLatestQuestions(userId,offset,limit);
    }
}
