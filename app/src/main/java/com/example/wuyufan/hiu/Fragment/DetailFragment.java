package com.example.wuyufan.hiu.Fragment;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wuyufan.hiu.Database.Info;
import com.example.wuyufan.hiu.MainActivity;
import com.example.wuyufan.hiu.R;

import java.util.Calendar;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**

 */
public class DetailFragment extends Fragment {
    //view 声明
    private TextView textContent;
    private Button buttonSubmit;
    private DatePicker datePicker;
    private Spinner spinner;
    // String 初始化
    private String Content;//内容
    private String Interest;//兴趣记录

    String []item={"运动", "约炮", "搞基", "学习", "约饭", "电影", "博物馆", "瞎逛", "讲座", "实习"};
    // Integer 初始化
    private static int year,month,day;
    private static int position;

    private OnFragmentInteractionListener mListener;

    public DetailFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(Bundle bundle) {
        DetailFragment fragment = new DetailFragment();
        position = bundle.getInt("position");
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_detail2, container, false);
        textContent=(TextView) view.findViewById(R.id.content_et);
        buttonSubmit=(Button)view.findViewById(R.id.send_btn);
        datePicker=(DatePicker)view.findViewById(R.id.datePicker);
        //spinner=(Spinner)view.findViewById(R.id.spinner);
        Calendar calendar= Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Interest=item[i];
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

        datePicker.init((int) calendar.get(Calendar.YEAR), (int) calendar.get(Calendar.MONTH), (int) calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                Log.i(">>dataPicker", i + " " + i1 + " " + i2 + " ");
                year=i;
                month=i1;
                day=i2;
            }
        });
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(">>button","onClick");
                Content=textContent.getText().toString();
                if(Content.equals("")){
                    Toast.makeText(getContext(),"请不要发送空消息",Toast.LENGTH_LONG).show();

                }
                else{
                    Info info=new Info();
                    info.setTime(year + "/" + month + "/" + day + "/");
                    info.setUserName(BmobUser.getCurrentUser().getUsername());
                    info.setContent(Content);
                    //info.setInterest(Interest);//这是用Spinner的
                    info.setInterest(item[position]);

                    //TODO-LIST: 把interest在一开始就写入User数据库并且在本地保存一个备份，每次读写的时候就从本地拿，本地没有再从网络上读取。
                    info.save(new SaveListener<String>() {

                        @Override
                        public void done(String objectId, BmobException e) {
                            if(e==null){
                                Toast.makeText(getContext(),"创建数据成功：" + objectId,Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(getContext(),"网络连接不畅或者不符合国家有关规定：" + objectId,Toast.LENGTH_LONG).show();
                                Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                            }

//                            Intent intent=new Intent();
//                            intent.setAction("detailToactivity");
//                            intent.setClass(getContext(),MainActivity.class);
//                            getContext().startActivity(intent);
//                            FriendFragment friendFragment=FriendFragment.newInstance(null);
//                            FragmentTransaction fragmentTransaction=getActivity().getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.hide(getActivity().getSupportFragmentManager().findFragmentByTag("DetailFragment"));
//                            fragmentTransaction.add(R.id.detailContent,friendFragment);
//                            fragmentTransaction.commit();
//                            fragmentTransaction.show(friendFragment);

                        }
                    });
                }
            }
        });

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
