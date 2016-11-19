package tk.sonaapi.sona_bacode;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import net.daum.mf.speech.api.SpeechRecognizerActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Button b_scan,b_negative,b_naver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_scan = (Button)findViewById(R.id.b_scan);
        b_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent("com.google.zxing.client.android.SCAN");
                startActivityForResult(i,0);
            }
        });
        b_negative = (Button)findViewById(R.id.b_negative);
        b_negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), VoiceRecoActivity.class);
                i.putExtra(SpeechRecognizerActivity.EXTRA_KEY_API_KEY, "7a27aca5064cc457cf581a8ab107db4c"); // apiKey는 신청과정을 통해     package와 매치되도록 발급받은 APIKey 문자열 값.
                startActivityForResult(i, 0);
            }
        });
        b_naver = (Button)findViewById(R.id.b_naver);
        b_naver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,NaverActivity.class);
                startActivity(i);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) { // 성공
            ArrayList<String> results = data.getStringArrayListExtra(VoiceRecoActivity.EXTRA_KEY_RESULT_ARRAY);
            // ....
        }
        else if (requestCode == RESULT_CANCELED) { // 실패
            int errorCode = data.getIntExtra(VoiceRecoActivity.EXTRA_KEY_ERROR_CODE, -1);
            String errorMsg = data.getStringExtra(VoiceRecoActivity.EXTRA_KEY_ERROR_MESSAGE);
            // ....
        }
    }
}