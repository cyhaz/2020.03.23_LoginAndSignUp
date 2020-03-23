package kr.co.youhyun.a20200323_loginandsignup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import kr.co.youhyun.a20200323_loginandsignup.databinding.ActivityMainBinding;
import kr.co.youhyun.a20200323_loginandsignup.utils.ContextUtil;

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
        }
        });
    }

    @Override
    public void setValues() {

//        이 화면을 키면, 저장된 이메일값을 emailEdt에 입력
        binding.emailEdt.setText(ContextUtil.getEmail(mContext));

    }
}
