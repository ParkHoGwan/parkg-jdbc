package org.parkg.jdbc.raw;

import java.util.List;

import org.hokwan.jdbc.template.ArticleDaoImplUsingTemplate;

public class ArticleMain {
    ArticleDao articleDao = new ArticleDaoImplUsingTemplate();

    /**
     * main 메서드
     */
    public static void main(String[] args) {
        new ArticleMain().listArticles();
    }

    public void listArticles() {
        List<Article> articles = articleDao.listArticles();
        System.out.println(articles);
    }

    public void getArticle() {
        Article article = articleDao.getArticle("3");
        System.out.println(article);
    }

    public void addArticle() {
        Article article = new Article();
        article.setTitle("This is title.");
        article.setContent("This is content");
        article.setUserId("1");
        article.setName("조원석");
        articleDao.addArticle(article);
    }

    public void updateArticle() {
        Article article = new Article();
        article.setArticleId("7");
        article.setTitle("This is modified title.");
        article.setContent("This is modified content");
        articleDao.updateArticle(article);
    }

    public void deleteArticle() {
        articleDao.deleteArticle("8");
    }
}