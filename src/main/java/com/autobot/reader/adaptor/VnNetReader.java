package com.autobot.reader.adaptor;

import com.autobot.reader.model.Entity;
import com.autobot.reader.model.EntityType;
import com.autobot.reader.model.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;


/**
 * Created by trunghuynh on 8/11/18.
 */

public class VnNetReader {
    private static final Logger logger = LoggerFactory.getLogger(VnNetReader.class);

    public Post run(String url) throws Exception {
        Post post = new Post();
        Document doc = Jsoup.connect(url).get();

        //Get title
        Elements title = doc.select(".ArticleDetail h1");
        logger.info("Title: {}",title.text());
        post.setTitle(title.text());


        Element content = doc.getElementById("ArticleContent");
        //get summary
//        Elements summary = content.select(".t-j .bold");


        Elements pEls = content.children();


        boolean isFirst = true ;

        ArrayList<Entity> body = new ArrayList<>();
        for (Element pEl : pEls) {
            if(isFirst){
                logger.info("Summary: {}",pEl.text());
                post.setSummary(pEl.text());
                isFirst = false;
                continue;
            }

            if(pEl.tagName().equals("p")){
                Elements els = pEl.children();
                Elements firstImg = pEl.select("img");

                if(pEl.text().isEmpty() && firstImg != null){
                    logger.info("p.img {}",firstImg.attr("src"));
                    Entity ne = new Entity(EntityType.IMAGE,null,firstImg.attr("src"));
                    body.add(ne);
                }else {
                    logger.info("p.text {}",pEl.text());
                    Entity e = new Entity(EntityType.PARAGRAPH,pEl.text(),null);
                    body.add(e);
                }
            } else {
                //
                String link = null;
                String desc = null;
                if(pEl.tagName().equals("table")){
                    Elements rows = pEl.select("td");
                    for (Element e : rows) {
                        String className = e.className();

                        if(className.equals("FmsArticleBoxStyle-Images image")){
                            link = e.select("img").attr("src");
                            logger.info("Link:{}",link);
                        }

                        if(className.equals("FmsArticleBoxStyle-Content image_desc")){
                            desc = e.text();
                            logger.info("Desc:{}",desc);
                        }
                    }
                }
                if(link != null && desc != null){
                    logger.info("Image: {} {}", link,desc);
                    Entity ne = new Entity(EntityType.IMAGE,desc,link);
                    body.add(ne);
                }
            }
        }

        post.setBody(body);
        return post;
    }
}
