package com.dpvn.crmcrudservice;

import com.dpvn.crmcrudservice.domain.BaseDto;
import com.dpvn.crmcrudservice.domain.BaseEntity;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AbstractController<E extends BaseEntity, T extends BaseDto> {

  protected final AbstractService<E> service;

  public AbstractController(AbstractService<E> service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<T> create(@RequestBody T dto) {
    return ResponseEntity.ok(service.save(dto.toEntity()).toDto());
  }

  @PostMapping("/upsert")
  public ResponseEntity<T> upsert(@RequestBody T dto) {
    return ResponseEntity.ok(service.upsert(dto.toEntity()).toDto());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> get(@PathVariable Long id) {
    return service
        .findById(id)
        .map(entity -> ResponseEntity.ok(entity.toDto()))
        .orElse(ResponseEntity.notFound().build());
  }

  @GetMapping
  public List<T> list() {
    return service.findAll().stream().map(e -> (T) e.toDto()).toList();
  }

  @PutMapping("/{id}")
  public ResponseEntity<T> updatePut(@PathVariable Long id, @RequestBody T dto) {
    return ResponseEntity.ok(service.save(dto.toEntity()).toDto());
  }

  @PostMapping("/{id}")
  public ResponseEntity<T> updatePartial(@PathVariable Long id, @RequestBody T dto) {
    return ResponseEntity.ok(service.update(id, dto.toEntity()).toDto());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    service.deleteById(id);
    return ResponseEntity.ok().build();
  }
}
