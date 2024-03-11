package com.example.ibeuwa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {



    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 디자인 정의
        btnCapture = (Button) findViewById(R.id.btnPhoto);
        imgCapture = (ImageView) findViewById(R.id.imageView);
        btnCapture.setOnClickListener(this);
        findViewById(R.id.btnPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            // 사진 촬영 버튼 클릭 이벤트
            public void onClick(View view) {
                // 카메라 기능을 Intent
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, Image_Capture_COde);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)    {
        super.onActivityResult(requestCode, resultCode, data);


        // 카메라를 다루지 않기 때문에 앨범 상수에 대해서 성공한 경우에 대해서만 처리
        if (requestCode == Image_Capture_COde) {
            if (resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("data");
                bp = RotateBitmap(bp, 90);
                int cx = 128, cy = 128;
                bp = Bitmap.createScaledBitmap(bp, cx, cy, false);
                int[] pixels = new int[cx * cy];
                bp.getPixels(pixels, 0, cx, 0, 0, cx, cy);

                ByteBuffer input_img = getInputImage_2(pixels, cx, cy);
                Interpreter tf_lite = getTfliteInterpreter("cats_and_dogs.tflite");

                float[][] pred = new float[1][2];
                tf_lite.run(input_img, pred);

                final String predText = String.format("%f", pred[0][0]) + String.format("%f", pred[0][1]);
                if(pred[0][0] &gt; pred[0][1]){
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("고양이 확률 : %f", pred[0][0]), Toast.LENGTH_LONG); toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(), String.format("강아지 확률 : %f", pred[0][1]), Toast.LENGTH_LONG); toast.show();
                }



                Log.d("prediction", predText);


                imgCapture.setImageBitmap(bp);



            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            }
        }
        try {
/*
            // 카메라 촬영을 하면 이미지뷰에 사진 삽입
            if(requestCode == 0 && resultCode == RESULT_OK) {
                // Bundle로 데이터를 입력
                Bundle extras = data.getExtras();

                // Bitmap으로 컨버전
                Bitmap imageBitmap = (Bitmap) extras.get("data");

                // 이미지뷰에 Bitmap으로 이미지를 입력
                imageView.setImageBitmap(imageBitmap);
            }
*/
            int batchNum = 0;
            InputStream buf = getContentResolver().openInputStream(data.getData());
            Bitmap bitmap = BitmapFactory.decodeStream(buf);
            buf.close();

            //이미지 뷰에 선택한 사진 띄우기
            ImageView iv = findViewById(R.id.imageView);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageBitmap(bitmap);

            // x,y 최댓값 사진 크기에 따라 달라짐 (조절 해줘야함)
            for (int x = 0; x < 224; x++) {
                for (int y = 0; y < 224; y++) {
                    int pixel = bitmap.getPixel(x, y);
                    input[batchNum][x][y][0] = Color.red(pixel) / 1.0f;
                    input[batchNum][x][y][1] = Color.green(pixel) / 1.0f;
                    input[batchNum][x][y][2] = Color.blue(pixel) / 1.0f;
                }
            }

            // 자신의 tflite 이름 써주기
            Interpreter lite = getTfliteInterpreter("converted_model_mobileNetCheck.tflite");
            lite.run(input, output);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView test_output = findViewById(R.id.textView20);
        int i;
        // 텍스트뷰에 무슨 버섯인지 띄우기 but error남 ㅜㅜ 붉은 사슴뿔만 주구장창
        for (i = 0; i < 5; i++) {
            if (output[0][i] * 100 > 90) {
                if (i == 0) {
                    test_output.setText(String.format("개나리 광대버섯  %d %.5f", i, output[0][0] * 100));
                } else if (i == 1) {
                    test_output.setText(String.format("붉은사슴뿔버섯,%d  %.5f", i, output[0][1] * 100));
                } else if (i == 2) {
                    test_output.setText(String.format("새송이버섯,%d, %.5f", i, output[0][2] * 100));
                } else if (i == 3) {
                    test_output.setText(String.format("표고버섯, %d, %.5f", i, output[0][3] * 100));
                } else {
                    test_output.setText(String.format("화경버섯, %d, %.5f", i, output[0][4] * 100));
                }
            } else
                continue;
        }
    }

    // 딥러닝 추가 부분
    // 내부에 tflite관련 코드(작성 필수!)
    private Interpreter getTfliteInterpreter(String modelPath) {
        try {
            return new Interpreter(loadModelFile(MainActivity.this, modelPath));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public MappedByteBuffer loadModelFile(Activity activity, String modelPath) throws IOException {
        AssetFileDescriptor fileDescriptor = activity.getAssets().openFd(modelPath);
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }
}

//border_layout.xml파일 drawable에 생성
//<?xml version="1.0" encoding="utf-8"?>
//<layer-list xmlns:android="http://schemas.android.com/apk/res/android">
//    <item
//        android:bottom="2dp"
//        android:end="2dp"
//        android:start="2dp"
//        android:top="2dp">
//        <shape android:shape="rectangle">
//            <stroke
//                android:width="2dp"
//                android:color="#000000" />
//            <solid android:color="null" />
//        </shape>
//    </item>
//</layer-list>

//