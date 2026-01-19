package com.prodapt.DunningCurring.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prodapt.DunningCurring.Entity.DunningLog;

import java.util.List;

public interface DunningLogRepository extends JpaRepository<DunningLog, Long> {

    List<DunningLog> findByServiceId(Long serviceId);
}

