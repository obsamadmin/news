package org.exoplatform.news.webui.activity;

import org.apache.commons.lang3.StringUtils;

import org.exoplatform.commons.utils.CommonsUtils;
import org.exoplatform.social.core.identity.model.Identity;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.core.space.model.Space;
import org.exoplatform.social.core.space.spi.SpaceService;
import org.exoplatform.social.webui.activity.BaseUIActivity;
import org.exoplatform.webui.config.annotation.ComponentConfig;
import org.exoplatform.webui.config.annotation.EventConfig;
import org.exoplatform.webui.core.lifecycle.UIFormLifecycle;

@ComponentConfig(lifecycle = UIFormLifecycle.class, template = "war:/groovy/news/webui/activity/UISharedNewsActivity.gtmpl", events = {
    @EventConfig(listeners = BaseUIActivity.LoadLikesActionListener.class),
    @EventConfig(listeners = BaseUIActivity.ToggleDisplayCommentFormActionListener.class),
    @EventConfig(listeners = UINewsActivity.LikeActivityActionListener.class),
    @EventConfig(listeners = BaseUIActivity.SetCommentListStatusActionListener.class),
    @EventConfig(listeners = UINewsActivity.PostCommentActionListener.class),
    @EventConfig(listeners = BaseUIActivity.DeleteActivityActionListener.class),
    @EventConfig(listeners = BaseUIActivity.DeleteCommentActionListener.class),
    @EventConfig(listeners = BaseUIActivity.LikeCommentActionListener.class),
    @EventConfig(listeners = BaseUIActivity.EditActivityActionListener.class),
    @EventConfig(listeners = BaseUIActivity.EditCommentActionListener.class) })
public class UISharedNewsActivity extends UINewsActivity {
  public static final String ACTIVITY_TYPE = "shared_news";

  public String getSharedActivityId() {
    return this.getActivity().getId();
  }

  public Space getOriginalNewsSpace() {
    String spaceId = this.getNews().getSpaceId();
    if(StringUtils.isNotBlank(spaceId)) {
      SpaceService spaceService = CommonsUtils.getService(SpaceService.class);
      return spaceService.getSpaceById(spaceId);
    }
    return null;
  }

  public String getPosterFullName() {
    IdentityManager identityManager = CommonsUtils.getService(IdentityManager.class);
    Identity poster = identityManager.getIdentity(this.getActivity().getUserId(), true);
    if (poster != null) {
      return poster.getProfile().getFullName();
    } else {
      return null;
    }
  }

  public String getPosterUserName() {
    IdentityManager identityManager = CommonsUtils.getService(IdentityManager.class);
    Identity poster = identityManager.getIdentity(this.getActivity().getUserId(), true);
    if (poster != null) {
      return poster.getRemoteId();
    } else {
      return null;
    }
  }

  public String getPosterUrl() {
    IdentityManager identityManager = CommonsUtils.getService(IdentityManager.class);
    Identity poster = identityManager.getIdentity(this.getActivity().getUserId(), true);
    if (poster != null) {
      return poster.getProfile().getUrl();
    } else {
      return null;
    }
  }
}
