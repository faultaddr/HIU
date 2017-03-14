package com.example.wuyufan.hiu.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuyufan.hiu.Database.Info;
import com.example.wuyufan.hiu.R;

import java.util.Calendar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**

 */
public class DetailFragment extends Fragment implements View.OnClickListener{
    //view 声明
    private TextView textContent;
    private Button buttonSubmit;
    private DatePicker datePicker;
    // String 初始化
    private String Content;//内容
    // Integer 初始化
    private static int year,month,day;


    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance() {
        DetailFragment fragment = new DetailFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_detail2, container, false);
        textContent=(TextView) view.findViewById(R.id.content_et);
        buttonSubmit=(Button)view.findViewById(R.id.send_btn);
        datePicker=(DatePicker)view.findViewById(R.id.datePicker);
        Calendar calendar= Calendar.getInstance();


        datePicker.init((int) calendar.get(Calendar.YEAR), (int) calendar.get(Calendar.MONTH), (int) calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                year=i;
                month=i1;
                day=i2;
            }
        });
        buttonSubmit.setOnClickListener(this);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        Content=textContent.getText().toString();
        if(Content.equals("")){
            Toast.makeText(getContext(),"请不要发送空消息",Toast.LENGTH_LONG).show();

        }
        else{
            Info info=new Info();
            info.setTime(year+month+day+"");
            info.setUserName(BmobUser.getCurrentUser().toString());
            info.setContent(Content);
            info.setInterest("运动");
            //TODO-LIST: 把interest在一开始就写入User数据库并且在本地保存一个备份，每次读写的时候就从本地拿，本地没有再从网络上读取。
            info.save(new SaveListener<String>() {

                @Override
                public void done(String objectId, BmobException e) {
                    if(e==null){
                        Toast.makeText(getContext(),"创建数据成功：" + objectId,Toast.LENGTH_LONG);
                    }else{
                        Toast.makeText(getContext(),"网络连接不畅或者不符合国家有关规定：" + objectId,Toast.LENGTH_LONG);
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }
            });
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
