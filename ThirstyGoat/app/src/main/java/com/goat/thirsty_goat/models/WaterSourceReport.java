package com.goat.thirsty_goat.models;

import java.util.Calendar;

/**
 * Created by Walker on 3/11/17.
 */

public class WaterSourceReport {
    private int mID;
    private String mName;
    private WaterType mWaterType;
    private WaterSourceCondition mWaterCondition;
    private Calendar mCalendar;

    private WaterPurityReport mWaterPurityReport;

    public WaterSourceReport(WaterType type, WaterSourceCondition condition, String name) {
        mCalendar = Calendar.getInstance();
        mID = WaterReport.getAndIncrementID();
        mName = name;
        mWaterType = type;
        mWaterCondition = condition;
    }

    /**
     * Tries to add a water purity report to this water source report. Since a source report can only
     * have one purity report, if a purity report already exists, the old report is replaced with this
     * new one.
     * @param condition the water purity condition
     * @param virusPPM the amount of virus in the water in parts per million
     * @param contaminantPPM the amount of contaminants in the water in parts per million
     * @param name the name of the person who submitted this purity report
     */
    public void addWaterPurityReport(WaterPurityCondition condition, double virusPPM, double contaminantPPM, String name) {
        mWaterPurityReport = new WaterPurityReport(condition, virusPPM, contaminantPPM, name);
    }


    // ##########################################################
    // Methods dealing with getters/setters for WaterSourceReport
    // ##########################################################
    /**
     * Gets the name of the person who submitted this report.
     * @return the report submitter's name
     */
    public String getName() {
        return mName;
    }

    /**
     * Gets the unique ID number of this report.
     * @return this report's unique ID number
     */
    public int getReportNumber() {
        return mID;
    }

    /**
     * Gets the water type of this report.
     * @return the water type of this report
     */
    public WaterType getWaterType() {
        return mWaterType;
    }

    /**
     * Gets the string representation of the water type of this report.
     * @return the string representation of the water type of this report.
     */
    public String getWaterTypeString() {
        return  mWaterType.toString();
    }

    /**
     * Gets the water condition of this report.
     * @return the water condition of this report
     */
    public WaterSourceCondition getWaterCondition() {
        return mWaterCondition;
    }

    /**
     * Gets the string representation of the water condition of this report.
     * @return the string representation of the warer condition of this report.
     */
    public String getWaterConditionString() {
        return mWaterCondition.toString();
    }

    /**
     * Gets the string representation of the date of this report.
     * @return the string representation of the date of this report.
     */
    public String getDateString() {
        return "" + mCalendar.get(Calendar.MONTH) + "/" + mCalendar.get(Calendar.DAY_OF_MONTH)
                + "/" + mCalendar.get(Calendar.YEAR);
    }

    // #####################################################
    // Methods dealing with the associated WaterPurityReport
    // #####################################################
    /**
     * Gets the purity report associated with this water source report.
     * @return the purity report associated with this source report, or null if none exists
     */
    public WaterPurityReport getWaterPurityReport() {
        return mWaterPurityReport;
    }

    /**
     * Gets the reporter's name for the purity report associated with this water source report.
     * @return the reporter's name for the purity report associated with this source report, or null if none exists
     */
    public String getWaterPurityReportName() {
        if (mWaterPurityReport == null) {
            return null;
        } else {
            return mWaterPurityReport.getName();
        }
    }

    /**
     * Gets the report number of the purity report associated with this water source report.
     * @return the report number of the purity report associated with this source report, or -1 if none exists
     */
    public int getWaterPurityReportNumber() {
        if (mWaterPurityReport == null) {
            return -1;
        } else {
            return mWaterPurityReport.getReportNumber();
        }
    }

    /**
     * Gets the purity condition from the purity report associated with this water source report.
     * @return the purity condition from the purity report associated with this source report, or null if none exists
     */
    public String getWaterPurityReportConditionString() {
        if (mWaterPurityReport == null) {
            return null;
        } else {
            return mWaterPurityReport.getWaterConditionString();
        }
    }

    /**
     * Gets the date from the purity report associated with this water source report.
     * @return the date from the purity report associated with this source report, or null if none exists
     */
    public String getWaterPurityReportDateString() {
        if (mWaterPurityReport == null) {
            return null;
        } else {
            return mWaterPurityReport.getDateString();
        }
    }
}