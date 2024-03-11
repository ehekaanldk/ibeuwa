package com.example.ibeuwa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity5 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Button moveButton7=(Button) findViewById(R.id.button8);
        moveButton7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity3.class);  //이동경로 지정X
                startActivity(intent);
            }
        });

        Button moveButton8=(Button)findViewById(R.id.button9);
        moveButton8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity2.class);  //이동경로 지정X
                startActivity(intent);
            }
        });

        Button moveButton9=(Button)findViewById(R.id.button10);
        moveButton9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);  //이동경로 지정X
                startActivity(intent);
            }
        });

        Button moveButton3=(Button) findViewById(R.id.button11);
        moveButton3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);  //폐기물 등록 추가 화면> 초기화면으로 이동
                startActivity(intent);
            }
        });

    }
}