<%@ page import="org.exoplatform.commons.utils.CommonsUtils" %>
<%@ page import="org.exoplatform.news.NewsService" %>

<%
  NewsService newsService = CommonsUtils.getService(NewsService.class);
  boolean showPinInput = newsService.canPinNews();
  String tinyMCEApiKey = System.getProperty("exo.tinyMCE.apiKey","");
%>

<div id="NewsComposerApp"></div>

<script>
require(['PORTLET/news/NewsComposer'], function(newsComposer) {
  newsComposer.init(<%=showPinInput%>, "<%=tinyMCEApiKey%>");
});
</script>