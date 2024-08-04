package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class InteractionDto extends BaseDto {
  private Long interactionTypeId;
  private Long campaignId;
  private Long customerId;
  private Long interactBy;
  private Long channelId;
  private String content;
  private String urls;
  private String images;
  private String audios;
  private String videos;
  private String files;
  private Integer visibility;

  @Override
  public Interaction toEntity() {
    return BeanMapper.instance().map(this, Interaction.class);
  }

  public Long getInteractionTypeId() {
    return interactionTypeId;
  }

  public void setInteractionTypeId(Long interactionTypeId) {
    this.interactionTypeId = interactionTypeId;
  }

  public Long getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(Long campaignId) {
    this.campaignId = campaignId;
  }

  public Long getCustomerId() {
    return customerId;
  }

  public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  public Long getInteractBy() {
    return interactBy;
  }

  public void setInteractBy(Long interactBy) {
    this.interactBy = interactBy;
  }

  public Long getChannelId() {
    return channelId;
  }

  public void setChannelId(Long channelId) {
    this.channelId = channelId;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUrls() {
    return urls;
  }

  public void setUrls(String urls) {
    this.urls = urls;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getAudios() {
    return audios;
  }

  public void setAudios(String audios) {
    this.audios = audios;
  }

  public String getVideos() {
    return videos;
  }

  public void setVideos(String videos) {
    this.videos = videos;
  }

  public String getFiles() {
    return files;
  }

  public void setFiles(String files) {
    this.files = files;
  }

  public Integer getVisibility() {
    return visibility;
  }

  public void setVisibility(Integer visibility) {
    this.visibility = visibility;
  }
}
