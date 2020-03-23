package kr.co.youhyun.a20200323_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import org.json.JSONObject;

import kr.co.youhyun.a20200323_loginandsignup.databinding.ActivityMainBinding;
import kr.co.youhyun.a20200323_loginandsignup.utils.ContextUtil;
import kr.co.youhyun.a20200323_loginandsignup.utils.ServerUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

//        체크박스에 체크가 될 때마다 체크 여부를 저장
        binding.idCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                ContextUtil을 이용해서 체크 여부를 저장
                ContextUtil.setIdCheck(mContext, isChecked);
            }
        });


//        로그인 버튼을 누르면 => 아이지 저장이 체크되어 있다면 입력되어있는 이메일 저장
        // 그렇지 않다면 이메일을 빈칸 ""으로 저장
        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.idCheckBox.isChecked()) {
                    String inputEmail = binding.emailEdt.getText().toString();
                    ContextUtil.setEmail(mContext, inputEmail);
                } else  {
                    ContextUtil.setEmail(mContext, "");
                }

                String inputEmail = binding.emailEdt.getText().toString();
                String inputPw = binding.pwEdt.getText().toString();

                ServerUtil.postRequestLogin(mContext, inputEmail, inputPw, new ServerUtil.JsonResponseHandler() {
                    @Override
                    public void onResponse(JSONObject json) {
                        Log.d("JSON내용-메인에서", json.toString());
                    }
                });
        }
        });
    }

    @Override
    public void setValues() {

//        이 화면을 키면, 저장된 이메일값을 emailEdt에 입력
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));
        binding.idCheckBox.setChecked(ContextUtil.isIdCheck(mContext));
    }
}
