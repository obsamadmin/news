package org.exoplatform.news;

import java.util.List;

import javax.jcr.Node;

import org.exoplatform.commons.exception.ObjectNotFoundException;
import org.exoplatform.news.filter.NewsFilter;
import org.exoplatform.news.model.News;
import org.exoplatform.news.search.NewsESSearchResult;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.space.model.Space;

public interface NewsService {
  News createNews(News news) throws Exception;

  News getNewsById(String id, boolean editMode) throws Exception;

  List<News> getNews(NewsFilter filter) throws Exception;

  int getNewsCount(NewsFilter filter) throws Exception;

  News updateNews(News news) throws Exception;

  void markAsRead(News news, String userId) throws Exception;

  /**
   * Shares a news into a dedicated space
   * 
   * @param newsId {@link News} technical identitifer
   * @param space {@link Space} to share with, the news
   * @param userIdentity {@link Identity} of user making the modification
   * @throws IllegalAccessException when user doesn't have access to {@link News}
   * @throws ObjectNotFoundException when {@link News} is not found
   */
  void shareNews(String newsId, Space space, Identity userIdentity) throws IllegalAccessException, ObjectNotFoundException;

  void pinNews(String newsId) throws Exception;

  void unpinNews(String newsId) throws Exception;

  News convertNodeToNews(Node node, boolean editMode) throws Exception;

  News createNewsDraft(News news) throws Exception;

  void deleteNews(String id, boolean isDraft) throws Exception;

  public boolean canEditNews(String posterId, String spaceId);

  /**
   * @param newsId News technical identifier
   * @param posterIdentityId activity poster {@link Identity} identifier
   * @return true if user has access to news, else false
   */
  boolean canViewNews(String newsId, String posterIdentityId);

  public boolean canPinNews();
  
  boolean canArchiveNews(String newsAuthor);

  List<News> searchNews(NewsFilter filter, String lang) throws Exception;

  void archiveNews(String newsId) throws Exception;

  void unarchiveNews(String newsId) throws Exception;

  public boolean canDeleteNews(String posterId, String spaceId);

  public List<NewsESSearchResult> search(Identity currentUser, String term, int offset, int limit);

  News scheduleNews(News news) throws Exception;

}
