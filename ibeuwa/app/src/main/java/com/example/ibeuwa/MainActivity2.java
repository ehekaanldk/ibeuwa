package com.example.ibeuwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button moveButton2=(Button) findViewById(R.id.button3);
        moveButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity3.class);  //예약자 정보 확인 완료> 폐기물 등록완료 화면으로 이동
                startActivity(intent);
            }
        });
    }
}