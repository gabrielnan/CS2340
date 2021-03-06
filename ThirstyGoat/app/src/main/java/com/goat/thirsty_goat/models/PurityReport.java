package com.goat.thirsty_goat.models;

import android.util.Log;

import org.joda.time.LocalDateTime;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Walker on 3/11/17.
 *
 * Water purity report.
 */

public class PurityReport implements Reportable {

    private static final String TAG = PurityReport.class.getSimpleName();

    private int mID;
    private String mName;
    private PurityCondition mCondition;
    private LocalDateTime mDateTime;
    private double mVirusPPM;
    private double mContaminantPPM;

    public PurityReport(PurityCondition condition, double virusPPM, double contaminantPPM, String name) {
//        mID = Report.getAndIncrementID();
        mName = name;
        mCondition = condition;
        mDateTime = LocalDateTime.now();
        mVirusPPM = virusPPM;
        mContaminantPPM = contaminantPPM;
    }

    //TODO: delete this; only for testing
//    public PurityReport(PurityCondition condition, double virusPPM, double contaminantPPM, String name, int month, int dayOfMonth, int year) {
//        mID = Report.getAndIncrementID();
//        mName = name;
//        mCondition = condition;
//        mDateTime = LocalDateTime.now();
//        mDateTime = mDateTime.withMonthOfYear(month).withDayOfMonth(dayOfMonth).withYear(year);
//        mVirusPPM = virusPPM;
//        mContaminantPPM = contaminantPPM;
//    }

    public PurityReport(PurityCondition cond, double virus, double contaminant, String name, int purityId, LocalDateTime dateTime) {
        mID = purityId;
        mName = name;
        mCondition = cond;
        mVirusPPM = virus;
        mContaminantPPM = contaminant;
        mDateTime = dateTime;
    }

    /**
     * Gets the name of the person who submitted this report.
     * @return the report submitter's name
     */
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    /**
     * Gets the unique ID number of this report.
     * @return this report's unique ID number
     */
    public int getReportNumber() {
        return mID;
    }

    /**
     * Gets the water condition of this report.
     * @return the water condition of this report
     */
    public PurityCondition getCondition() {
        return mCondition;
    }

    public void setCondition(PurityCondition condition) {
        mCondition = condition;
    }

    /**
     * Gets the string representation of the water condition of this report.
     * @return the string representation of the water condition of this report.
     */
    public String getConditionString() {
        return mCondition.toString();
    }

    /**
     * Gets the string representation of the date of this report.
     * @return the string representation of the date of this report.
     */
    public String getDateString() {
        return mDateTime.toString();
    }

    public void setDateTime(LocalDateTime dateTime) {
        mDateTime = dateTime;
    }

    /**
     * Gets the concentration of virus in PPM
     * @return virus concentration (PPM)
     */
    public double getVirusPPM() {
        return mVirusPPM;
    }

    public void setVirusPPM(double virusPPM) {
        mVirusPPM = virusPPM;
    }

    /**
     * Gets the concentration of contaminants in PPM
     * @return contaminant concentration (PPM)
     */
    public double getContaminantPPM() {
        return mContaminantPPM;
    }

    public void setContaminantPPM(double contaminantPPM) {
        mContaminantPPM = contaminantPPM;
    }

    /**
     * Converts the Report instance to a JSON object.
     * @return JSON object
     */
    public int getYear() {
        return mDateTime.getYear();
    }

    public int getMonth() {
        return mDateTime.getMonthOfYear();
    }

    public int getDay() {
        return mDateTime.getDayOfMonth();
    }

    public JSONObject toJson() {
        JSONObject json = null;
        try {
            json = new JSONObject()
                    .put("overall_condition", mCondition.name())
                    .put("virus_ppm", mVirusPPM)
                    .put("contaminant_ppm", mContaminantPPM)
                    .put("date_modified", mDateTime.toString()) //TODO check if + 'Z' is needed
                    .put("user_modified", mName);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
        }
        return json;
    }

    public void setID(int id) {
        mID = id;
    }
}
