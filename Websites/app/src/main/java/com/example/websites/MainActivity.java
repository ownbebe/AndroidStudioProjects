package com.example.websites;

import androidx.appcompat.app.AppCompatActivity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity {

    private Button mAddWidgetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAddWidgetButton = findViewById(R.id.add_widget_button);
        mAddWidgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the AppWidgetManager instance
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(MainActivity.this);

                // Get the ComponentName for the MyWidget class
                ComponentName myWidget = new ComponentName(MainActivity.this, MyWidget.class);

                // Check if the widget is already added to the home screen
                int[] appWidgetIds = appWidgetManager.getAppWidgetIds(myWidget);
                if (appWidgetIds.length == 0) {
                    // If the widget isn't added, add it to the home screen
                    Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
                    startActivityForResult(intent, 1);
                }
            }
        });
    }
}

