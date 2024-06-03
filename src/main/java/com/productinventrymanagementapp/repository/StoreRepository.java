package com.productinventrymanagementapp.repository;

import com.productinventrymanagementapp.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store,Long> {
    Store findByStoreCode(@Param("storeCode") String storeCode);
}
