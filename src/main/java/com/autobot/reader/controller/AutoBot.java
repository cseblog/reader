package com.autobot.reader.controller;

import com.autobot.reader.model.Post;
import com.autobot.reader.service.PostGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by trunghuynh on 8/12/18.
 */
@Controller
public class AutoBot {
    private final PostGeneratorService postGeneratorService;

    @Autowired
    public AutoBot(PostGeneratorService postGeneratorService) {
        this.postGeneratorService = postGeneratorService;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

    @PostMapping("/post")
    @ResponseBody
    public Post generate(@RequestParam String url) throws Exception {
        return postGeneratorService.greet(url);
    }

}
