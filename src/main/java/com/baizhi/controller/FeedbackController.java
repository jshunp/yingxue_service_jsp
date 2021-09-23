package com.baizhi.controller;

import com.baizhi.dto.Feed;
import com.baizhi.dto.FeedPage;
import com.baizhi.entity.Feedback;
import com.baizhi.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;

@RestController
@RequestMapping("feedback")
public class FeedbackController {
    @Autowired
    public FeedbackService feedbackService;
    @PostMapping("queryAllPage")
    public FeedPage<Feedback> queryAllPage(@RequestBody Feed feed){
        List<Feedback> feedbacks = feedbackService.queryAllByLimit((feed.getPage() - 1) * feed.getPageSize(), feed.getPageSize());
        return null;
    }
}
