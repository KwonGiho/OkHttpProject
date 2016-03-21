package com.example.adminstrator.okhttpproject;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.sendBtn)
    Button sendBtn;
    @Bind(R.id.textView)
    TextView textView;
    public static final String URL="https://api.github.com/repos/square/retrofit/contributors";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

//http://emflant.tistory.com/47
    @OnClick(R.id.sendBtn)
    public void setSendBtn(View view) {
        OkHttpClient okhttpCLient = new OkHttpClient();
        Request request = new Request.Builder().url(URL).build();
        okhttpCLient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(!response.isSuccessful()) // 2.0부터 추가 되었다규!
                    return ;
                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.create();


                String result = response.body().toString();

                List<Contributor> contributors = gson.fromJson(result, new TypeToken<List<Contributor>>() {
                }.getType());


                /*for(Contributor contributor : contributors) {
                    stringBuilder.append(contributor.toString());
                }*/
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //textView.setText(stringBuilder.toString());
                    }
                });


            }
        });
    }
}
/*
음.. 제가 JSON을 많이 사용해보지 못해서, JSON을 완벽히 사용할 수 있으면 GSON으로 넘어가야 겠다~ 했습니다.
그래서 아래와 같이 코드를 작성했는데, 코딩을 차츰 해보니, Java1.3버전에서 작성된 JSON lib라고 나오더라구요!
에공..이게 뭐꼬~~ 하면서
시간이 촉박해져서 GSON으로 급하게 갈아 탔습니다~ㅠ

String body = response.body().string();
                JSONParser jsonParser = new JSONParser();
                JSONArray jsonArray = null;
                try {
                    jsonArray = (JSONArray)jsonParser.parse(body);

                } catch(ParseException pe) {pe.printStackTrace();}

                final StringBuilder stringBuilder = new StringBuilder();
                try {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                        stringBuilder.append(jsonObject.get("login")+"\n");
                    }
                } catch ( JSONException jsonException) {
                    jsonException.printStackTrace();
                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }


                MainActivity.this.runOnUiThread(new Runnable(){
                    public void run() {
                        textView.setText(stringBuilder.toString());
                    }
                });


 */
