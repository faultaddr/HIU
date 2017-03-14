package com.example.wuyufan.hiu;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wuyufan.hiu.Fragment.ChatFragment;
import com.example.wuyufan.hiu.Fragment.DetailFragment;
import com.example.wuyufan.hiu.Fragment.FriendFragment;
import com.example.wuyufan.hiu.Tools.ACache;
import com.example.wuyufan.hiu.Tools.bitmapRound;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //Navigation  视图：
    private ImageView imageNav;
    private TextView userNameNav;
    private ImageView exitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                DetailFragment detailFragment=DetailFragment.newInstance();
                FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.detailContent, detailFragment,"DetailFragment");
                fragmentTransaction.commit();
                fragmentTransaction.show(detailFragment);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_navigation);
        imageNav=(ImageView)headerLayout.findViewById(R.id.photoofNav);
        userNameNav=(TextView)headerLayout.findViewById(R.id.userNameofNav);
        exitButton=(ImageView) headerLayout.findViewById(R.id.exitButton);
        //--debug info Log.i("userNameNav",(userNameNav==null)+"");
        //userNameNav.setText("1234");--debug info
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ACache cache=ACache.get(getApplication(),"User");
                cache.clear();
                ACache mCache=ACache.get(getApplication(),"ACache");
                mCache.clear();
                Intent intent=new Intent();
                intent.setClass(MainActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        try{
            ACache aCache=ACache.get(getApplication(),"User");
            Log.i("sbsbsb",aCache.getAsString("username"));
            userNameNav.setText(aCache.getAsString("username"));
        }catch (Exception ex){
            Log.i("sbsbsbs",ex.getMessage());
            ex.printStackTrace();
        }
        imageNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
                intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
                intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
                startActivityForResult(intent, 1);
            }
        });
        if(getIntent().getAction()!=null){
            Log.i(">>getIntent","!=null");
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            ChatFragment chatFragment=ChatFragment.newInstance();
            try {


                fragmentTransaction.replace(R.id.detailContent,chatFragment,"ChatFragment");
                fragmentTransaction.commit();
                //fragmentTransaction.hide(getSupportFragmentManager().findFragmentByTag("FriendFragment"));
            }catch (Exception e){
                e.printStackTrace();
            }
            finally {
                fragmentTransaction.show(chatFragment);
                fab.hide();
            }

        }
        else {
            Log.i(">>onCreat", "success");
            FriendFragment friendFragment = FriendFragment.newInstance();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.detailContent, friendFragment, "FriendFragment");
            fragmentTransaction.commit();
            fragmentTransaction.show(friendFragment);
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Log.e("uri", uri.toString());
            ContentResolver cr = this.getContentResolver();
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));

                /* 将Bitmap设定到ImageView */
                Bitmap newBitmap= bitmapRound.makeRoundCorner(bitmap);
                imageNav.setImageBitmap(newBitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(),e);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
