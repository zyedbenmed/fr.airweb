package com.sample.frairwebnews;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.util.Log;
import android.widget.ListView;
import 	android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private ListView lv;
    private String TAG = MainActivity.class.getSimpleName();

    // URL to get JSON
    private static String url = "https://airweb-demo.airweb.fr/psg/psg.json";

    ArrayList<HashMap<String, String>> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        newsList = new ArrayList<>();

        lv = findViewById(R.id.list);

        new GetNews().execute();


    }


    private class GetNews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();


            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);


                    JSONArray news = jsonObj.getJSONArray("news");


                    for (int i = 0; i < news.length(); i++) {
                        JSONObject c = news.getJSONObject(i);

                        String nid = c.getString("nid");
                        String type = c.getString("type");
                        String date = c.getString("date");
                        String title = c.getString("title");
                        String picture = c.getString("picture");
                        String contents = c.getString("content");
                        String dateformated = c.getString("dateformated");



                        HashMap<String, String> hashMap = new HashMap<>();

                        if (type.equals("news")) {

                            hashMap.put("id", nid);
                            hashMap.put("title", title);
                            hashMap.put("desc", contents);
                            hashMap.put("pic", picture);

                            // adding contact to contact list
                            newsList.add(hashMap);
                        }
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, newsList,
                    R.layout.single_view, new String[]{"title", "desc"}, new int[]{R.id.title,
                    R.id.description});

            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    /*Intent intent =new Intent(getApplicationContext(), Main2Activity.class);
                    intent.putExtra("id",newsList.get((int)id));
                    startActivity(intent);
                    */
                    Toast.makeText(MainActivity.this, newsList.get((int)id).toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}