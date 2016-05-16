package com.branch.test.refferraltest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

public class MainActivity extends AppCompatActivity {

    Branch branch = Branch.getInstance();
    String key;
    Button btnShareEarn;
    TextView txtReferral;
    TextView txtCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_earn);
        btnShareEarn = (Button)findViewById(R.id.btnShareEarn);
        txtReferral = (TextView)findViewById(R.id.txtReferral);
        txtCode = (TextView)findViewById(R.id.txtCode);


        btnShareEarn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, "https://bnc.lt/referralcode");
                startActivity(Intent.createChooser(shareIntent, "Share link using"));
            }
        });

        branch.initSession(new Branch.BranchReferralInitListener(){
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {

                    Log.i("ReferringParams" ,referringParams.toString());
                    try {
                        key = referringParams.getString("key");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("MyApp", error.getMessage());
                    Toast.makeText(MainActivity.this,"got params error : "+error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }
}
