package com.example.creditcomputator.notification;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.creditcomputator.entity.AppUser;

@Service
public class SmsNotificationService implements NotificationService{

	@Override
	public void notifyCreditLimitAppeal(AppUser appUser, Double creditLimit,
			Boolean creditApprovalStatus, LocalDateTime executedAt) {

		if(creditApprovalStatus) {
			System.out.println("Sayın " + appUser.getFirstName() + appUser.getLastName() 
							+ " kredi başvuru sonucunuz onaylandı. " + "Kredi limitiniz: " 
							+ creditLimit + ". " + executedAt.toString());
		}
		
		else if (!creditApprovalStatus){
			System.out.println("Sayın " + appUser.getFirstName() + appUser.getLastName() 
							+ " kredi başvuru sonucunuz reddedildi. " + executedAt.toString());
		}
		
		else {
			System.out.println("Unknown.");
		}
		
		
	}
	
	
	
}


