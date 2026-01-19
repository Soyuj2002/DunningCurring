package com.prodapt.DunningCurring.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.DunningCurring.DAO.ActivityLogRepository;
import com.prodapt.DunningCurring.DTO.LogDTO;
import com.prodapt.DunningCurring.Entity.ActivityLog;
import com.prodapt.DunningCurring.Entity.User;

@Service
public class ActivityLogService {

	@Autowired
	private final ActivityLogRepository activityLogRepository;

	public ActivityLogService(ActivityLogRepository activityLogRepository) {
		this.activityLogRepository = activityLogRepository;
	}

	public void log(User admin, String action, String entity, Long entityId) {
		ActivityLog log = new ActivityLog();
		log.setAdminUser(admin);
		log.setAction(action);
		log.setEntityType(entity);
		log.setEntityId(entityId);
		activityLogRepository.save(log);
	}

	public List<LogDTO> getAllLogs() {
		return activityLogRepository.findAllByOrderByTimestampDesc() 
				.stream()
				.map(log -> new LogDTO(
						log.getId(),
						log.getAdminUser().getUsername(),
						log.getAction(),
						log.getEntityType(),
						log.getEntityId(),
						log.getTimestamp()))
				.collect(Collectors.toList());
	}
}
