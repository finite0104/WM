package kr.deu.sw.lnp.whmanageapplication;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.daum.mf.speech.api.SpeechRecognizeListener;
import net.daum.mf.speech.api.SpeechRecognizerClient;
import net.daum.mf.speech.api.SpeechRecognizerManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SpeechRecognizeListener{
    //메인화면

    private final static int WAREHOUSING_REQUEST = 0;
    private final static int UNSTORING_REQUEST = 1;
    private static final int CONNECTION_TIME = 5000;

    String apikey;
    Button b_warehousing,b_unstoring,b_search,b_setting;
    SpeechRecognizerClient client;
    SpeechRecognizerClient.Builder builder;
    AudioManager am;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        b_warehousing = (Button)findViewById(R.id.b_warehousing);
        b_unstoring = (Button)findViewById(R.id.b_unstoring);
        b_search = (Button)findViewById(R.id.b_search);
        b_setting = (Button)findViewById(R.id.b_setting);

        SpeechRecognizerManager.getInstance().initializeLibrary(this);
        apikey = "516d3c46cd3d6cccf477091a2c5068d1";
        builder = new SpeechRecognizerClient.Builder().setApiKey(apikey).setServiceType(SpeechRecognizerClient.SERVICE_TYPE_WEB);
        client = builder.build();
        client.setSpeechRecognizeListener(this);
        client.startRecording(true);
        am = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
    }

    public void onClick(View v){
        switch(v.getId()) {
            case R.id.b_warehousing :
                //입고
                Intent ware_Intent = new Intent("com.google.zxing.client.android.SCAN");
                ware_Intent.putExtra("SCAN_MODE", "ALL");
                startActivityForResult(ware_Intent, WAREHOUSING_REQUEST);
                break;
            case R.id.b_unstoring :
                //출고
                Intent unsto_Intent = new Intent("com.google.zxing.client.android.SCAN");
                unsto_Intent.putExtra("SCAN_MODE","ALL");
                startActivityForResult(unsto_Intent, UNSTORING_REQUEST);
                break;
            case R.id.b_search :
                //검색
                Intent info_Intent = new Intent(this, InfoActivity.class);
                startActivity(info_Intent);
                break;
            case R.id.b_setting :
                //설정
                Intent setting_Intent = new Intent(this, SettingsActivity.class);
                startActivity(setting_Intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch(requestCode) {
            case WAREHOUSING_REQUEST :
                //상품 입고 : 데이터베이스 접근해서 상품정보와 일치하는 데이터의 수량을 1 증가
                //만약 데이터가 없을 경우 : 데이터가 없다는 Dialog 띄우기
                if(resultCode == RESULT_OK) {
                    String content = data.getStringExtra("SCAN_RESULT");
                    String[] p_data = content.split("/");
                    String p_id = p_data[0];
                    String p_name = p_data[1];

                    dataInsert("http://sonagod.tk/data_select.php", p_id, p_name);
                }
                break;
            case UNSTORING_REQUEST :
                //상품 출고 : 데이터베이스 접근해서 상품정보와 일치하는 데이터의 수량을 1 감소
                //만약 데이터가 없을 경우 : 데이터가 없다는 Dialog 띄우기
                if(resultCode == RESULT_OK) {
                    String content = data.getStringExtra("SCAN_RESULT");
                    String[] p_data = content.split("/");
                    String p_id = p_data[0];
                    String p_name = p_data[1];

                    dataInsert("http://sonagod.tk/warehousing.php", p_id, p_name);
                }
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

    public void onDestroy() {
        super.onDestroy();

        SpeechRecognizerManager.getInstance().finalizeLibrary();
    }

    @Override
    public void onReady() {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int i, String s) {
        Log.d("Error",s);
    }

    @Override
    public void onPartialResult(String s) {

    }

    @Override
    public void onResults(Bundle bundle) {
        ArrayList<String> texts =bundle.getStringArrayList(SpeechRecognizerClient.KEY_RECOGNITION_RESULTS);
        Log.d("result : ",texts.get(0));
        if(texts.get(0).equals("입고")||texts.get(0).equals("입구")){
            //client.stopRecording();
            //am.setStreamVolume(AudioManager.STREAM_MUSIC,10,AudioManager.FLAG_PLAY_SOUND);
            b_warehousing.performClick();
        }
        else if(texts.get(0).equals("출고")||texts.get(0).equals("출구")) {
            //client.stopRecording();
            //am.setStreamVolume(AudioManager.STREAM_MUSIC,10,AudioManager.FLAG_PLAY_SOUND);
            b_unstoring.performClick();

        }
        else if(texts.get(0).equals("재고")) {
            b_search.performClick();
            client.cancelRecording();
            am.setStreamVolume(AudioManager.STREAM_MUSIC,10,AudioManager.FLAG_PLAY_SOUND);
        }
        else if(texts.get(0).equals("설정")) {
            b_setting.performClick();
            client.cancelRecording();
            am.setStreamVolume(AudioManager.STREAM_MUSIC,10,AudioManager.FLAG_PLAY_SOUND);
        }
        else{
            client.startRecording(true);
        }
    }

    @Override
    public void onAudioLevel(float v) {

    }

    @Override
    public void onFinished() {

    }

    private void dataInsert(String url, String p_id, String p_name) {
        class PHPInsert extends AsyncTask<String, Integer, String> {

            @Override
            protected String doInBackground(String... params) {
                //파라미터 1 : url, 2 : p_id, 3 : p-name
                String result = null;
                String data = "p_id=" + params[1] + "&p_name=" + params[2];
                try {
                    URL url = new URL(params[0]);
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
                            OutputStream outputStream = conn.getOutputStream();
                            outputStream.write(data.getBytes("UTF-8"));
                            outputStream.flush();
                            outputStream.close();
                            //데이터 입력 후 전송
                            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                            result = reader.readLine();
                            //읽어온 데이터를 저장함
                            conn.disconnect();
                        }
                    }
                    return result;
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        }

        PHPInsert phpInsert = new PHPInsert();
        phpInsert.execute(url, p_id, p_name);
    }
}
