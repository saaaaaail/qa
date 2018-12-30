package com.sail.qa.service;

import com.sail.qa.model.Question;

import java.util.List;

/**
 * @Author: sail
 * @Date: 2018/12/22 19:40
 * @Version 1.0
 */

public interface QuestionService {
    int addQuestion(Question question);
    List<Question> getLatestQuestions(int userId,int offset,int limit);
    Question selectById(int id);
}
