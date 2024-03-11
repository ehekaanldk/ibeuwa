package com.example.ibeuwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);

        Button moveButton1=(Button)findViewById(R.id.button2);
        moveButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);  //정보 입력 및 납부금액 결정 > 예약자 확인 화면으로 이동
                startActivity(intent);
            }
        });
    }
}