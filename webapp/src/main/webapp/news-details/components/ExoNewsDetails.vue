<template>
  <div id="newsDetails">
    <exo-news-details-toolbar
      v-if="!isMobile"
      :news="news"
      :news-id="newsId"
      :activity-id="activityId"
      :show-edit-button="showEditButton"
      :show-delete-button="showDeleteButton"
      :show-publish-button="showPublishButton" />
    <exo-news-details-toolbar-mobile
      v-if="isMobile"
      :news="news"
      :show-edit-button="showEditButton"
      :show-delete-button="showDeleteButton"
      :show-publish-button="showPublishButton" />
    <schedule-news-drawer
      @post-article="postNews"
      :news-id="newsId" />
    <exo-confirm-dialog
      ref="deleteConfirmDialog"
      :message="$t('news.message.confirmDeleteNews')"
      :title="$t('news.title.confirmDeleteNews')"
      :ok-label="$t('news.button.ok')"
      :cancel-label="$t('news.button.cancel')"
      @ok="deleteNews" />
    <exo-news-details-body
      v-if="!isMobile"
      :news="news" />
    <exo-news-details-body-mobile
      v-if="isMobile"
      :news="news"
      :news-id="newsId"
      :space="currentSpace" />
    <exo-news-edit-publishing-drawer
      :news="news"
      @refresh-news="getNewsById(newsId)" />
    <exo-news-notification-alerts />
  </div>
</template>
<script>

const USER_TIMEZONE_ID = new window.Intl.DateTimeFormat().resolvedOptions().timeZone;
export default {
  props: {
    news: {
      type: Object,
      required: false,
      default: function() { return new Object(); }
    },
    newsId: {
      type: String,
      required: false,
      default: null
    },
    activityId: {
      type: String,
      required: false,
      default: ''
    },
    showEditButton: {
      type: Boolean,
      required: false,
      default: false
    },
    showPublishButton: {
      type: Boolean,
      required: false,
      default: false
    },
    showDeleteButton: {
      type: Boolean,
      required: false,
      default: false
    },
  },
  data() {
    return {
      currentSpace: null,
      spaceId: null,
      BYTES_IN_MB: 1048576,
      dateFormat: {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
      },
      dateTimeFormat: {
        hour: '2-digit',
        minute: '2-digit',
      },
    };
  },
  computed: {
    isMobile() {
      return this.$vuetify.breakpoint.name === 'xs' || this.$vuetify.breakpoint.name === 'sm';
    },
  },
  created() {
    this.$root.$on('delete-news', this.deleteConfirmDialog);
    this.$root.$on('edit-news', this.editLink);
    if (!this.news || !this.news.spaceId) {
      this.getNewsById(this.newsId);
    } else {
      this.spaceId = this.news.spaceId;
      this.getSpaceById(this.spaceId );
      if (!this.news.newsId) {
        this.news.newsId = this.newsId;
      }
      this.$root.$emit('application-loaded');
    }
  },
  mounted() {
    this.markNewsAsRead(this.newsId);
  },
  methods: {
    markNewsAsRead(newsId) {
      if (newsId) {
        this.$newsServices.markNewsAsRead(newsId);
      }
    },
    getSpaceById(spaceId) {
      this.$spaceService.getSpaceById(spaceId, 'identity')
        .then((space) => {
          if (space && space.identity && space.identity.id) {
            this.currentSpace = space;
          }
        });
    },
    editLink() {
      const editUrl = `${eXo.env.portal.context}/${eXo.env.portal.portalName}/news/editor?spaceId=${this.spaceId}&newsId=${this.newsId}&activityId=${this.activityId}`;
      window.open(editUrl, '_target');
    },
    deleteConfirmDialog() {
      this.$refs.deleteConfirmDialog.open();
    },
    deleteNews() {
      const deleteDelay = 6;
      const redirectionTime = 6100;
      this.$newsServices.deleteNews(this.newsId, false, deleteDelay)
        .then(() => {
          this.$root.$emit('confirm-news-deletion', this.news);
        });
      setTimeout(() => {
        const deletedNews = localStorage.getItem('deletedNews');
        if (deletedNews != null) {
          window.location.href = this.news.spaceUrl;
        }
      }, redirectionTime);
    },
    postNews(schedulePostDate, postArticleMode, publish, isActivityPosted, selectedTargets) {
      this.news.timeZoneId = USER_TIMEZONE_ID;
      this.news.activityPosted = isActivityPosted;
      this.news.published = publish;
      this.news.targets = selectedTargets;
      if (postArticleMode === 'later') {
        this.news.schedulePostDate = schedulePostDate;
        this.$newsServices.scheduleNews(this.news).then((scheduleNews) => {
          if (scheduleNews) {
            window.location.href = scheduleNews.url;
          }
        });
      } else if (postArticleMode === 'immediate') {
        this.news.publicationState = 'published';
        this.$newsServices.saveNews(this.news).then((createdNews) => {
          let createdNewsActivity = null;
          if (createdNews.activities) {
            const createdNewsActivities = createdNews.activities.split(';')[0].split(':');
            if (createdNewsActivities.length > 1) {
              createdNewsActivity = createdNewsActivities[1];
            }
          }
          if (createdNewsActivity) {
            window.location.href = `${eXo.env.portal.context}/${eXo.env.portal.portalName}/activity?id=${createdNewsActivity}`;
          } else {
            window.location.href = `${eXo.env.portal.context}/${eXo.env.portal.portalName}`;
          }
        });
      } else {
        this.news.publicationState = 'draft';
        this.$newsServices.saveNews(this.news).then((createdNews) => {
          this.news.id = createdNews.id;
          this.$emit('draftCreated');
          if (createdNews) {
            window.location.href = `${eXo.env.portal.context}/${eXo.env.portal.portalName}/news?filter=drafts`;
          }
        });
      }
    },
    getNewsById(newsId) {
      this.$newsServices.getNewsById(newsId)
        .then(news => {
          this.spaceId = news.spaceId;
          this.getSpaceById(this.spaceId);
          if (!this.news) {
            this.news = news;
          }
          if (!this.news.newsId) {
            this.news.newsId = newsId;
          }
          return this.$nextTick();
        })
        .finally(() => {
          document.title = this.$t('news.window.title', {0: this.news.title});
          this.$root.$emit('application-loaded');
        });
    }
  }
};
</script>
