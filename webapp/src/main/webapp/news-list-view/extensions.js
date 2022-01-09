/*
 * Copyright (C) 2021 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
extensionRegistry.registerComponent('NewsList', 'views', {
  id: 'NewsEmptyTemplate',
  vueComponent: Vue.options.components['news-empty-template'],
  rank: 10,
});

extensionRegistry.registerComponent('NewsList', 'views', {
  id: 'NewsSlider',
  vueComponent: Vue.options.components['news-slider-view'],
  rank: 20,
});

extensionRegistry.registerComponent('NewsList', 'views', {
  id: 'NewsLatest',
  vueComponent: Vue.options.components['news-latest-view'],
  rank: 30,
});

extensionRegistry.registerComponent('NewsList', 'views', {
  id: 'NewsSliderEmptyTemplate',
  vueComponent: Vue.options.components['news-empty-slider-view'],
  rank: 40,
});

extensionRegistry.registerComponent('NewsList', 'views', {
  id: 'NewsLatestEmptyTemplate',
  vueComponent: Vue.options.components['news-empty-latest-view'],
  rank: 50,
});