package kr.deu.sw.lnp.whmanageapplication;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class InvenActivity extends AppCompatActivity {
    //리스트 표시하는 화면 : 리스트 header 등록, header에서 query실행
    ListView listView = null;
    ArrayList<WHData> arrayList = null;
    InvenAdapter adapter = null;

    private static final int CONNECTION_TIME = 5000;

    private static final String TAG_RESULT = "result";
    private static final String TAG_PID    = "p_id";
    private static final String TAG_PNAME  = "p_name";
    private static final String TAG_AMOUNT = "amount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inven);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView)findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        adapter = new InvenAdapter(this, arrayList);
        listView.setAdapter(adapter);
        dataSelect("https://sonagod.tk/data_select.php");
    }

    private void showList(String phpData) {
        //json 사용해서 arraylist 만듬
        arrayList.clear();
        try {
            JSONObject jsonObject = new JSONObject(phpData);
            JSONArray inven = jsonObject.getJSONArray(TAG_RESULT);
            for(int i=0;i<inven.length();i++) {
                //재고 총 리스트 수 만큼 루프
                JSONObject obj = inven.getJSONObject(i);
                String p_id = obj.getString(TAG_PID);
                String p_name = obj.getString(TAG_PNAME);
                int amount = obj.getInt(TAG_AMOUNT);

                arrayList.add(new WHData(p_id,p_name,amount));
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void dataSelect(String url) {
        class PHPSelect extends AsyncTask<String, Integer, String> {
            ProgressDialog dialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog = ProgressDialog.show(InvenActivity.this, "데이터를 불러오는 중입니다.", null, true, true);
            }

            @Override
            protected String doInBackground(String... param) {
                //background 작업 시작
                //파라미터 : url
                try {
                    StringBuilder builder = new StringBuilder();
                    URL url = new URL(param[0]);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    if (conn != null) {
                        //연결시작되면
                        conn.setConnectTimeout(CONNECTION_TIME);
                        conn.setUseCaches(false);
                        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            //연결 성공 시 실행
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            String line = null;
                            while( (line = reader.readLine()) != null ) {
                                builder.append(line);
                                builder.append("\n");
                            }
                            //읽어온 데이터를 저장함
                            conn.disconnect();
                        }
                    }
                    return builder.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                //작업 완료 => listview에 데이터 보여줌
                showList(result);
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }

        PHPSelect task = new PHPSelect();
        task.execute(url);
    }
}
