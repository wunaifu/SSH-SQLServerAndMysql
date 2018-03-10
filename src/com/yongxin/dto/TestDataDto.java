package com.yongxin.dto;

import java.util.List;

/**
 * Created by Administrator on 2018-02-03.
 */
public class TestDataDto {
    int pNumber;
    List<PartsResult> partsResults;

    public int getpNumber() {
        return pNumber;
    }

    public void setpNumber(int pNumber) {
        this.pNumber = pNumber;
    }

    public List<PartsResult> getPartsResults() {
        return partsResults;
    }

    public void setPartsResults(List<PartsResult> partsResults) {
        this.partsResults = partsResults;
    }

    @Override
    public String toString() {
        return "TestDataDto{" +
                "pNumber=" + pNumber +
                ", partsResults=" + partsResults +
                '}';
    }
}
