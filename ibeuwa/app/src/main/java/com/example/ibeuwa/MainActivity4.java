package com.example.ibeuwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        Button moveButton5=(Button) findViewById(R.id.button6);
        moveButton5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity5.class); //폐기물 처리 목록으로 이동
                startActivity(intent);
            }
        });

        Button moveButton6=(Button) findViewById(R.id.button7);
        moveButton5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity5.class);  //중고거래 목록으로 이동 MainActivity5수정필요
                startActivity(intent);
            }
        });

    }
}