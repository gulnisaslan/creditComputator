package com.example.creditcomputator.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "app_users")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
@DynamicUpdate
public class AppUser {
	
	@Id
	@Column(name = "national_id", length = 11)
    @Size(min = 11, max = 11, message = "National id's length must be exactly 11")
	private String nationalId;
	
	@NotNull
	private String firstName;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private Double monthlyIncome;
	
	@NotNull
	private String phoneNumber;
	
	@NotNull
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	
	@OneToOne(mappedBy = "appUser", orphanRemoval = true)
	private CreditScore creditScore;
	
	@OneToMany(mappedBy = "appUser", orphanRemoval = true)
	private List<CreditAppeal> creditAppeals;

	
}
