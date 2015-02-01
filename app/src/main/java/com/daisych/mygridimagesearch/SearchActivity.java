package com.daisych.mygridimagesearch;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.apache.http.Header;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    ArrayList<ImageResult> imageResults;
    ImageResultsAdapter adapter;
    EditText etQuery;
    GridView gvResults;
    String imageSize = "";
    String imageColor = "";
    String imageType = "";
    String imageSite = "";
    final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        imageResults = new ArrayList<ImageResult>();
        adapter = new ImageResultsAdapter(this, imageResults);
        gvResults.setAdapter(adapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                ImageResult result = imageResults.get(position);
                i.putExtra("result", result);
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

        if(id == R.id.miSettings) {
            Intent i = new Intent(this, SettingsActivity.class);
            i.putExtra("imageSize", imageSize);
            i.putExtra("imageColor", imageColor);
            i.putExtra("imageType", imageType);
            i.putExtra("imageSite", imageSite);

            startActivityForResult(i, REQUEST_CODE);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onImageSearch(View v) {
        AsyncHttpClient client = new AsyncHttpClient();
        String query = etQuery.getText().toString();
        String searchUrl = generateQueryString(query);
        Log.e("DEBUG", searchUrl);

        client.get(searchUrl, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray JSONImageResults;

                try {
                    JSONImageResults = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear();
                    // when you make change in adapter, it does change underlying data structure and notify
                    adapter.addAll(ImageResult.fromJSONArray(JSONImageResults));
                }   catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public String generateQueryString(String query) {
        String url = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";

        if(imageSize != "select") {
            url += "&imgsz=" + imageSize;
        }
        if(imageColor != "select") {
            url += "&imgcolor=" + imageColor;
        }
        if(imageType != "select") {
            url += "&imgtype=" + imageType;
        }
        if(imageSite != "") {
            url += "&as_sitesearch=" + imageSite;
        }

        return url;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            Toast.makeText(this, "back", Toast.LENGTH_SHORT).show();
            imageSize = data.getStringExtra("imageSize");
            imageColor = data.getStringExtra("imageColor");
            imageType = data.getStringExtra("imageType");
            imageSite = data.getStringExtra("imageSite");

            Toast.makeText(this, imageSize, Toast.LENGTH_SHORT).show();
        }
    }

}
