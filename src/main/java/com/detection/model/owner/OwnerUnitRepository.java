package com.detection.model.owner;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerUnitRepository extends JpaRepository<OwnerUnit, Integer> {
    public List<OwnerUnit> findByOwnerName(String ownerName);

    public List<OwnerUnit> findByEmail(String email);
}
