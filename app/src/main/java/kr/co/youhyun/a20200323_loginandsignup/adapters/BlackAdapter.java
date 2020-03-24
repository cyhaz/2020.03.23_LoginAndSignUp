package kr.co.youhyun.a20200323_loginandsignup.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

import kr.co.youhyun.a20200323_loginandsignup.R;
import kr.co.youhyun.a20200323_loginandsignup.datas.Black;

public class BlackAdapter extends ArrayAdapter<Black> {

    Context mContext;
    List<Black> mList;
    LayoutInflater inf;

    public BlackAdapter(@NonNull Context context, int resource, @NonNull List<Black> objects) {
        super(context, resource, objects);

        mContext = context;
        mList = objects;
        inf = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;

        if (row == null) {
            row = inf.inflate(R.layout.black_list_item, null);
        }

        Black data = mList.get(position);

        TextView writerNameTxt = row.findViewById(R.id.writerNameTxt);
        TextView titleTxt = row.findViewById(R.id.titleTxt);
        TextView createdAtTxt = row.findViewById(R.id.createdAtTxt);
        TextView contentTxt = row.findViewById(R.id.contextTxt);

        writerNameTxt.setText(data.getWriter().getName());
        titleTxt.setText(data.getTitle());
        contentTxt.setText(data.getContent());

//        잓작성일시 출력 : Calendar => String으로 변환 => Black의 기능으로 추가
        createdAtTxt.setText(data.getFormattedCreateAt());


        return row;
    }
}
