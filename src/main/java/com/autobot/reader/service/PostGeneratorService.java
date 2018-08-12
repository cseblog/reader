package com.autobot.reader.service;

import com.autobot.reader.adaptor.NLDReader;
import com.autobot.reader.adaptor.VnNetReader;
import com.autobot.reader.model.Post;
import org.springframework.stereotype.Service;

/**
 * Created by trunghuynh on 8/12/18.
 *     //Call adaptor
 //Download image
 //generate post body
 //return request
 */
@Service
public class PostGeneratorService {


    public Post greet(String url) throws Exception {
//        VnNetReader vnNetReader = new VnNetReader();
        NLDReader nldReader = new NLDReader();
//        Post post = vnNetReader.run(url);
        Post post = nldReader.run(url);
        return post;
    }
}
