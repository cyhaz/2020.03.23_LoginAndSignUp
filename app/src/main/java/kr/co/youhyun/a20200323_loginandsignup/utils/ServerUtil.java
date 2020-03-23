package kr.co.youhyun.a20200323_loginandsignup.utils;

import android.content.Context;
import android.util.Log;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ServerUtil {

//    이론
//    서버통신의 주체? ServerUtil
//    응답처리? 액티비티가 한다. => 인터페이스로 연결

    public interface JsonResponseHandler {
        void onResponse(JSONObject json);
    }

//   서버 호스트주소를 편하게 가져다 쓰려고 변수로 저장
    private static final String BASE_URL = "http://192.168.0.236:5000/";

//    로그인 요청 기능 메쏘드
//    파라미터 기본구조 : 어떤화면에서? 어떤 응답 처리를 할지? 를 변수로
//    파라미터 추가 : 서버로 전달할 때 필요한 데이터들을 변수로 받음
    public static void postRequestLogin(Context context, String id, String pw, final JsonResponseHandler handler) {

//        클라이언트 역할 수행 변수 생성
        OkHttpClient client = new OkHttpClient();

//        어느 주소(호스트주소/기능주소)로 갈지? String 변수로 저장
        String urlstr = String.format("%s/auth", BASE_URL);

//        서버로 들고갈 파라미터를 담아줘야한다.
        FormBody formData = new FormBody.Builder()
                .add("login_id", id)
                .add("password", pw)
                .build();

        Request request = new Request.Builder()
                .url(urlstr)
                . post(formData)
                .build();
                // 필요한 경우 헤더도 추가해야한다.


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                연결에 실패했을 때의 처리
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                연결에 성공해서 응답이 돌아왔을 때의 처리 => string()으로 변환
                String body = response.body().string();
                Log.d("로그인응답!!!!!!", body);

//                응답 내용을 JSON 객체로 가공
                try {
//                    body의 String을 JSONObject 형태로 변환
//                    양식에 맞지않는 내용이면, 앱이 터질 수 있으니 try / catch로 감싸도록 처리
                    JSONObject json = new JSONObject(body);

//                    이 JSON에 대한 분석은 화면단에 넘겨주자
                    if (handler != null) {
                        handler.onResponse(json);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
