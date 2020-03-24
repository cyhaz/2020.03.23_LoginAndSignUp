package kr.co.youhyun.a20200323_loginandsignup;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import org.json.JSONException;
import org.json.JSONObject;

import kr.co.youhyun.a20200323_loginandsignup.databinding.ActivityMainBinding;
import kr.co.youhyun.a20200323_loginandsignup.datas.User;
import kr.co.youhyun.a20200323_loginandsignup.utils.ContextUtil;
import kr.co.youhyun.a20200323_loginandsignup.utils.ServerUtil;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setupEvents();
        setValues();
    }

    @Override
    public void setupEvents() {

        binding.goToBoardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BoardListActicity.class);
                startActivity(intent);
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                얼럿으로 "정말 로그아웃 하시겠습니까?"
                AlertDialog.Builder alert = new AlertDialog.Builder(mContext);
                alert.setMessage("정말 로그아웃하시겠습니까?");
                alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        로그아웃 처리
//                        저장된 토큰 날려버림 => 로그인 액티비티를 띄우자 => 메인액티비티 종료
                        ContextUtil.setUserToken(mContext, "");
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                alert.setNegativeButton("취소", null);
                alert.show();
            }
        });
    }

    @Override
    public void setValues() {
//        저장된 토큰을 확인해서 => 그 토큰으로 내 정보를 서버에서 다시 불러올것 - 조회 (Get)
        ServerUtil.getReuestMyInfo(mContext, new ServerUtil.JsonResponseHandler() {
            @Override
            public void onResponse(JSONObject json) {
                Log.d("내정보", json.toString());

                try {
                    int code = json.getInt("code");
                    if (code == 200) {
                        JSONObject data = json.getJSONObject("data");
                        JSONObject user = data.getJSONObject("user");
                        final User myinfo = User.getUserFromJson(user);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nameTxt.setText(myinfo.getName());
                                binding.memoTxt.setText(myinfo.getMemo());
                                binding.phoneTxt.setText(myinfo.getPhone());
                            }
                        });
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
       });

    }
}
