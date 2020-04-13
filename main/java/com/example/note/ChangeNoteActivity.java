package com.example.note;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ChangeNoteActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;

    private EditText title, content;
    private Button save;

    Intent intent;
    Bundle bundle = new Bundle();

    Database db = new Database(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);

        toolbar = findViewById(R.id.add_note_toolbar);
        setSupportActionBar(toolbar);

        intent = getIntent();
        bundle = intent.getExtras();

        title = findViewById(R.id.title_change);
        title.setText(bundle.getString("Title"));

        content = findViewById(R.id.content_change);
        content.setText(bundle.getString("Content"));

        save = findViewById(R.id.save_change);
        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = bundle.getInt("ID");
        String t = title.getText().toString();
        String c = content.getText().toString();

        if(!t.equals("") || !c.equals("")) {
            db.updateData(id, new Note(t, c));
            intent = new Intent(ChangeNoteActivity.this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(ChangeNoteActivity.this, "Cần có tiêu đề hoặc nội dung", Toast.LENGTH_SHORT).show();
        }
    }
}