package bvpieee.securitysystem;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static android.content.ContentValues.TAG;

/**
 * Created by JASPREET SINGH on 03-03-2018.
 */


public class MyService extends Service {
String emergency="";
    @Override
    public void onCreate() {
        // code to execute when the service is first created
        super.onCreate();
        Log.i("MyService", "Service Started.");
go();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public MyService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.






     //   if (emergency.equals("1")) {
      //      Intent i = new Intent(MyService.this, MainActivity.class);
       //     startActivity(i);
    //        onDestroy();
     //   }

        Log.e("myservice", "Ongoing");
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }


    public static class ApiClientearthquake {

        public static final String BASE_URL = "https://api.thingspeak.com/channels/402782/";

        private static Retrofit retrofit = null;


        public static Retrofit getClient() {
            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            return retrofit;
        }


    }


    public interface ApiInterfaceearthquake {

        @GET("feeds.json?api_key=SK4AD2L8YEO9P9LU&results=2")
        Call<ResponseBody> getall();

    }

void go(){
        ApiInterfaceearthquake apiServiceearth = ApiClientearthquake.getClient().create(ApiInterfaceearthquake.class);
//TODO
        Call calle = apiServiceearth.getall();
        calle.enqueue(new Callback() {
            @Override
            public void onResponse(Call calle, Response response) {
                Log.e("TAG", "success");
                Log.e(TAG, response.raw().request().url().toString());
                String url = response.raw().request().url().toString();
                Earthquakefinder mytask = new Earthquakefinder();
                mytask.execute(url);


            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.e(TAG, "failureee");
            }


        });


    }
    public class Earthquakefinder extends AsyncTask<String, Void, Integer> {


        public Earthquakefinder() {

            super();

        }


        // The onPreExecute is executed on the main UI thread before background processing is

        // started. In this method, we start the progressdialog.

        @Override

        protected void onPreExecute() {

            super.onPreExecute();


            // Show the progress dialog on the screen


        }


        // This method is executed in the background and will return a result to onPostExecute

        // method. It receives the file name as input parameter.

        @Override

        protected Integer doInBackground(String... urls) {


            InputStream inputStream = null;

            HttpURLConnection urlConnection = null;

            Integer result = 0;


            // TODO connect to server, download and process the JSON string


            // Now we read the file, line by line and construct the

            // Json string from the information read in.

            try {

                /* forming th java.net.URL object */

                URL url = new URL(urls[0]);

                urlConnection = (HttpURLConnection) url.openConnection();



                 /* optional request header */

                urlConnection.setRequestProperty("Content-Type", "application/json");



                /* optional request header */

                urlConnection.setRequestProperty("Accept", "application/json");



                /* for Get request */

                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();



                /* 200 represents HTTP OK */

                if (statusCode == 200) {

                    inputStream = new BufferedInputStream(urlConnection.getInputStream());


                    // Convert the read in information to a Json string

                    String response = convertInputStreamToString(inputStream);


                    // now process the string using the method that we implemented in the previous exercise
//bad way
                    Log.e("responseeee", response.replace(" ", ""));
                    JSONObject obj = new JSONObject(response.replace(" ", ""));
                    String geo = obj.getString("feeds");
                    Log.e("jsj",geo);
                    String gg[]=geo.replace("\"","").replace(":","").split("field1");
                    Log.e("jsj1",gg[2]);

                    String emergency=gg[2].replace("]","").replace("}","").replace("{","").replace(",","");

                    Log.e("jsjjsjs",emergency);

                    if (emergency.equals("8"))
                    {



                        Intent i=new Intent(MyService.this,MainalertActivity.class);

                     startActivity(i);

                    }

                } else {

                    result = 0; //"Failed to fetch data!";

                }

            } catch (Exception e) {

                Log.d("yt", e.getLocalizedMessage());

            }

            return result; //"Failed to fetch data!";

        }

    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";

        String result = "";

        while ((line = bufferedReader.readLine()) != null) {

            result += line;

        }



            /* Close Stream */

        if (null != inputStream) {

            inputStream.close();

        }

        return result;

    }

}



































