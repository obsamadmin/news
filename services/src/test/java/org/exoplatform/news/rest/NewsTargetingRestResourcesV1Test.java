package org.exoplatform.news.rest;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.news.service.NewsTargetingService;
import org.exoplatform.services.rest.impl.RuntimeDelegateImpl;
import org.exoplatform.services.security.ConversationState;
import org.exoplatform.services.security.Identity;
import org.exoplatform.social.core.manager.IdentityManager;
import org.exoplatform.social.metadata.model.Metadata;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.RuntimeDelegate;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class NewsTargetingRestResourcesV1Test {

  @Mock
  NewsTargetingService newsTargetingService;

  @Mock
  PortalContainer      container;

  @Mock
  IdentityManager      identityManager;

  @Before
  public void setup() {
    RuntimeDelegate.setInstance(new RuntimeDelegateImpl());
  }

  @Test
  public void shouldReturnOkWhenGetTargets() {
    // Given
    NewsTargetingRestResourcesV1 newsTargetingRestResourcesV1 = new NewsTargetingRestResourcesV1(newsTargetingService, container);
    HttpServletRequest request = mock(HttpServletRequest.class);
    lenient().when(request.getRemoteUser()).thenReturn("john");

    // When
    Response response = newsTargetingRestResourcesV1.getTargets(request);

    // Then
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void shouldReturnOkWhenGetReferencedTargets() {
    // Given
    NewsTargetingRestResourcesV1 newsTargetingRestResourcesV1 = new NewsTargetingRestResourcesV1(newsTargetingService, container);
    HttpServletRequest request = mock(HttpServletRequest.class);
    lenient().when(request.getRemoteUser()).thenReturn("john");

    // When
    Response response = newsTargetingRestResourcesV1.getReferencedTargets(request);

    // Then
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void shouldReturnOkWhenDeleteNewsTarget() {
    // Given
    NewsTargetingRestResourcesV1 newsTargetingRestResourcesV1 = new NewsTargetingRestResourcesV1(newsTargetingService, container);
    HttpServletRequest request = mock(HttpServletRequest.class);
    lenient().when(request.getRemoteUser()).thenReturn("john");
    Identity currentIdentity = new Identity("john");
    ConversationState.setCurrent(new ConversationState(currentIdentity));

    List<NewsTargetingEntity> targets = new LinkedList<>();
    NewsTargetingEntity newsTargetingEntity = new NewsTargetingEntity();
    newsTargetingEntity.setName("test1");
    targets.add(newsTargetingEntity);
    lenient().when(newsTargetingService.getTargets()).thenReturn(targets);

    // When
    Response response = newsTargetingRestResourcesV1.deleteTarget(request, targets.get(0).getName(), 0);

    // Then
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void shouldReturnOkWhenCreateTargets() throws IllegalAccessException {
    // Given
    NewsTargetingRestResourcesV1 newsTargetingRestResourcesV1 = new NewsTargetingRestResourcesV1(newsTargetingService, container);
    HttpServletRequest request = mock(HttpServletRequest.class);
    lenient().when(request.getRemoteUser()).thenReturn("john");
    Identity currentIdentity = new Identity("john");
    ConversationState.setCurrent(new ConversationState(currentIdentity));
    Metadata sliderNews = new Metadata();
    sliderNews.setName("sliderNews");
    sliderNews.setCreatedDate(100);
    HashMap<String, String> sliderNewsProperties = new HashMap<>();
    sliderNewsProperties.put("label", "slider news");
    sliderNews.setProperties(sliderNewsProperties);
    sliderNews.setId(1);
    NewsTargetingEntity newsTargetingEntity = new NewsTargetingEntity();
    newsTargetingEntity.setName(sliderNews.getName());
    lenient().when(newsTargetingService.createNewsTarget(newsTargetingEntity, currentIdentity)).thenReturn(sliderNews);

    // When
    Response response = newsTargetingRestResourcesV1.createNewsTarget(request, newsTargetingEntity);

    // Then
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

  @Test
  public void shouldReturnOkWhenUpdateTargets() throws IllegalAccessException {
    // Given
    NewsTargetingRestResourcesV1 newsTargetingRestResourcesV1 = new NewsTargetingRestResourcesV1(newsTargetingService, container);
    HttpServletRequest request = mock(HttpServletRequest.class);
    lenient().when(request.getRemoteUser()).thenReturn("john");
    Identity currentIdentity = new Identity("john");
    ConversationState.setCurrent(new ConversationState(currentIdentity));
    Metadata sliderNews = new Metadata();
    sliderNews.setName("sliderNews");
    sliderNews.setCreatedDate(100);
    HashMap<String, String> sliderNewsProperties = new HashMap<>();
    sliderNewsProperties.put("label", "slider news");
    sliderNews.setProperties(sliderNewsProperties);
    sliderNews.setId(1);
    NewsTargetingEntity newsTargetingEntity = new NewsTargetingEntity();
    newsTargetingEntity.setName(sliderNews.getName());
    String originalTargetName = "sliderNews";
    lenient().when(newsTargetingService.updateNewsTargets(originalTargetName, newsTargetingEntity, currentIdentity)).thenReturn(sliderNews);

    // When
    Response response = newsTargetingRestResourcesV1.updateNewsTarget(newsTargetingEntity, originalTargetName);

    // Then
    assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
  }

}
