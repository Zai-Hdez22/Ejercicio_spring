package com.concretepage.dao;

import com.concretepage.entity.Article;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class ArticleDAO implements IArticleDAO{
    @PersistenceContext	
    private EntityManager entityManager;	
    @Override
    public Article getArticleById(int articleId) {
        return entityManager.find(Article.class, articleId);
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Article> getAllArticles() {
        String hql = "FROM Article as atcl ORDER BY atcl.articleId";
        return (List<Article>) entityManager.createQuery(hql).getResultList();
    }
    @Override
    public void addArticle(Article article) {
	entityManager.persist(article);
    }
    @Override
    public void updateArticle(Article article) {
	Article artcl = getArticleById(article.getArticleId());
	artcl.setTitle(article.getTitle());
	artcl.setCategory(article.getCategory());
	entityManager.flush();
    }
    @Override
    public void deleteArticle(int articleId) {
	entityManager.remove(getArticleById(articleId));
    }
    @Override
    public boolean articleExists(String title, String category) {
        String hql="select article_id FROM articles WHERE title = '"+title+"' and category = '"+category+"'";
	int count = entityManager.createQuery(hql).setParameter(1, title).setParameter(2, category).getResultList().size();
	return count > 0 ? true : false;
    }
    
}
