package com.nana.intent_service_example;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;
    private Button button;

    public static final String TAG = "MainActivity";

    public static MainActivity INSTANCE;

    public static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Bundle reply = msg.getData();
            new AlertDialog.Builder(INSTANCE)
                    .setTitle("Time is up")
                    .setMessage("You waited for " + reply.getInt("duration") + " seconds")
                    .setCancelable(false)
                    .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        INSTANCE = this;

        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int duration = Integer.parseInt(editText.getText().toString().trim());
                Intent intent = new Intent(MainActivity.this, ReminderService.class);
                intent.putExtra("duration", duration);
                intent.putExtra("messenger", new Messenger(handler));
                startService(intent);
            }
        });
    }
}
