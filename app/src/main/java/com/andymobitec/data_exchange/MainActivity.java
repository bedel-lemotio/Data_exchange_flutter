package com.andymobitec.data_exchange;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import io.flutter.Log;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL = "AndySample/test";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void lauchFlutter(View view) {
        FlutterEngine flutterEngine=new FlutterEngine(this);
        flutterEngine.getDartExecutor().executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        );
        FlutterEngineCache
                .getInstance()
                .put("my_engine_id", flutterEngine);
        flutterEngine.getNavigationChannel().setInitialRoute("/");

        MethodChannel mc=new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(),CHANNEL);
        mc.setMethodCallHandler((methodCall, result) ->
                {
                    if(methodCall.method.equals("test"))
                    {
                        result.success("Hai from android "+ methodCall.argument("data"));
                    }
                    else
                    {
                        Log.i("new method came",methodCall.method);
                    }

                }
        );

        startActivity(
                FlutterActivity
                        .withCachedEngine("my_engine_id")
                        .build(this)
        );
    }
}