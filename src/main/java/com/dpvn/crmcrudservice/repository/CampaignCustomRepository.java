package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.Campaign;
import com.dpvn.shared.util.StringUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CampaignCustomRepository {
  @PersistenceContext private EntityManager entityManager;

  public List<Campaign> searchCampaigns(
      String campaignName,
      Long picUser,
      Long typeId,
      Integer status,
      Instant fromDate,
      Instant toDate) {
    StringBuilder sql = new StringBuilder("SELECT * FROM crm_campaign cc WHERE 1=1 ");

    if (StringUtil.isNotEmpty(campaignName)) {
      sql.append("AND cc.campaign_name ILIKE '%' || :campaignName || '%'");
    }
    if (picUser != null) {
      sql.append(
          "AND EXISTS (SELECT 1 FROM crm_campaign_user ccu WHERE ccu.campaign_id = cc.id AND ccu.user_id = :picUser) ");
    }
    if (typeId != null) {
      sql.append("AND cc.campaign_type_id = :typeId ");
    }
    if (status != null) {
      sql.append("AND cc.status = :status ");
    }
    if (fromDate != null) {
      sql.append("AND cc.start_date >= :fromDate ");
    }
    if (toDate != null) {
      sql.append("AND cc.end_date <= :toDate ");
    }

    Query query = entityManager.createNativeQuery(sql.toString(), Campaign.class);

    if (StringUtil.isNotEmpty(campaignName)) {
      query.setParameter("campaignName", "%" + campaignName + "%");
    }
    if (picUser != null) {
      query.setParameter("picUser", picUser);
    }
    if (typeId != null) {
      query.setParameter("typeId", typeId);
    }
    if (status != null) {
      query.setParameter("status", status);
    }
    if (fromDate != null) {
      query.setParameter("fromDate", Timestamp.from(fromDate));
    }
    if (toDate != null) {
      query.setParameter("toDate", Timestamp.from(toDate));
    }

    return query.getResultList();
  }
}
