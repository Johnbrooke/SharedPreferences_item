package com.example.administrator.mydemo_onepic_2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends Activity {


    SharedPreferences sharedPreferences;
    @InjectView(R.id.tv_item)
    TextView tvItem;
    @InjectView(R.id.tv_item2)
    TextView tvItem2;
    @InjectView(R.id.bt_item)
    Button btItem;
    @InjectView(R.id.bt_item2)
    Button btItem2;
    @InjectView(R.id.tv_item3)
    TextView tvItem3;
    @InjectView(R.id.tv_item4)
    TextView tvItem4;
    @InjectView(R.id.iv_item)
    ImageView ivItem;
    @InjectView(R.id.iv_item2)
    ImageView ivItem2;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        sharedPreferences = getSharedPreferences("text", Context.MODE_PRIVATE);


        btItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
                //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
                //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
                byte[] bytes = byteArrayOutputStream.toByteArray();
                String imagString = new String(Base64.encodeToString(bytes, Base64.DEFAULT));
                //第三步:将String保持至SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("aa", tvItem.getText().toString());
                editor.putString("bb", tvItem2.getText().toString());
                editor.putString("cc", imagString);
                editor.commit();
                Toast.makeText(MainActivity.this, "写入完成", Toast.LENGTH_SHORT).show();

            }
        });

        btItem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String texta = sharedPreferences.getString("aa", "");
                String textb = sharedPreferences.getString("bb", "");

                //第一步:取出字符串形式的Bitmap
                String imageString = sharedPreferences.getString("cc", "");

                //第二步:利用Base64将字符串转换为ByteArrayInputStream
                byte[] bytes = Base64.decode(imageString, Base64.DEFAULT);
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

                //第三步:利用ByteArrayInputStream生成Bitmap
                Bitmap bitmap = BitmapFactory.decodeStream(byteArrayInputStream);

                tvItem4.setText(textb);
                tvItem3.setText(texta);
                ivItem2.setImageBitmap(bitmap);


            }
        });
    }


}
