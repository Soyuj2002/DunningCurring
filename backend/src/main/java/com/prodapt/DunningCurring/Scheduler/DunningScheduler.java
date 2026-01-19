package com.prodapt.DunningCurring.Scheduler;



import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.prodapt.DunningCurring.DAO.DunningLogRepository;
import com.prodapt.DunningCurring.DAO.DunningRuleRepository;
import com.prodapt.DunningCurring.DAO.TelecomServiceRepository;
import com.prodapt.DunningCurring.Entity.DunningLog;
import com.prodapt.DunningCurring.Entity.DunningRule;
import com.prodapt.DunningCurring.Entity.TelecomService;

@Component
public class DunningScheduler {

    @Autowired
    private TelecomServiceRepository serviceRepository;

    @Autowired
    private DunningRuleRepository ruleRepository;

    @Autowired
    private DunningLogRepository logRepository;

    // Runs every day at 12:00 AM
    @Scheduled(cron = "0 0 0 * * ?")
    public void runDunningProcess() {

        List<TelecomService> services = serviceRepository.findAll();
        List<DunningRule> rules = ruleRepository.findByActiveTrueOrderByOverdueDaysAsc();

        for (TelecomService service : services) {

            if (service.getNextDueDate() == null) continue;

            long overdueDays = ChronoUnit.DAYS.between(
                    service.getNextDueDate(), LocalDate.now()
            );

            for (DunningRule rule : rules) {
                if (overdueDays >= rule.getOverdueDays()) {
                    applyRule(service, rule);
                }
            }
        }
    }

    private void applyRule(TelecomService service, DunningRule rule) {

        String oldStatus = service.getStatus();

        if ("BLOCK".equals(rule.getAction())) {
            service.setStatus("BLOCKED");
        } else if ("RESTRICT".equals(rule.getAction())) {
            service.setStatus("RESTRICTED");
        }

        serviceRepository.save(service);

        DunningLog log = new DunningLog();
        log.setService(service);
        log.setRule(rule);
        log.setActionTaken(rule.getAction());
        log.setStatusBefore(oldStatus);
        log.setStatusAfter(service.getStatus());

        logRepository.save(log);
    }
}


