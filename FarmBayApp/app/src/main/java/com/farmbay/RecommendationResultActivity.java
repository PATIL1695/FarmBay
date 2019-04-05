package com.farmbay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class RecommendationResultActivity extends AppCompatActivity {

    public static Map<String,Double> yieldMap = new HashMap<String, Double>();
    Double yield1, yield2,yield3,yield4,yield5;

    public void populateYieldMap(){
        yieldMap.put("HAY & HAYLAGE",5.7);
        yieldMap.put("SORGHUM",3.3);
        yieldMap.put("CORN",4.2);
        yieldMap.put("WHEAT",1.8);
        yieldMap.put("SOYABEANS",1.1);
        yieldMap.put("BARLEY",1.2);
        yieldMap.put("COTTON",0.7);
        yieldMap.put("TOBACCO",1.2);
        yieldMap.put("PEANUTS",1.7);
        yieldMap.put("RICE",3.9);
        yieldMap.put("BEANS",1.2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation_result);
        if(yieldMap.size()<11)
            populateYieldMap();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            String area = intent.getStringExtra("area");
            Double dArea = Double.parseDouble(area);
            if(dArea < 0)
                dArea = 1.0;
            String crop1 = intent.getStringExtra("crop1");
            System.out.println("****"+crop1);
            String crop2 = intent.getStringExtra("crop2");
            String crop3 = intent.getStringExtra("crop3");
            String crop4 = intent.getStringExtra("crop4");
            String crop5 = intent.getStringExtra("crop5");
            if(yieldMap.containsKey(crop1)){
                yield1 = yieldMap.get(crop1)*dArea;
                System.out.println("*************"+yield1);
            }
            else
            {
               yield1 = 2*dArea;
            }
            if(yieldMap.containsKey(crop2)){
                yield2 = yieldMap.get(crop2)*dArea;
            }
            else
            {
                yield2 = 2*dArea;
            }
            if(yieldMap.containsKey(crop3)){
                yield3 = yieldMap.get(crop3)*dArea;
            }
            else
            {
                yield3 = 2*dArea;
            }
            if(yieldMap.containsKey(crop4)){
                yield4 = yieldMap.get(crop4)*dArea;
            }
            else
            {
                yield4 = 2*dArea;
            }
            if(yieldMap.containsKey(crop5)){
                yield5 = yieldMap.get(crop5)*dArea;
            }
            else
            {
                yield5 = 2*dArea;
            }
            TextView cropText1 =findViewById(R.id.crop1);
            cropText1.setText(crop1);
            TextView yieldText1 =findViewById(R.id.yield1);
            yieldText1.setText(yield1.toString());

            TextView cropText2 =findViewById(R.id.crop2);
            cropText2.setText(crop2);
            TextView yieldText2 =findViewById(R.id.yield2);
            yieldText2.setText(yield2.toString());

            TextView cropText3 =findViewById(R.id.crop3);
            cropText3.setText(crop3);
            TextView yieldText3 =findViewById(R.id.yield3);
            yieldText3.setText(yield3.toString());

            TextView cropText4 =findViewById(R.id.crop4);
            cropText4.setText(crop4);
            TextView yieldText4 =findViewById(R.id.yield4);
            yieldText4.setText(yield4.toString());

            TextView cropText5 =findViewById(R.id.crop5);
            cropText5.setText(crop5);
            TextView yieldText5 =findViewById(R.id.yield5);
            yieldText5.setText(yield5.toString());
        }
    }
}
