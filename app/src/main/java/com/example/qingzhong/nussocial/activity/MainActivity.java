package com.example.qingzhong.nussocial.activity;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.qingzhong.nussocial.R;
import com.example.qingzhong.nussocial.adapter.PageIndicatorAdapter;
import com.example.qingzhong.nussocial.cons.StatusCons;
import com.example.qingzhong.nussocial.fragment.PlayGroundFragment;
import com.viewpagerindicator.TabPageIndicator;

import java.io.IOException;

public class MainActivity extends FragmentActivity implements PlayGroundFragment.ControlPostRead{
    //private ActionBar actionBar;

    private ViewPager pager;
    public TabPageIndicator pageIndicator;
    private BroadcastReceiver readReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager=(ViewPager)findViewById(R.id.pager);
        pager.setAdapter(new PageIndicatorAdapter(getSupportFragmentManager(), this));

        pageIndicator=(TabPageIndicator)findViewById(R.id.pageindicator);
        pageIndicator.setViewPager(pager);

    }


    @Override
    protected void onResume() {
        super.onResume();
        //Toast.makeText(this,"back",Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    @Override
    public void readPost() {

        this.pageIndicator.getTabView(0).getTabMarker().setVisibility(View.INVISIBLE);

    }

    @Override
    public void hasnewPost(int number) {
        this.pageIndicator.getTabView(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "back from send", Toast.LENGTH_SHORT).show();
        if(requestCode== StatusCons.SEND_POST){
            if(resultCode==RESULT_OK){

                try {
                    ((PageIndicatorAdapter) this.pager.getAdapter()).getPlaygroundFragment().startDownloading();
                }
                catch(IOException e){
                    Toast.makeText(this, "download failed... ", Toast.LENGTH_SHORT).show();
                }
            }
            if(requestCode==RESULT_CANCELED){
                Toast.makeText(this, "cancel the sending", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
