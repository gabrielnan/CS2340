package com.goat.thirsty_goat;

import android.os.AsyncTask;
import android.util.Log;

import com.goat.thirsty_goat.models.Location;
import com.goat.thirsty_goat.models.ModelFacade;
import com.goat.thirsty_goat.models.Report;
import com.goat.thirsty_goat.models.WaterCondition;
import com.goat.thirsty_goat.models.WaterType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by GabrielNAN on 3/7/17.
 */

public class FetchReportsTask extends AsyncTask<Void, Void, List<Report>> {

    private static final String TAG = FetchReportsTask.class.getSimpleName();
    private ModelFacade mFacade;


    private List<Report> getReportsFromJson(String reportJsonString) throws JSONException {

        /**
         * "source_report_id" : 2,
         "location" : Michigan,
         "water_type" : Bottled,
         "water_condition" : Potable,
         "date_modified" : 2017-03-04T00:00:00.000Z,
         "user_modified" : Username
         */
        // JSON object names
        final String ID = "source_report_id";
        final String LOCATION = "location";
        final String LAT = "latitude";
        final String LON = "longitude";
        final String WATER_TYPE = "water_type";
        final String WATER_COND = "water_condition";
        final String DATE = "date_modified";
        final String USER = "user_modified";

        JSONArray reportJsonArray = new JSONArray(reportJsonString);

        // Random testing
        WaterType[] waterTypeValues = WaterType.values();
        WaterCondition[] waterCondValues = WaterCondition.values();
        Random rand = new Random();

        List<Report> reports = new ArrayList<>();
        for (int i = 0; i < reportJsonArray.length(); i++) {

            JSONObject reportJson = reportJsonArray.getJSONObject(i);
//            WaterType waterType = WaterType.valueOf(reportJson.getString(WATER_TYPE));
//            WaterCondition waterCondition = WaterCondition.valueOf(reportJson.getString(WATER_COND));
            WaterType waterType = waterTypeValues[rand.nextInt(waterTypeValues.length)];
            WaterCondition waterCondition = waterCondValues[rand.nextInt(waterCondValues.length)];
//            double lat = reportJson.getDouble(LAT);
//            double lon = reportJson.getDouble(LON);
            double lat = (rand.nextDouble() - 0.5) * 8 + 33;
            double lon = (rand.nextDouble() - 0.5) * 8 - 85;
            Location location = new Location(lat, lon);
            String name = reportJson.getString(USER);
            // TODO: Calendar(int year, int month, int day) add calendar as param to report
            reports.add(new Report(waterType, waterCondition, location, name));
        }
        Log.d(TAG, "Created reports");
        return reports;
    }

    @Override
    protected void onPreExecute() {
        mFacade = ModelFacade.getInstance();
    }


    @Override
    protected List<Report> doInBackground(Void... voids) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reportJsonString;
        final String BASE_URL = "http://thirstygoat.myoberon.com/api/source_reports";
        final String ID_PARAM = "id";

        try {

//            Uri buildUri = Uri.parse(BASE_URL).buildUpon()
//                    .appendQueryParameter(ID_PARAM, 3);
            URL url = new URL(BASE_URL);


            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            Log.d(TAG, "Got input stream");
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null) {
                Log.d(TAG, "InputStream object null");
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // For debugging purposes
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }

            if (buffer.length() == 0) {
                Log.d(TAG, "Empty String returned");
                return null;
            }
            reportJsonString = buffer.toString();

        } catch(IOException e) {
            Log.e(TAG, "Error", e);
            return null;

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }

        // Return reports
        try {
            return getReportsFromJson(reportJsonString);
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Report> result) {
        if (result != null) {
            mFacade.addAllReports(result);
        }
    }
}