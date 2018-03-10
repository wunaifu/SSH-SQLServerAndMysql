package com.yongxin.dto;

/**
 * Created by Administrator on 2018-01-13.
 */
public class CheckItem {
    String result;//结果参数
    String checkResult;//检测是否合格

    @Override
    public String toString() {
        return "CheckItem{" +
                "result='" + result + '\'' +
                ", checkResult='" + checkResult + '\'' +
                '}';
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }
}
