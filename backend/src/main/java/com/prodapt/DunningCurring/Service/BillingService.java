package com.prodapt.DunningCurring.Service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prodapt.DunningCurring.DAO.BillRepository;
import com.prodapt.DunningCurring.Entity.Bill;

@Service
public class BillingService {

	 @Autowired
    private final BillRepository billRepository;

   
    public BillingService(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    public List<Bill> getUnpaidBills(Long serviceId) {
        return billRepository.findByServiceIdAndPaidFalse(serviceId);
    }

    public Bill saveBill(Bill bill) {
        return billRepository.save(bill);
    }
}

