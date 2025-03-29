package com.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.api.model.*;

public interface NameRepository extends JpaRepository<NameEntity, Long> {
}
