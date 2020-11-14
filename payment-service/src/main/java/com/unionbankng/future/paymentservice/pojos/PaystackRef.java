package com.unionbankng.future.paymentservice.pojos;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.unionbankng.marcus.enums.Gateway;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paystackRef")
@Getter
@Setter
public class PaystackRef implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 70)
	private String ref;
	@Column(nullable = true, length = 30)
	private String status;
	@Column(nullable = true)
	private Double amount;
	@Column(nullable = true, length = 15)
	private String walletId;
	@Enumerated
	private Gateway gateway;
	@Column(updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;
	
	
	public PaystackRef() {
		
		
	}
	
	public PaystackRef(PaystackRef ref) {
		this.id = ref.getId();
		this.ref = ref.getRef();
		
	}
	
	@PrePersist
	private void setCreatedAt() {
		createdAt = new Date();
	}
	
	@PreUpdate
	private void setUpdatedAt() {
		updatedAt = new Date();
	}

	@Override
	public boolean equals(Object wallet) {
		return this.id.equals(((Wallet)wallet).getId()) ;
		
	}
	
}
