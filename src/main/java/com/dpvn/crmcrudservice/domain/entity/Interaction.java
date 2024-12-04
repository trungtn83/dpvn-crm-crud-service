package com.dpvn.crmcrudservice.domain.entity;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.crmcrudservice.domain.BeanMapper;
import com.dpvn.crmcrudservice.domain.constant.Visibility;
import com.dpvn.crmcrudservice.domain.dto.InteractionDto;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "interaction")
public class Interaction extends BaseEntity {
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // TODO: maybe does not need now
  private Integer typeId;

  @Column(columnDefinition = "TEXT")
  private String type;

  @Column(nullable = false)
  private Long interactBy;

  private Long campaignId;
  private Long customerId;

  @Column(columnDefinition = "TEXT")
  private String title;

  @Column(columnDefinition = "TEXT")
  private String content;

  @Column(columnDefinition = "TEXT")
  private String urls;

  @Column(columnDefinition = "TEXT")
  private String images;

  @Column(columnDefinition = "TEXT")
  private String audios;

  @Column(columnDefinition = "TEXT")
  private String videos;

  @Column(columnDefinition = "TEXT")
  private String files;

  @Column(nullable = false)
  private Integer visibility = Visibility.PRIVATE;

  @Override
  public InteractionDto toDto() {
    InteractionDto dto = BeanMapper.instance().map(this, InteractionDto.class);
    dto.setUrls(StringUtil.split(urls));
    dto.setImages(StringUtil.split(images));
    dto.setAudios(StringUtil.split(audios));
    dto.setVideos(StringUtil.split(videos));
    dto.setFiles(StringUtil.split(files));
    return dto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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
}
