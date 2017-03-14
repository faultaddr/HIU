package com.example.wuyufan.hiu.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wuyufan.hiu.Adapter.ListViewAdapter;
import com.example.wuyufan.hiu.Database.Info;
import com.example.wuyufan.hiu.R;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobRealTimeData;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FriendFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //View 初始化
    private ListView listView;
    private ListViewAdapter listViewAdapter;
    //数据初始化
    private ArrayList<String> mUserName = new ArrayList<>();
    private ArrayList<String> mTime = new ArrayList<>();
    private ArrayList<String> mContent = new ArrayList<>();
    private ArrayList<String> mInterest = new ArrayList<>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public FriendFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment FriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendFragment newInstance() {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();

        //fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bmob.initialize(getActivity(),"4ae63084cfae4043d7621e7bf44725ad");
        BmobRealTimeData rtd = new BmobRealTimeData();
        rtd.start(new ValueEventListener() {
            @Override
            public void onDataChange(JSONObject data) {
                Log.d("bmob", "(" + data.optString("action") + ")" + "数据：" + data);


            }

            @Override
            public void onConnectCompleted(Exception ex) {
                Log.d("bmob", "连接成功:" + ex.getMessage());
            }
        });
        mInterest.add("运动");
        mContent.add("我想去打球");
        mTime.add("3月十号晚上");
        mUserName.add("潘云逸");
        //rtd.subTableUpdate("Info");

        AsyncTask asyncTask = null;
        asyncTask = new loadData();
        asyncTask.execute();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friend, container, false);
        listView = (ListView) view.findViewById(R.id.friend_circle);

        // Inflate the layout for this fragment
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

    class loadData extends AsyncTask<Object, Void, Boolean> {
        public loadData() {

        }

        @Override

        protected void onPostExecute(Boolean aBoolean) {

            Log.i(">>onPostExecute","success");
            Log.i(">>mUserName",mUserName.size()+"");
            //ListViewAdapter listViewAdapter=new ListViewAdapter(getActivity(), mUserName, mTime, mContent, mInterest);
            listViewAdapter=new ListViewAdapter(getActivity(), mUserName, mTime, mContent, mInterest);
            listView.setAdapter(listViewAdapter);
            setListViewHeight(listView);
            //listView.postInvalidate();//listView 刷新
        }

        @Override
        protected Boolean doInBackground(Object[] objects) {
            Log.i(">>doInBackground","success");
            BmobQuery<Info> query = new BmobQuery<Info>();
//查询playerName叫“比目”的数据
            query.addWhereEqualTo("UserName", "123");

//返回50条数据，如果不加上这条语句，默认返回10条数据
            query.setLimit(100);
//执行查询方法
            query.findObjects(new FindListener<Info>() {
                @Override
                public void done(List<Info> object, BmobException e) {
                    if (e == null) {
                        Log.i(">>查询", "查询成功：共" + object.size() + "条数据。");
                        for (Info info : object) {
                            //获得playerName的信息
                            mUserName.add(info.getUserName());
                            //获得数据的objectId信息
                            mContent.add(info.getContent());

                            mInterest.add(info.getInterest());
                            mTime.add(info.getTime());
                            //info.getCreatedAt();
                            //获得createdAt数据创建时间（注意是：createdAt，不是createAt）

                        }
                    } else {
                        Log.i("bmob", "失败：" + e.getMessage() + "," + e.getErrorCode());
                    }
                }
            });
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
    public void setListViewHeight(ListView listView) {

        // 获取ListView对应的Adapter

        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
