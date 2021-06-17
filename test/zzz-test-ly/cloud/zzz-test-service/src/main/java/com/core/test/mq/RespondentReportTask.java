package com.core.test.mq;


import org.springframework.data.annotation.Id;

import java.io.Serializable;


/**
 * 调查对象报表任务（对应二期归集表）
 *
 * @author luyi
 * @date 2021/3/23
 */

public class RespondentReportTask implements Serializable {
    /**
     * 主键ID
     */
    @Id
    private String id;
    /**
     * 报表编码
     */
    private String reportCode;
    /**
     * 调查对象ID
     */
    private String respondentId;
    /**
     * 调查对象名称
     */
    private String respondentCaption;
    /**
     * 数据处理地
     */
    private String areaId;
    /**
     * 调查方案ID
     */
    private String surveyTaskId;
    /**
     * 机构ID
     */
    private String agencyId;
    /**
     * 状态
     */
    private String respondentTaskStatus;

    /**
     * 核实标记 0未审核 1审核错误  2审核通过
     */
    private String checkflag;

    /**
     * 报告期
     */
    private String periodId;
    /**
     * 直报开始时间
     */
    private Long taskStartTime;
    /**
     * 直报结束时间
     */
    private Long taskEndTime;
    /**
     * 补报开始时间
     */
    private Long extraStartTime;
    /**
     * 补报结束时间
     */
    private Long extraEndTime;
    /**
     * 联系人
     */
    private String contacts;
    /**
     * 联系电话
     */
    private String phoneNum;

    /**
     * 0 直报  1待报
     */
    private String gatherWay;
    /**
     * 国家验收结果
     */
    private Integer nationCheckResult;
    /**
     * 省级验收结果
     */
    private Integer provinceCheckResult;
    /**
     * 市级验收结果
     */
    private Integer municipalCheckResult;
    /**
     * 区县验收结果
     */
    private Integer countyCheckResult;


    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public String getRespondentId() {
        return respondentId;
    }

    public void setRespondentId(String respondentId) {
        this.respondentId = respondentId;
    }

    public String getRespondentCaption() {
        return respondentCaption;
    }

    public void setRespondentCaption(String respondentCaption) {
        this.respondentCaption = respondentCaption;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getSurveyTaskId() {
        return surveyTaskId;
    }

    public void setSurveyTaskId(String surveyTaskId) {
        this.surveyTaskId = surveyTaskId;
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }


    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }


    public String getRespondentTaskStatus() {
        return respondentTaskStatus;
    }

    public void setRespondentTaskStatus(String respondentTaskStatus) {
        this.respondentTaskStatus = respondentTaskStatus;
    }

    public Long getTaskStartTime() {
        return taskStartTime;
    }

    public void setTaskStartTime(Long taskStartTime) {
        this.taskStartTime = taskStartTime;
    }

    public Long getTaskEndTime() {
        return taskEndTime;
    }

    public void setTaskEndTime(Long taskEndTime) {
        this.taskEndTime = taskEndTime;
    }

    public Long getExtraStartTime() {
        return extraStartTime;
    }

    public void setExtraStartTime(Long extraStartTime) {
        this.extraStartTime = extraStartTime;
    }

    public Long getExtraEndTime() {
        return extraEndTime;
    }

    public void setExtraEndTime(Long extraEndTime) {
        this.extraEndTime = extraEndTime;
    }

    public String getContacts() {
        return contacts;
    }

    public String getGatherWay() {
        return gatherWay;
    }

    public void setGatherWay(String gatherWay) {
        this.gatherWay = gatherWay;
    }

    public Integer getNationCheckResult() {
        return nationCheckResult;
    }

    public void setNationCheckResult(Integer nationCheckResult) {
        this.nationCheckResult = nationCheckResult;
    }

    public Integer getProvinceCheckResult() {
        return provinceCheckResult;
    }

    public void setProvinceCheckResult(Integer provinceCheckResult) {
        this.provinceCheckResult = provinceCheckResult;
    }

    public Integer getMunicipalCheckResult() {
        return municipalCheckResult;
    }

    public void setMunicipalCheckResult(Integer municipalCheckResult) {
        this.municipalCheckResult = municipalCheckResult;
    }

    public Integer getCountyCheckResult() {
        return countyCheckResult;
    }

    public void setCountyCheckResult(Integer countyCheckResult) {
        this.countyCheckResult = countyCheckResult;
    }

    public String getCheckflag() {
        return checkflag;
    }

    public RespondentReportTask setCheckflag(String checkflag) {
        this.checkflag = checkflag;
        return this;
    }

    @Override
    public String toString() {
        return "RespondentReportTask{" +
                "id='" + id + '\'' +
                ", reportCode='" + reportCode + '\'' +
                ", respondentId='" + respondentId + '\'' +
                ", areaId='" + areaId + '\'' +
                ", agencyId='" + agencyId + '\'' +
                ", periodId='" + periodId + '\'' +
                '}';
    }
}
