package com.dpvn.crmcrudservice.campaigntype;

import com.dpvn.crmcrudservice.AbstractService;
import com.dpvn.crmcrudservice.domain.entity.CampaignType;
import com.dpvn.crmcrudservice.repository.CampaignTypeRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CampaignTypeService extends AbstractService<CampaignType> {

  public CampaignTypeService(CampaignTypeRepository repository) {
    super(repository);
  }

  @Override
  public void sync(List<CampaignType> entities) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
