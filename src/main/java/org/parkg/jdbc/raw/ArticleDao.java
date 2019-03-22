package org.parkg.jdbc.raw;

import java.util.List;

public interface ArticleDao {
	List<Article> listArticles();

    Article getArticle(String articleId);

    void addArticle(Article article);

    void updateArticle(Article article);

    void deleteArticle(String articleId);
}

