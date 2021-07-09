package org.exoplatform.news.model;

import org.exoplatform.social.core.space.model.Space;

import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
public class News {

  private String               id;

  private String               title;

  private String               summary;

  private String               body;

  private String               author;

  private String               authorDisplayName;

  private String               updater;

  private String               draftUpdater;

  private String               draftUpdaterDisplayName;

  private String               uploadId;

  private byte[]               illustration;

  private Date                 illustrationUpdateDate;

  private String               illustrationURL;

  private Date                 creationDate;

  private Date                 publicationDate;

  private Date                 updateDate;

  private Date                 draftUpdateDate;

  private boolean              pinned;

  private boolean              archived;

  private boolean              canArchive;

  private String               spaceId;

  private String               spaceDisplayName;

  private String               spaceUrl;

  private boolean              isSpaceMember;

  private String               path;

  private String               publicationState;

  private Long                 viewsCount;

  private String               activities;

  private String               activityId;

  private List<NewsAttachment> attachments;

  private String               spaceAvatarUrl;

  private String               authorAvatarUrl;

  private boolean              canEdit;

  private boolean              canDelete;

  private boolean              canPublish;

  private Set<Space>           sharedInSpacesList;

  private String               url;

  private boolean              hiddenSpace;

  private String               schedulePostDate;

  private String               timeZoneId;

}
