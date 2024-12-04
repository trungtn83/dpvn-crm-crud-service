package com.dpvn.crmcrudservice.domain.dto;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.entity.Interaction;
import com.dpvn.shared.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InteractionDto extends BaseDto {
  private Integer typeId;
  private String type;

  private Long interactBy;
  private Long campaignId;
  private Long customerId;

  private String title;
  private String content;

  private List<String> urls;
  private List<String> images;
  private List<String> audios;
  private List<String> videos;
  private List<String> files;

  private Integer visibility;

  @Override
  public Interaction toEntity() {
    Interaction entity = BeanMapper.instance().map(this, Interaction.class);
    entity.setUrls(StringUtil.join(urls));
    entity.setImages(StringUtil.join(images));
    entity.setAudios(StringUtil.join(audios));
    entity.setVideos(StringUtil.join(videos));
    entity.setFiles(StringUtil.join(files));
    return entity;
  }

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getInteractBy() {
    return interactBy;
  }

  public void setInteractBy(Long interactBy) {
    this.interactBy = interactBy;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public List<String> getUrls() {
    return urls;
  }

  public void setUrls(List<String> urls) {
    this.urls = urls;
  }

  public List<String> getImages() {
    return images;
  }

  public void setImages(List<String> images) {
    this.images = images;
  }

  public List<String> getAudios() {
    return audios;
  }

  public void setAudios(List<String> audios) {
    this.audios = audios;
  }

  public List<String> getVideos() {
    return videos;
  }

  public void setVideos(List<String> videos) {
    this.videos = videos;
  }

  public List<String> getFiles() {
    return files;
  }

  public void setFiles(List<String> files) {
    this.files = files;
  }

  public Integer getVisibility() {
    return visibility;
  }

  public void setVisibility(Integer visibility) {
    this.visibility = visibility;
  }
}
