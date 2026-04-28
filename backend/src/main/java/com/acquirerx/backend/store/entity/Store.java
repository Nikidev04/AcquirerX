package com.acquirerx.backend.store.entity;

import com.acquirerx.backend.merchant.entity.Merchant;
import jakarta.persistence.*;

@Entity
@Table(name = "stores")
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long storeID;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    private String address;
    private String status;

    public Store() {}

    public Long getStoreID() { return storeID; }
    public void setStoreID(Long storeID) { this.storeID = storeID; }

    public Merchant getMerchant() { return merchant; }
    public void setMerchant(Merchant merchant) { this.merchant = merchant; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
