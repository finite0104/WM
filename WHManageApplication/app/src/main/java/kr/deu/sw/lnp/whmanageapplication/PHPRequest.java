package kr.deu.sw.lnp.whmanageapplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by how32 on 2016-11-30.
 */

public class PHPRequest {
    /*
    입고 (dataInsert, warehousing)
     => 데이터를 먼저 확인, 없을 경우에만 insert 작업하고 있는 경우에는 update 작업 수행
    출고 (unstoring)
     => 데이터를 먼저 확인, 없을 경우에는 오류메세지 출력(php 에서 출력됨)하고
                데이터가 있는 경우에는 있는 데이터를 확인해서 update 작업 수행
     */

    private static int CONNECTION_TIME = 5000;
    private String result = "";
    private ArrayList<WHData> arrayList = new ArrayList<>();

    public String dataInsert(String p_num, String p_name) {
        //데이터 입력(입고)
        final String data = "p_num=" + p_num + "&p_name=" + p_name;
        String findResult = dataFind(p_num,p_name);
        if(findResult.equals("데이터 없음")) {
            new Thread() {
                public void run() {
                    try {
                        URL url = new URL("주소");
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
                    } catch (Exception e) {
                        e.printStackTrace();
                        result = e.toString();
                    }
                }
            }.start();
            return result;
        }else{
            String updateResult = warehousing(p_num, p_name);
            return updateResult;
        }
    }

    public ArrayList<WHData> dataSelect() {
        return null;
    }

    public String unstoring(String p_num, String p_name) {
        //출고
        return null;
    }

    private String warehousing(String p_num, String p_name) {
        //입고
        return null;
    }

    private String dataFind(String p_num, String p_name) {
        //데이터 찾기
        final String data = "p_num=" + p_num + "&p_name=" + p_name;
        new Thread() {
            public void run() {
                try{
                    URL url = new URL("주소");
                    HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    if(conn != null) {
                        //연결시작되면
                        conn.setConnectTimeout(CONNECTION_TIME);
                        conn.setUseCaches(false);

                        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
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
                }catch(Exception e) {
                    e.printStackTrace();
                    result = e.toString();
                }
            }
        }.start();
        return result;
    }
}
