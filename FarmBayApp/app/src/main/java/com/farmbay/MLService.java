package com.farmbay;

import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
//import java.util.Base64;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by manas on 4/28/2018.
 */

public class MLService {


    private static RecommendCropsActivity callback;
    public static void setCallBack(RecommendCropsActivity rc){
        callback = rc;
    }



    public static void invoke(FarmDetail farmDetailObject) throws IOException {

        Map<String, String> wml_credentials = new HashMap<String, String>()
        {{
            put("url","https://ibm-watson-ml.mybluemix.net");
            put("username", "01577b1d-7d8c-48c7-a4a6-b95ebbb12247");
            put("password", "87b97e34-6295-49ae-a9ff-28868d0f9f0f");
        }};

        String wml_auth_header = "Basic " +
                Base64.encodeToString((wml_credentials.get("username") + ":" +
                        wml_credentials.get("password")).getBytes(StandardCharsets.UTF_8),Base64.NO_WRAP);
       /* String wml_auth_header = "Basic " +
               Base64.getEncoder().encodeToString((wml_credentials.get("username") + ":" +
                        wml_credentials.get("password")).getBytes(StandardCharsets.UTF_8));*/

        String wml_url = wml_credentials.get("url") + "/v3/identity/token";

        HttpURLConnection tokenConnection = null;
        HttpURLConnection scoringConnection = null;
        BufferedReader tokenBuffer = null;
        BufferedReader scoringBuffer = null;
        try {
            // Getting WML token
            URL tokenUrl = new URL(wml_url);
            System.out.println("***********wml url :"+tokenUrl+"********************");

            tokenConnection = (HttpURLConnection) tokenUrl.openConnection();
            //tokenConnection.setDoOutput(true);
            tokenConnection.setDoInput(true);
            tokenConnection.setRequestMethod("GET");


            tokenConnection.setRequestProperty("Authorization", wml_auth_header);


            System.out.println("********Connection Response:*********"+tokenConnection.getResponseCode());

            InputStream iStream = tokenConnection.getInputStream();
            tokenBuffer = new BufferedReader(new InputStreamReader(iStream));
            System.out.println("***********Connection is open********************");
            StringBuffer jsonString = new StringBuffer();
            String line;
            while ((line = tokenBuffer.readLine()) != null) {
                System.out.println("********"+ line+"*********");
                jsonString.append(line);
            }
            // Scoring request
            URL scoringUrl = new URL("https://ibm-watson-ml.mybluemix.net/v3/wml_instances/fac8b5f4-d56f-465c-86c6-e89f2e1bc365/published_models/50ed01d4-65c1-4921-96d8-01b21ca0c450/deployments/2ddcc796-7407-4bde-9570-66831b34bd0a/online");
            String wml_token = "Bearer " +
                    jsonString.toString()
                            .replace("\"","")
                            .replace("}", "")
                            .split(":")[1];
            scoringConnection = (HttpURLConnection) scoringUrl.openConnection();
            scoringConnection.setDoInput(true);
            scoringConnection.setDoOutput(true);
            scoringConnection.setRequestMethod("POST");
            scoringConnection.setRequestProperty("Accept", "application/json");
            scoringConnection.setRequestProperty("Authorization", wml_token);
            scoringConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(scoringConnection.getOutputStream(), "UTF-8");

            // NOTE: manually define and pass the array(s) of values to be scored in the next line
            String payload = "{\"fields\": [\"ZIP_CODE\", \"STATE_NAME\", \"MIN_HARVEST_AREA\", \"MAX_HARVEST_AREA\"], \"values\": [["+farmDetailObject.getZipcode()+",\""+farmDetailObject.getState()+"\","+farmDetailObject.getArea()+","+farmDetailObject.getMaxArea()+"]]}";
            System.out.println(payload);
            writer.write(payload);
            writer.close();

            scoringBuffer = new BufferedReader(new InputStreamReader(scoringConnection.getInputStream()));
            StringBuffer jsonStringScoring = new StringBuffer();
            String lineScoring;
            while ((lineScoring = scoringBuffer.readLine()) != null) {
                jsonStringScoring.append(lineScoring);
            }
            ArrayList<String> cropList =  getCrops(jsonStringScoring.toString());
            ArrayList<Double> predictionArray = getPredictionValues(jsonStringScoring.toString());
            ArrayList<String> sortedCrop =createMapAndSort(cropList,predictionArray);
            callback.navigateToResultActivity(sortedCrop);
            System.out.println(jsonStringScoring);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("The URL is not valid.");
            System.out.println(e.getMessage());
        }
        finally {
            if (tokenConnection != null) {
                tokenConnection.disconnect();
            }
            if (tokenBuffer != null) {
                tokenBuffer.close();
            }
            if (scoringConnection != null) {
                scoringConnection.disconnect();
            }
            if (scoringBuffer != null) {
                scoringBuffer.close();
            }
        }
    }
    public static ArrayList getCrops(String jsonString){
        ArrayList<String> arrCrop = new ArrayList<String>();
        try {
            JSONObject cropObject = new JSONObject(jsonString);
            JSONArray arr = cropObject.getJSONArray("values");
            JSONArray arr2 = arr.getJSONArray(0);
            JSONArray arr3 = arr2.getJSONArray(9);
            //JSONArray arr4 =  arr3.getJSONArray(0);
            for (int i = 0; i < arr3.length(); i++)
            {
                arrCrop.add(arr3.getString(i));
                System.out.println(arr3.toString());
            }
            System.out.println(arr3.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return arrCrop;
    }
    public static ArrayList getPredictionValues(String jsonString){
        ArrayList<Double> arrCrop = new ArrayList<Double>();
        try {
            JSONObject cropObject = new JSONObject(jsonString);
            JSONArray arr = cropObject.getJSONArray("values");
            JSONArray arr2 = arr.getJSONArray(0);
            JSONArray arr3 = arr2.getJSONArray(6);
            //JSONArray arr4 =  arr3.getJSONArray(0);
            for (int i = 0; i < arr3.length(); i++)
            {
                arrCrop.add(arr3.getDouble(i));
                System.out.println(arrCrop.get(i));

            }
            System.out.println(arr3.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
        return arrCrop;
    }

    public static ArrayList<String> createMapAndSort( ArrayList<String> cropList, ArrayList<Double> predictionArray){
        HashMap<String, Integer> map = new HashMap<>();
        ArrayList<String> myArray = new ArrayList<String>();
        for(int i=0;i<cropList.size();i++){
            Double d = (predictionArray.get(i)*1000);
            Integer myint = d.intValue();
            map.put(cropList.get(i),myint);
        }
        Object[] a = map.entrySet().toArray();
        Arrays.sort(a, new Comparator() {
            public int compare(Object o1, Object o2) {
                return ((Map.Entry<String, Integer>) o2).getValue()
                        .compareTo(((Map.Entry<String, Integer>) o1).getValue());
            }
        });
        for (Object e : a) {
            myArray.add(((Map.Entry<String, Integer>) e).getKey());
            System.out.println(((Map.Entry<String, Integer>) e).getKey() + " : "
                    + ((Map.Entry<String, Integer>) e).getValue());
        }
        return myArray;
    }

}
