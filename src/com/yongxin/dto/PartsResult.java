package com.yongxin.dto;

/**
 * Created by Administrator on 2018-01-27.
 */
public class PartsResult {
    String supplierId;
    String supplierName;
    String itemName;
    String standard;
    String testResult;
    String result;
    String testId;
    int pNumber;

    public int getpNumber() {
        return pNumber;
    }

    public void setpNumber(int pNumber) {
        this.pNumber = pNumber;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "PartsResult{" +
                "supplierId='" + supplierId + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", itemName='" + itemName + '\'' +
                ", standard='" + standard + '\'' +
                ", testResult='" + testResult + '\'' +
                ", result='" + result + '\'' +
                ", testId='" + testId + '\'' +
                ", pNumber=" + pNumber +
                '}';
    }
}
