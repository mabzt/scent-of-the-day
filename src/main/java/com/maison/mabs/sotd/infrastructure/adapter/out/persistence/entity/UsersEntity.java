package com.maison.mabs.sotd.infrastructure.adapter.out.persistence.entity;

import com.maison.mabs.sotd.domain.model.FragranceType;
import com.maison.mabs.sotd.domain.model.ProfileStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(name = "email_constraint", columnNames = "email") })
public class UsersEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	private String firstName;

	private String lastName;

	private String email;

	@Embedded
	private Location location;

	@Enumerated(EnumType.STRING)
	private ProfileStatus status;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FragranceCollectionEntity> fragranceCollection = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "user_fragrance_types", joinColumns = @JoinColumn(name = "user_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "fragrance_type", nullable = false)
	private Set<FragranceType> fragranceTypes = new HashSet<>();

}
