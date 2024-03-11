package com.example.ibeuwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button moveButton3=(Button) findViewById(R.id.button4);
        moveButton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);  //폐기물 등록 추가 화면> 초기화면으로 이동
                startActivity(intent);
            }
        });

        Button moveButton4=(Button) findViewById(R.id.button5);
        moveButton4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity4.class);  //폐기물 등록 완료> 목록화면으로 이동
                startActivity(intent);
            }
        });
    }
}