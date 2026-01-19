package com.prodapt.DunningCurring.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.prodapt.DunningCurring.DAO.DunningRuleRepository;
import com.prodapt.DunningCurring.DAO.UserRepository;
import com.prodapt.DunningCurring.DTO.LogDTO;
import com.prodapt.DunningCurring.Entity.DunningRule;
import com.prodapt.DunningCurring.Entity.User;
import com.prodapt.DunningCurring.Service.ActivityLogService;
import com.prodapt.DunningCurring.Service.DunningService;

@RestController
@RequestMapping("/api/dunning")
public class DunningController {

	@Autowired
	private DunningService dunningService;
	@Autowired
	private DunningRuleRepository ruleRepository;

//    Activity Logging, admin actions
	@Autowired
	private ActivityLogService activityLogService;
	@Autowired
	private UserRepository userRepository;

	// Run dunning manually (admin)
	@PostMapping("/run")
	public String runDunning() {
		dunningService.runDunning();
//        logging the activity
		User admin = userRepository.findByUsername("admin_super").orElse(null);
		if (admin != null) {
			activityLogService.log(admin, "Manual Dunning Trigger", "System", 0L);

		}
		return "Dunning process executed successfully";
	}

	@PostMapping("/rules")
	public DunningRule createRule(@RequestBody DunningRule rule) {
		DunningRule savedRule = ruleRepository.save(rule);

//        Log rule creation
		User admin = userRepository.findByUsername("admin_super").orElse(null);
		if (admin != null) {
			activityLogService.log(admin, "CREATE_RULE", "DunningRule", savedRule.getId());
		}
		return savedRule;
	}

	@GetMapping("/rules")
	public Iterable<DunningRule> getAllRules() {
		return ruleRepository.findAll();
	}

	// Usage: DELETE /api/dunning/rules/5
	@DeleteMapping("/rules/{id}")
	public String deleteRule(@PathVariable Long id) {

		// 1. Check if rule exists (Best Practice)
		if (!ruleRepository.existsById(id)) {
			throw new RuntimeException("Rule not found with id: " + id);
		}
		// 2. Perform the delete
		ruleRepository.deleteById(id);
		// 3. Log the activity
		User admin = userRepository.findByUsername("admin_super").orElse(null);
		if (admin != null) {
			activityLogService.log(admin, "DELETE_RULE", "DunningRule", id);
		}
		return "Rule deleted successfully";
	}
	// ... imports

    @GetMapping("/logs")
    public List<LogDTO> getViewLogs() {
        return activityLogService.getAllLogs();
    }

}
