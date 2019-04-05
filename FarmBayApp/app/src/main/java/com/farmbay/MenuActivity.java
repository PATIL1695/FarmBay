package com.farmbay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {
    private Button recommendCrop;
    private Button bidCrop;
    private Button viewMarket;
    private Button about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        recommendCrop = (Button)findViewById(R.id.recommend);
        recommendCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,RecommendCropsActivity.class);
                startActivity(intent);
            }
        });

        bidCrop = (Button)findViewById(R.id.bidcrop);
        bidCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,CreateBidActivity.class);
                startActivity(intent);
            }
        });
        viewMarket = (Button)findViewById(R.id.viewmarket);
        viewMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,MarketPaceActivity.class);
                startActivity(intent);
            }
        });
        about = (Button)findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuActivity.this,AboutActivity.class);
                startActivity(intent);
            }
        });
    }
}
