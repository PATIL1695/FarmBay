package com.farmbay;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class RecommendCropsActivity extends AppCompatActivity {

    private Button recommendButton;
    private FarmDetail farmDetail;
    private EditText zipcode;
    private EditText state;
    private EditText area;
    private int zipCodeLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_crops);
        recommendButton = findViewById(R.id.recommendcrops);
        recommendButton.setOnClickListener(recommendationHandler);
        farmDetail =  new FarmDetail();
        zipcode = findViewById(R.id.zipcode);
        state = findViewById(R.id.state);
        area = findViewById(R.id.area);
    }
    View.OnClickListener recommendationHandler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if( zipcode.getText().toString().length()>3 && zipcode.getText().toString().length()<6 && area.getText().toString().length() != 0) {
                farmDetail.setZipcode(Integer.parseInt(zipcode.getText().toString()));
                farmDetail.setState(state.getText().toString());
                farmDetail.setArea(Integer.parseInt(area.getText().toString()));
                int maxArea = Integer.parseInt(area.getText().toString())+ 10;
                farmDetail.setMaxArea(maxArea);

                MLService.setCallBack(RecommendCropsActivity.this);
                AsyncTaskRunner runner = new AsyncTaskRunner();
                runner.setFarmDetailObject(farmDetail);
                runner.execute();
            }
            else
            {
                Toast.makeText(getApplication(), "Invalid Input Values", Toast.LENGTH_LONG).show();
            }
        }
    };
    public void navigateToResultActivity(ArrayList<String> resultCrop){

        System.out.println("***************Recommendation result*******************");
        Intent intent = new Intent(RecommendCropsActivity.this, RecommendationResultActivity.class);

        for (int i = 0; i < resultCrop.size(); i++)
        {
            System.out.println(resultCrop.get(i));
            String key = "crop"+(i+1);
            intent.putExtra(key,resultCrop.get(i));
        }
        intent.putExtra("area",area.getText().toString());
        startActivity(intent);
    }
    private class AsyncTaskRunner extends AsyncTask<String,String , String> {

        private FarmDetail farmDetailObject;


        public void setFarmDetailObject(FarmDetail farmDetailObject) {
            this.farmDetailObject = farmDetailObject;
        }

        @Override
        protected String doInBackground(String... params) {

            try{
               MLService.invoke(farmDetailObject);
            }catch(Exception e) {
                e.printStackTrace();
                System.out.println("***********Exception*************");
            }

            return "Success";
        }

        @Override
        protected void onPostExecute(String result) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }
}
