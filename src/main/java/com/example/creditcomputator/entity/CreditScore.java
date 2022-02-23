package com.example.creditcomputator.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "credit_scores")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
@DynamicUpdate
public class CreditScore {
	
	@Id
	@Builder.Default
	private String id = UUID.randomUUID().toString();
	
	@NotNull
	private Double score;
	
	@OneToOne
	@JoinColumn(name = "user_national_id", referencedColumnName = "national_id", unique = true)
	@NotNull
//	@JsonIgnore
	private AppUser appUser;
	
	@NotNull
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
	
}
