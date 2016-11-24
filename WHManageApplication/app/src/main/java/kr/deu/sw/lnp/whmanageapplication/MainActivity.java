package kr.deu.sw.lnp.whmanageapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //메인화면

    private final static int WAREHOUSING_REQUEST = 0;
    private final static int UNSTORING_REQUEST = 1;

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
                unsto_Intent.putExtra("SCAN)MODE","ALL");
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
                    String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                    Toast.makeText(MainActivity.this, content + "/" + format, Toast.LENGTH_SHORT).show();
                }
                break;
            case UNSTORING_REQUEST :
                //상품 출고 : 데이터베이스 접근해서 상품정보와 일치하는 데이터의 수량을 1 감소
                //만약 데이터가 없을 경우 : 데이터가 없다는 Dialog 띄우기
                if(resultCode == RESULT_OK) {
                    String content = data.getStringExtra("SCAN_RESULT");
                    String format = data.getStringExtra("SCAN_RESULT_FORMAT");

                    Toast.makeText(MainActivity.this, content + "/" + format, Toast.LENGTH_SHORT).show();
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
}
