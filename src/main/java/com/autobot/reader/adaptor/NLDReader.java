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

import java.util.ArrayList;


/**
 * Created by trunghuynh on 8/11/18.
 * https://thitruong.nld.com.vn/
 */

public class NLDReader {
    private static final Logger logger = LoggerFactory.getLogger(NLDReader.class);

    public Post run(String url) throws Exception {
//        String url = "https://nld.com.vn/phap-luat/dien-bien-gay-can-vu-csgt-lien-quan-duong-day-logo-xe-vua-20180812134845336.htm";
        Post post = new Post();
        Document doc = Jsoup.connect(url).get();

        //Get title
        logger.info("Title: {}", doc.title());
        post.setTitle(doc.title());

        Elements sapo = doc.select(".sapo");
        logger.info("Summary: {}", sapo.text());
        post.setSummary(sapo.text());

        Elements bd = doc.select(".contentdetail").first().children();

        ArrayList<Entity> body = new ArrayList<>();
        for (Element pEl : bd) {
            if(pEl.className().contains("displaynone")){
                continue;
            }

            if (pEl.tagName().equals("p")) {
                Elements els = pEl.children();
                Elements firstImg = pEl.select("img");

                if (pEl.text().isEmpty() && firstImg != null) {
                    logger.info("p.img {}", firstImg.attr("src"));
                    Entity ne = new Entity(EntityType.IMAGE, null, firstImg.attr("src"));
                    body.add(ne);
                } else {
                    logger.info("p.text {}", pEl.text());
                    Entity e = new Entity(EntityType.PARAGRAPH, pEl.text(), null);
                    body.add(e);
                }
            } else {
                //
                String link = null;
                String desc = null;
                if (pEl.tagName().equals("div") && !pEl.html().isEmpty() &&
                        (pEl.className().contains("VCSortableInPreviewMode"))) {
                    Elements img = pEl.children().select("img");

                    link = img.attr("src");
                    logger.info("IMG: {}",link);

                    Elements caption = pEl.select(".PhotoCMS_Caption");
                    desc = caption.text();
                    logger.info("CAPTION: {}", desc);
                }

                if (link != null && desc != null) {
                    logger.info("Image: {} {}", link, desc);
                    Entity ne = new Entity(EntityType.IMAGE, desc, link);
                    body.add(ne);
                }
            }
        }

        post.setBody(body);
        return post;
    }
}