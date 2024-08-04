package com.dpvn.crmcrudservice.repository;

import com.dpvn.crmcrudservice.domain.entity.CampaignType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignTypeRepository extends JpaRepository<CampaignType, Long> {}
