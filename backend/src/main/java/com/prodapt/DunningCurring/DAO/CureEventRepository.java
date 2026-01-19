package com.prodapt.DunningCurring.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.DunningCurring.Entity.CureEvent;

import java.util.List;

public interface CureEventRepository extends JpaRepository<CureEvent, Long> {

    List<CureEvent> findByServiceId(Long serviceId);
}

