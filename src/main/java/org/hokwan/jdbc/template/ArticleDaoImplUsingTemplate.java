package org.hokwan.jdbc.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.parkg.jdbc.raw.Article;
import org.parkg.jdbc.raw.ArticleDao;
import org.mariadb.jdbc.MariaDbDataSource;

/**
 * ArticleDao 인터페이스의 구현 클래스. JdbcTemplate을 사용한다.
 * 
 * @author Jacob
 */
public class ArticleDaoImplUsingTemplate implements ArticleDao {
	/**
	 * 목록 가져오는 sql
	 */
	static final String LIST_ARTICLES = "SELECT articleId, title, name, cdate FROM article LIMIT 20";

	/**
	 * 글 1개 가져오는 sql
	 */
	static final String GET_ARTICLE = "SELECT articleId, title, content, name, cdate FROM article WHERE articleId=?";

	/**
	 * 글 등록하는 sql
	 */
	static final String ADD_ARTICLE = "INSERT INTO article(title, content, userId, name) VALUES (?,?,?,?)";

	/**
	 * 글 수정하는 sql
	 */
	static final String UPDATE_ARTICLE = "UPDATE article SET title=?, content=? WHERE articleId=?";

	/**
	 * 글 삭제하는 sql
	 */
	static final String DELETE_ARTICLE = "DELETE FROM article WHERE articleId=?";

	/**
	 * 데이터베이스 DataSource
	 */
	DataSource dataSource;
	
	/**
	 * JDBC helper 클래스
	 */
	JdbcTemplate jdbcTemplate;

	/**
	 * Default Constructor<br> 
	 * dataSource와 jdbcTemplate을 초기화한다.
	 */
	public ArticleDaoImplUsingTemplate() {
		dataSource = new MariaDbDataSource(
				"jdbc:mariadb://localhost:3306/jacobdb?username=jacob&password=xxxxxxxx");
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * 게시글 목록
	 */
	@Override
	public List<Article> listArticles() {
		return jdbcTemplate.queryForList(LIST_ARTICLES, null,
				new RowMapper<>() {
					@Override
					public Article mapRow(ResultSet rs) throws SQLException {
						Article article = new Article();
						article.setArticleId(rs.getString("articleId"));
						article.setTitle(rs.getString("title"));
						article.setName(rs.getString("name"));
						article.setCdate(rs.getString("cdate"));
						return article;
					}
				});
	}

	/**
	 * 게시글 상세
	 */
	@Override
	public Article getArticle(String articleId) {
		return jdbcTemplate.queryForObject(GET_ARTICLE,
				new Object[] { articleId }, new RowMapper<>() {
					@Override
					public Article mapRow(ResultSet rs) throws SQLException {
						Article article = new Article();
						article.setArticleId(rs.getString("articleId"));
						article.setTitle(rs.getString("title"));
						article.setContent(rs.getString("content"));
						article.setName(rs.getString("name"));
						article.setCdate(rs.getString("cdate"));
						return article;
					}
				});
	}

	/**
	 * 게시글 등록
	 */
	@Override
	public void addArticle(Article article) {
		jdbcTemplate.update(ADD_ARTICLE, article.getTitle(),
				article.getContent(), article.getUserId(), article.getName());
	}

	/**
	 * 게시글 수정
	 */
	@Override
	public void updateArticle(Article article) {
		jdbcTemplate.update(UPDATE_ARTICLE, article.getTitle(),
				article.getContent(), article.getArticleId());
	}

	/**
	 * 게시글 삭제
	 */
	@Override
	public void deleteArticle(String articleId) {
		jdbcTemplate.update(DELETE_ARTICLE, articleId);
	}
}
