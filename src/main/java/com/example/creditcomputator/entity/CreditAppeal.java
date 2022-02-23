package com.example.creditcomputator.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_appeals")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@DynamicUpdate
public class CreditAppeal {
	
	@Id
	@Builder.Default
	private String id = UUID.randomUUID().toString();
	
	@Column(nullable = true) //it's nullable = true by default, but we still put this here for doc purposes
	private Double creditLimit;
	
	@NotNull
	private Boolean creditApprovalStatus;
	
	@ManyToOne
	@JoinColumn(name = "user_national_id", referencedColumnName = "national_id")
	@NotNull
//	@JsonIgnore
	private AppUser appUser;
	
	@NotNull
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	
}

