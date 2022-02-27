package com.example.creditcomputator.notification;

import java.time.LocalDateTime;

import com.example.creditcomputator.entity.AppUser;

public interface NotificationService {
	
	void notifyCreditLimitAppeal(AppUser appUser, Double creditLimit,
			Boolean creditApprovalStatus, LocalDateTime executedAt);

}
