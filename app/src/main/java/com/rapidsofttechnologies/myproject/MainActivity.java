package com.rapidsofttechnologies.myproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class MainActivity extends Activity {
    private RecyclerView quesLevelRecyclerView;
    private RecyclerView.Adapter quesLevelRecyclerViewAdapter;
    private RecyclerView.LayoutManager quesLevelRecyclerViewLayoutManager;

    private String quesLevel, quesUrl, jsonString;
    JSONObject jsonObject; 
    JSONArray questionsArrayOfJSON;
    int currentQuestionNo, duration;
    int totalNoOfQuestions =0, correctNoOfQuestions=0,wrongNoOfQuestions=0;
    Button logOutButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logOutButton = (Button)findViewById(R.id.log_out_button);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you Sure? You want to log out.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getApplicationContext()
                                        .getSharedPreferences("loginDetails", MODE_PRIVATE)
                                        .edit().putBoolean("isLoggedIn",false).commit();
                                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .setNegativeButton("No",null);
                        builder.create().show();

            }
        });
        quesLevelRecyclerView = (RecyclerView)findViewById(R.id.ques_level_recycler_view);
        quesLevelRecyclerViewLayoutManager = new LinearLayoutManager(this);
        quesLevelRecyclerViewAdapter = new QLRecyclerViewAdapter(setQLRecyclerViewData());

        quesLevelRecyclerView.setHasFixedSize(true);
        quesLevelRecyclerView.setLayoutManager(quesLevelRecyclerViewLayoutManager);
        quesLevelRecyclerView.setAdapter(quesLevelRecyclerViewAdapter);
    }

    private ArrayList<DataOfRecyclerView> setQLRecyclerViewData() {
        String [] quesLevelArray = getResources().getStringArray(R.array.ques_level_array);
        int[] quesLevelColorArray = getResources().getIntArray(R.array.ques_level_color_Array);
        ArrayList<DataOfRecyclerView> recyclerViewData = new ArrayList<>();
        for (int i=0;i<quesLevelArray.length;i++){
            DataOfRecyclerView dataRecyclerView = new DataOfRecyclerView();
            dataRecyclerView.setLevel(quesLevelArray[i]);
            dataRecyclerView.setColor(quesLevelColorArray[i]);
            recyclerViewData.add(dataRecyclerView);
        }
        return recyclerViewData;
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((QLRecyclerViewAdapter)quesLevelRecyclerViewAdapter).setOnItemClickListener(new QLRecyclerViewAdapter.QLRecyclerViewClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                quesLevel=((TextView) v.findViewById(R.id.recycler_text_view)).getText().toString();
                getUrl();
                if(checkInternetConnection()){
                    new FetchJSONString().execute(quesUrl);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please Connect the Internet !",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

    private boolean checkInternetConnection() {
        ConnectivityManager cm =
                (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void getUrl() {
        switch (quesLevel){
            case "Easy" :
                quesUrl="http://dev2.rapidsoft.in/ota/ac/questions/queseasy.json";
                duration=20;
                break;

            case "Medium" :
                quesUrl="http://dev2.rapidsoft.in/ota/ac/questions/quesmedium.json";
                duration=30;
                break;

            case "Difficult" :
                quesUrl = "http://dev2.rapidsoft.in/ota/ac/questions/queshard.json";
                duration=40;
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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




    private class FetchJSONString extends AsyncTask<String, String, String >{
        @Override
        protected String doInBackground(String... url) {
            String s = null;
            try {
                s = downloadString(url[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return s;
        }

        private String downloadString(String url) throws IOException {
            InputStream inputStream=null;
            BufferedReader bufferedReader = null;
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            URL urlObject = new URL(url);
            HttpURLConnection connection = (HttpURLConnection)urlObject.openConnection();
            //connection.setRequestMethod("GET");
            connection.connect();
            inputStream=connection.getInputStream();
            bufferedReader= new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine())!=null){
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            jsonString=s;
            try {
                findArrayOfJSONObjects();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (NullPointerException e){
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),"Please Connect the Internet !",Toast.LENGTH_LONG).show();
            }
        }

        private void findArrayOfJSONObjects() throws JSONException, NullPointerException {
            jsonObject = new JSONObject(jsonString);
            questionsArrayOfJSON=jsonObject.optJSONArray("questions");
            totalNoOfQuestions=questionsArrayOfJSON.length();
            currentQuestionNo=0;
            logOutButton.setVisibility(View.INVISIBLE);
            showQuesFragment();
        }
    }


    protected void showQuesFragment() throws JSONException {
        JSONObject jqObject=questionsArrayOfJSON.getJSONObject(currentQuestionNo);
        currentQuestionNo++;
        
        String ques = jqObject.optString("ques"); String option1 = jqObject.optString("Option1");
        String option2 = jqObject.optString("Option2");String option3 = jqObject.optString("Option3");
        String option4 = jqObject.optString("Option4");
        String option5 = jqObject.optString("Option5");
        String correct_answer_index = jqObject.optString("correct_answer_index");

        Bundle bundle = new Bundle() ;
        bundle.putInt("DURATION", duration); bundle.putString("QUESTION", ques);
        bundle.putInt("QUESTION_NO", currentQuestionNo); bundle.putString("OPTION1", option1);
        bundle.putString("OPTION2",option2); bundle.putString("OPTION3",option3);
        bundle.putString("OPTION4", option4); bundle.putString("OPTION5", option5);
        bundle.putString("CORRECT_ANSWER_INDEX", correct_answer_index);

        QuesFragment quesFragment = new QuesFragment();
        quesFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, quesFragment);
        fragmentTransaction.commit();
    }

}
