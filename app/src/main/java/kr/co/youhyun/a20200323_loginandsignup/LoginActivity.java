package kr.co.youhyun.a20200323_loginandsignup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.youhyun.a20200323_loginandsignup.databinding.ActivityLoginBinding;
import kr.co.youhyun.a20200323_loginandsignup.datas.User;
import kr.co.youhyun.a20200323_loginandsignup.utils.ContextUtil;
import kr.co.youhyun.a20200323_loginandsignup.utils.ServerUtil;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SignUpActivity.class);
                startActivity(intent);
            }
        });

//        체크박스에 체크가 될 때마다 체크 여부를 저장
        binding.autoLoginCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                ContextUtil을 이용해서 체크 여부를 저장
                ContextUtil.setAutoLoginCheck(mContext, isChecked);
            }
        });


//        로그인 버튼을 누르면 => 아이지 저장이 체크되어 있다면 입력되어있는 이메일 저장
        // 그렇지 않다면 이메일을 빈칸 ""으로 저장
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputEmail = binding.emailEdt.getText().toString();
                String inputPw = binding.pwEdt.getText().toString();

                ServerUtil.postRequestLogin(mContext, inputEmail, inputPw, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {

//                        응답실행코드는 비동기 처리가 반드시 필요!
//                        비동기 : 다른 할 일들을 하다가 완료되면 별도로 실행해주자.
//                        okhttp : 비동기 처리를 자동으로 지원 => 별도 쓰레드가 알아서 진행
//                        => 이 onResponse는 다른 쓰레드가 돌리고 있다.
//                         UI 동작은 메인쓰레드가 전용으로 처리한다.
//                        -> 다른쓰레드가 UI를 건드리면 앱이 터진다.

                        Log.d("JSON내용-메인에서", json.toString());

                        try {
                            final String message = json.getString("message");
                            Log.d("서버가 주는 메세지", message);

                            int code = json.getInt("code");
                            Log.d("서버가 주는 코드값", code+"");

                            if (code == 200) {
                                // 로그인 성공!
                                // 해당 기능이 성공적으로 동작

                                JSONObject data = json.getJSONObject("data");
                                JSONObject user = data.getJSONObject("user");
                                final String token = data.getString("token");

//                                로그인한 사람의 이름과 번호를 토스트로 띄우기
//                                final String name = user.getString("name");
//                                final String phone = user.getString("phone");

                                final User loginUser = User.getUserFromJson(user);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, String.format("%s / %s", loginUser.getName(), loginUser.getPhone()), Toast.LENGTH_SHORT).show();

//                                        따온 토큰을 SharedPreference에 저장 => 로그인에 성공 + 내가 누군지 기록
                                        ContextUtil.setUserToken(mContext, token);


                                        // 메인화면으로 진입 => 내 프로필 정보를 출력 => 저장된 토큰을 이용할 예정
                                        Intent intent = new Intent(mContext, MainActivity.class);
                                        startActivity(intent);
                                    }
                                });

                            } else {
                                // 뭔가 문제가 있었다.

                                // Toast를 띄우는데 앱이 죽는다! => UI쓰레드가 아닌데 토스트를 띄우니까..!!
                                // 조치 : UIThread 안에서 토스트를 띄우자

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        }
        });
    }

    @Override
    public void setValues() {

//        이 화면을 키면, 저장된 이메일값을 emailEdt에 입력
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));
        binding.autoLoginCheckBox.setChecked(ContextUtil.isAutoLoginCheck(mContext));
    }
}
