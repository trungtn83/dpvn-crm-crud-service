package com.dpvn.crmcrudservice;

import com.dpvn.crmcrudservice.domain.BaseEntity;
import com.dpvn.shared.util.DateUtil;
import com.dpvn.shared.util.ObjectUtil;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public abstract class AbstractService<E extends BaseEntity> {
  protected static final Logger LOG = LoggerFactory.getLogger(AbstractService.class);

  protected JpaRepository<E, Long> repository;

  public AbstractService(JpaRepository<E, Long> repository) {
    this.repository = repository;
  }

  private void injectDate(E entity) {
    if (entity.getCreatedDate() == null) {
      entity.setCreatedDate(DateUtil.now());
    }
    entity.setModifiedDate(DateUtil.now());
  }

  public E save(E entity) {
    injectDate(entity);
    return repository.save(entity);
  }

  public E update(Long id, E entity) {
    E dbEntity = findById(id).orElseThrow();
    ObjectUtil.assign(dbEntity, entity);
    return save(dbEntity);
  }

  public E upsert(E entity) {
    Object id = ObjectUtil.getField(entity, "id");
    if (id == null) {
      return save(entity);
    }
    return update(Long.valueOf(id.toString()), entity);
  }

  @Transactional
  public void saveAll(List<E> entities) {
    int batchSize = 50; // Adjust batch size as necessary
    for (int i = 0; i < entities.size(); i += batchSize) {
      int end = Math.min(i + batchSize, entities.size());
      List<E> batch = entities.subList(i, end);
      batch.forEach(this::injectDate);
      repository.saveAll(batch);
    }
  }

  public Optional<E> findById(Long id) {
    if (id == null) return Optional.empty();
    return repository.findById(id);
  }

  public List<E> findAll() {
    return repository.findAll();
  }

  public void deleteById(Long id) {
    repository.deleteById(id);
  }

  public void delete(E entity) {
    repository.delete(entity);
  }

  @Transactional
  public abstract void sync(List<E> entities);
}
