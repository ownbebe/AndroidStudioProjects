package com.example.utilityapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class NotesActivity extends AppCompatActivity {

    private EditText mNoteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        mNoteEditText = findViewById(R.id.notes_edit_text);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    public void onSaveClick(View view) {
        saveNote();
    }

    public void onBackClick(View view) {
        finish();
    }

    private void saveNote() {
        String noteText = mNoteEditText.getText().toString().trim();

        if (!noteText.isEmpty()) {
            String fileName = "note.txt";
            String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getAbsolutePath() + "/" + fileName;

            try {
                FileOutputStream fos = new FileOutputStream(filePath);
                fos.write(noteText.getBytes());
                fos.close();
                Toast.makeText(this, "Saved to: " + filePath, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Please enter a note to save", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "External storage write permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "External storage write permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}


