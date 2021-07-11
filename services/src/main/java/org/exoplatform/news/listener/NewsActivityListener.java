package org.exoplatform.news.listener;

import org.exoplatform.news.NewsService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.social.core.activity.ActivityLifeCycleEvent;
import org.exoplatform.social.core.activity.ActivityListenerPlugin;
import org.exoplatform.social.core.activity.model.ExoSocialActivity;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.ActivityManager;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.space.spi.SpaceService;

public class NewsActivityListener extends ActivityListenerPlugin {

  private static final Log LOG = ExoLogger.getLogger(NewsActivityListener.class);

  private ActivityManager  activityManager;

  private IdentityManager  identityManager;

  private SpaceService     spaceService;

  private NewsService      newsService;

  public NewsActivityListener(ActivityManager activityManager,
                              IdentityManager identityManager,
                              SpaceService spaceService,
                              NewsService newsService) {
    this.newsService = newsService;
    this.spaceService = spaceService;
    this.identityManager = identityManager;
    this.activityManager = activityManager;
  }

  @Override
  public void shareActivity(ActivityLifeCycleEvent event) {
    ExoSocialActivity sharedActivity = event.getActivity();
    if (sharedActivity.getTemplateParams() != null && sharedActivity.getTemplateParams().containsKey("originalActivityId")) {
      String originalActivityId = sharedActivity.getTemplateParams().get("originalActivityId");
      ExoSocialActivity originalActivity = activityManager.getActivity(originalActivityId);
      if (originalActivity != null && originalActivity.getTemplateParams() != null
          && originalActivity.getTemplateParams().containsKey("newsId")) {
        Identity posterIdentity = getIdentity(sharedActivity);
        Space space = getSpace(sharedActivity);
        String newsId = originalActivity.getTemplateParams().get("newsId");
        try {
          newsService.shareNews(newsId, space, posterIdentity);
        } catch (Exception e) {
          LOG.error("Error while sharing news {} activity", newsId, e);
        }
      }
    }
  }

  private Identity getIdentity(ExoSocialActivity sharedActivity) {
    String posterIdentityId = sharedActivity.getPosterId();
    return identityManager.getIdentity(posterIdentityId);
  }

  private Space getSpace(ExoSocialActivity sharedActivity) {
    String spacePrettyName = sharedActivity.getActivityStream().getPrettyId();
    return spaceService.getSpaceByPrettyName(spacePrettyName);
  }

}
