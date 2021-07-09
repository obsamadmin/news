const newsActivityComposerPlugin = {
  key: 'news',
  rank: 30,
  resourceBundle: 'locale.portlet.news.News',
  labelKey: 'news.composer.write',
  description: 'news.composer.write.description',
  iconClass: 'newsComposerIcon',
  enabled: false,
  onExecute: function () {
    let url = `${eXo.env.portal.context}/${eXo.env.portal.portalName}/news/editor`;
    if (eXo.env.portal.spaceId) {
      url += `?spaceId=${eXo.env.portal.spaceId}`;
    }

    const editor = CKEDITOR.instances['activityContent'];
    if (editor) {
      const message = CKEDITOR.instances['activityContent'].getData();
      localStorage.setItem('exo-activity-composer-message', message);
    }

    window.open(url, '_blank');
  }
};

const switchToArticleActivityComposerPlugin = {
  key: 'switchToArticle',
  rank: 10,
  resourceBundle: 'locale.portlet.news.News',
  labelKey: 'news.composer.switch.article',

  onExecute: function (attachments) {
    let url = `${eXo.env.portal.context}/${eXo.env.portal.portalName}/news/editor`;
    if (eXo.env.portal.spaceId) {
      url += `?spaceId=${eXo.env.portal.spaceId}`;
    }

    const editor = CKEDITOR.instances['activityContent'];
    if (editor) {
      const message = CKEDITOR.instances['activityContent'].getData();
      localStorage.setItem('exo-activity-composer-message', message);
      localStorage.setItem('exo-activity-composer-attachments', JSON.stringify(attachments));
    }

    window.open(url, '_blank');
  }
};

require(['SHARED/extensionRegistry'], function (extensionRegistry) {
  extensionRegistry.registerExtension('ActivityComposer', 'activity-composer-hint-action', switchToArticleActivityComposerPlugin);
  document.dispatchEvent(new CustomEvent('activity-composer-extension-updated'));
  if (eXo.env.portal.spaceId) {
    canUserCreateNews().then(canCreateNews => {
      newsActivityComposerPlugin.enabled = eXo.env.portal.spaceId && (canCreateNews === 'true');
      extensionRegistry.registerExtension('ActivityComposer', 'activity-composer-action', newsActivityComposerPlugin);
      document.dispatchEvent(new CustomEvent('activity-composer-extension-updated'));
    });
  } else {
    newsActivityComposerPlugin.enabled = false;
  }
});

function canUserCreateNews() {
  return fetch(`${eXo.env.portal.context}/${eXo.env.portal.rest}/v1/news/canCreateNews/${eXo.env.portal.spaceId}`, {
    headers: {
      'Content-Type': 'application/json'
    },
    method: 'GET'
  }).then((resp) => resp.text()).then(resp => {
    return resp;
  });
}