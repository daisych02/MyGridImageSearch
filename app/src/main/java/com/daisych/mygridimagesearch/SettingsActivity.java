package com.daisych.mygridimagesearch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


public class SettingsActivity extends ActionBarActivity {

    String imageSize = "";
    String imageColor = "";
    String imageType = "";
    String imageSite = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        EditText etSite = (EditText) findViewById(R.id.etSite);
        imageSize = getIntent().getStringExtra("imageSize");
        imageColor = getIntent().getStringExtra("imageColor");
        imageType = getIntent().getStringExtra("imageType");
        imageSite = getIntent().getStringExtra("imageSite");

        etSite.setText(imageSite);
        setSpinners();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSpinners() {
        final Spinner sizeSpinner = (Spinner) findViewById(R.id.spImageSize);
        final Spinner colorSpinner = (Spinner) findViewById(R.id.spImageColor);
        final Spinner typeSpinner = (Spinner) findViewById(R.id.spImageType);

        // Image size dropdown
        ArrayAdapter<CharSequence> imgSizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.imageSize_array, R.layout.spinner_align_right);
        imgSizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sizeSpinner.setAdapter(imgSizeAdapter);

        // Image color dropdown
        ArrayAdapter<CharSequence> imgColorAdapter = ArrayAdapter.createFromResource(this,
                R.array.imageColor_array, R.layout.spinner_align_right);
        imgColorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(imgColorAdapter);

        // Image type dropdown
        ArrayAdapter<CharSequence> imgTypeAdapter = ArrayAdapter.createFromResource(this,
                R.array.imageType_array, R.layout.spinner_align_right);
        imgTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(imgTypeAdapter);

        sizeSpinner.setSelection(getIndex(sizeSpinner, imageSize));
        colorSpinner.setSelection(getIndex(colorSpinner, imageColor));
        typeSpinner.setSelection(getIndex(typeSpinner, imageType));

        sizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageSize = sizeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageColor = colorSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                imageType = typeSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void onSave (View view) {
        Intent i = new Intent();
        i.putExtra("imageSize", imageSize);
        i.putExtra("imageColor", imageColor);
        i.putExtra("imageType", imageType);
        i.putExtra("imageSite", imageSite);
        setResult(RESULT_OK, i);
        finish();
    }

    public void onCancel (View view) {
        finish();
    }

    private int getIndex(Spinner spinner, String myString)
    {
        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                i=spinner.getCount();//will stop the loop, kind of break, by making condition false
            }
        }
        return index;
    }
}
