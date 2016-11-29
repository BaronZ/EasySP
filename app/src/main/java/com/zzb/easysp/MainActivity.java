package com.zzb.easysp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.zzb.easysp.generated.EasySpTest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tv = (TextView) findViewById(R.id.tv);
        EasySpTest test = EasySpTest.create(this);
        //test.setImString("im string");
        tv.setText(test.getImString());

    }
}
