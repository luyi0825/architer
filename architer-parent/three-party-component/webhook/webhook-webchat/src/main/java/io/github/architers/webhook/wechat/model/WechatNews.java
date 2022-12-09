package io.github.architers.webhook.wechat.model;

import lombok.Data;

import java.util.List;

/**
 * @author luyi
 */
@Data
public class WechatNews {

    private List<Article> articles;

    public WechatNews(List<Article> articles) {
        this.articles = articles;
    }

    @Data
    public static class Article {
        private String title;

        private String description;

        private String url;

        private String picurl;

        public Article() {

        }

        public Article(String title, String description, String url, String picurl) {
            this.title = title;
            this.description = description;
            this.url = url;
            this.picurl = picurl;
        }
    }
}
