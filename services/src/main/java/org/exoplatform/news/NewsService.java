package org.exoplatform.news;

import java.util.List;

import javax.jcr.Node;

import org.exoplatform.news.filter.NewsFilter;
import org.exoplatform.news.model.News;
import org.exoplatform.news.model.SharedNews;
import org.exoplatform.news.search.NewsESSearchResult;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.space.model.Space;

public interface NewsService {

  News createNews(News news, org.exoplatform.services.security.Identity viewer) throws Exception;

  /**
   * Get a news by id
   * 
   * @param id Id of the news
   * @return The news with the given id
   * @throws Exception when error
   */
  News getNewsById(String id) throws Exception;

  News getNewsById(String id, org.exoplatform.services.security.Identity viewer, boolean editMode) throws Exception;

  List<News> getNews(NewsFilter filter, org.exoplatform.services.security.Identity viewer) throws Exception;

  int getNewsCount(NewsFilter filter, org.exoplatform.services.security.Identity viewer) throws Exception;

  News updateNews(News news, org.exoplatform.services.security.Identity viewer) throws Exception;

  void markAsRead(News news, String userId) throws Exception;

  void shareNews(SharedNews sharedNews, List<Space> spaces, org.exoplatform.services.security.Identity viewer) throws Exception;

  void pinNews(String newsId, org.exoplatform.services.security.Identity viewer) throws Exception;

  void unpinNews(String newsId, org.exoplatform.services.security.Identity viewer) throws Exception;

  News convertNodeToNews(Node node, org.exoplatform.services.security.Identity viewer, boolean editMode) throws Exception;

  News createNewsDraft(News news, org.exoplatform.services.security.Identity viewer) throws Exception;

  void deleteNews(String id, org.exoplatform.services.security.Identity viewer, boolean isDraft) throws Exception;

  public boolean canEditNews(String posterId, String spaceId, org.exoplatform.services.security.Identity viewer);

  public boolean canPublishNews(org.exoplatform.services.security.Identity viewer);
  
  boolean canArchiveNews(String newsAuthor, org.exoplatform.services.security.Identity viewer);

  List<News> searchNews(NewsFilter filter, org.exoplatform.services.security.Identity viewer, String lang) throws Exception;

  void archiveNews(String newsId, org.exoplatform.services.security.Identity viewer) throws Exception;

  void unarchiveNews(String newsId, org.exoplatform.services.security.Identity viewer) throws Exception;

  public boolean canDeleteNews(String posterId, String spaceId, org.exoplatform.services.security.Identity viewer);

  public List<NewsESSearchResult> search(Identity currentUser, String term, int offset, int limit);

  News scheduleNews(News news, org.exoplatform.services.security.Identity viewer) throws Exception;

  boolean canPublishNews(String posterId, String spaceId, org.exoplatform.services.security.Identity viewer);

}
