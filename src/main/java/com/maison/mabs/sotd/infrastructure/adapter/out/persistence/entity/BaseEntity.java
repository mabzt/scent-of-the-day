package com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

	@JsonIgnore
	@CreatedDate
	private Instant createdOn;

	@JsonIgnore
	@LastModifiedDate
	private Instant updatedOn;

	@CreatedBy
	@JsonIgnore
	@Column
	private String createdBy;

	@JsonIgnore
	@LastModifiedBy
	@Column
	private String lastModifiedBy;

}
