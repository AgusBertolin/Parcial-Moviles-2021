package com.example.parcialmoviles2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    EditText movie, description;
    ToggleButton seen;
    Button save, list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloTextView = (TextView) findViewById(R.id.text_view_id);
        helloTextView.setText(R.string.text_view_text);

        movie = (EditText) findViewById(R.id.movie_id);
        description = (EditText) findViewById(R.id.description_id);

        seen = (ToggleButton) findViewById(R.id.movie_seen_id);

        save = (Button) findViewById(R.id.save_id);

        list = (Button) findViewById(R.id.list_id);
    }
}