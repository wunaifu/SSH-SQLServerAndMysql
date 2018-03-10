package com.yongxin.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018-01-12.
 */
public class Result {

    String supplierId;
    String supplierName;//供应商
    List<CheckItem> itemA=new ArrayList<>();//每一项

    @Override
    public String toString() {
        return "Result{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", itemA=" + itemA +
                '}';
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public List<CheckItem> getItemA() {
        return itemA;
    }

    public void setItemA(List<CheckItem> itemA) {
        this.itemA = itemA;
    }
}
