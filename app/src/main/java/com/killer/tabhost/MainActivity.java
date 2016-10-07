package com.killer.tabhost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener , Fragment1.onMessageListener{

    private FragmentTabHost mTabHost;
    private LinearLayout button1, button3; //此处按钮是用布局文件ID

    //初始化标签数组
    String tabs[] = {"Tab1", "Tab2", "Tab3", "Tab4"};

    //初始化界面数组
    Class cls[] = {Fragment1.class, Fragment2.class, Fragment3.class,
            Fragment4.class};

    private ImageView image1, image2, image3, image4; // 下方按钮需要用的图片
    private TextView text1, text2, text3, text4, tvMsgNum; // 下方按钮需要用的文字和新消息数字
    private RelativeLayout button0, button2, rlNum1; //显示数字标签布局
    private int lastSelectButton; // 存储最后点击的布局按钮位置数

    private ActionBar mActionBar; //标题栏
    private BgChangeReceiver mBgChangeReceiver; //标题栏背景色更改监听广播

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //下面是获取写SD卡权限和测试程序运行速度代码
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,
//                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
//        }
//        Debug.startMethodTracing("tabhost");
        //初始化控件
        initView();
    }

//    @Override
//    protected void onDestroy() {
//        Debug.stopMethodTracing();
//        super.onDestroy();
//    }


    @Override
    protected void onResume() {
        super.onResume();
        //返回最后的选择
//        setLayoutButton(lastSelectButton);
    }


    private void initView() {
        //实例化控件
        this.image1 = (ImageView) findViewById(R.id.image1);
        this.image2 = (ImageView) findViewById(R.id.image2);
        this.image3 = (ImageView) findViewById(R.id.image3);
        this.image4 = (ImageView) findViewById(R.id.image4);
        this.text1 = (TextView) findViewById(R.id.text1);
        this.text2 = (TextView) findViewById(R.id.text2);
        this.text3 = (TextView) findViewById(R.id.text3);
        this.text4 = (TextView) findViewById(R.id.text4);

        //实例化 FragmentTabHost (注：id 的获取必须为固定tabhost) 与 FrameLayout 布局
        mTabHost = (FragmentTabHost) this.findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        mTabHost.getTabWidget().setVisibility(View.GONE);//隐藏顶部切换菜单
        for (int i = 0; i < tabs.length; i++) {
            //向 FragmentTabHost 添加标签以及 Fragment 界面
            mTabHost.addTab(mTabHost.newTabSpec(tabs[i]).setIndicator(tabs[i]),
                    cls[i], null);

        }

        //实例化布局按钮控件
        button0 = (RelativeLayout) findViewById(R.id.Button0);
        button1 = (LinearLayout) findViewById(R.id.Button1);
        button3 = (LinearLayout) findViewById(R.id.Button3);
        button2 = (RelativeLayout) findViewById(R.id.Button2);
        //设置监听事件
        this.button0.setOnClickListener(this);
        this.button1.setOnClickListener(this);
        this.button2.setOnClickListener(this);
        this.button3.setOnClickListener(this);

        //这里是实例化显示的提示标签数字
        rlNum1 = (RelativeLayout) findViewById(R.id.rlNum1);
        tvMsgNum = (TextView) findViewById(R.id.tvMsgNum);

        rlNum1.setVisibility(View.GONE); // 设置不可见
//        tvMsgNum.setText("2"); // 可以设置提醒图片的数字

        //设置默认选中标签
        mTabHost.setCurrentTabByTag(tabs[0]);
        lastSelectButton = 1;

        mActionBar = getSupportActionBar();


    }

    @Override
    protected void onStart() {
        super.onStart();
        // 启动广播监听
        mBgChangeReceiver = new BgChangeReceiver();
        IntentFilter intentFilter = new IntentFilter("com.killer.tabhost.fragment4");
        LocalBroadcastManager.getInstance(this).registerReceiver(mBgChangeReceiver,intentFilter);

    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止广播监听
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBgChangeReceiver);
    }

    // 切换页面按钮点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Button0:
                setLayoutButton(1);
                mTabHost.setCurrentTabByTag(tabs[0]);
                break;
            case R.id.Button1:
                setLayoutButton(2);
                mTabHost.setCurrentTabByTag(tabs[1]);
                break;
            case R.id.Button2:
                setLayoutButton(3);
                mTabHost.setCurrentTabByTag(tabs[2]);
                break;

            case R.id.Button3:
                setLayoutButton(4);
                mTabHost.setCurrentTabByTag(tabs[3]);
                break;
        }
    }

    /**
     * 设置点击切换标签字体颜色与背景图片的切换
     *
     * @param selectButton 选择的按钮位置
     */
    private void setLayoutButton(int selectButton) {

        // 将最后选择的按钮复位
        switch (lastSelectButton) {
            case 1:
                image1.setBackgroundResource(R.drawable.tab1_normal);
                text1.setTextColor(ContextCompat.getColor(this, R.color.txt666));
                break;
            case 2:
                image2.setBackgroundResource(R.drawable.tab2_normal);
                text2.setTextColor(ContextCompat.getColor(this, R.color.txt666));
                break;
            case 3:
                image3.setBackgroundResource(R.drawable.tab3_normal);
                text3.setTextColor(ContextCompat.getColor(this, R.color.txt666));
                break;
            case 4:
                image4.setBackgroundResource(R.drawable.tab4_normal);
                text4.setTextColor(ContextCompat.getColor(this, R.color.txt666));
                break;
        }
        // 将选择的按钮改变状态
        switch (selectButton) {
            case 1:
                image1.setBackgroundResource(R.drawable.tab1_light);
                text1.setTextColor(ContextCompat.getColor(this, R.color.txt009eb8));
                break;
            case 2:
                image2.setBackgroundResource(R.drawable.tab2_light);
                text2.setTextColor(ContextCompat.getColor(this, R.color.txt009eb8));
                break;
            case 3:
                image3.setBackgroundResource(R.drawable.tab3_light);
                text3.setTextColor(ContextCompat.getColor(this, R.color.txt009eb8));
                break;
            case 4:
                image4.setBackgroundResource(R.drawable.tab4_light);
                text4.setTextColor(ContextCompat.getColor(this, R.color.txt009eb8));
                break;
        }
        // 将最后选择保存
        lastSelectButton = selectButton;

    }

    // 用于接收fragment1的新消息信息更改UI
    @Override
    public void setMessageNumber(int number) {
        int messageNumber = Integer.parseInt(tvMsgNum.getText().toString());
        tvMsgNum.setText(String.valueOf(messageNumber + number));
        rlNum1.setVisibility(View.VISIBLE);
    }

    private class BgChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                // 从广播中得到更改的颜色代码
                int bg_num = intent.getIntExtra("actionbar_bg_num", 0);

//                Log.i("cc:", String.valueOf(bg_num));
                switch (bg_num) {
                    case 0:
                        mActionBar.setBackgroundDrawable(ContextCompat.getDrawable(context,R.color.colorPrimary));
                        break;
                    case 1:
                        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
                        break;
                    case 2:
                        mActionBar.setBackgroundDrawable(new ColorDrawable(Color.RED));
                        break;

                }
            }
        }
    }


}
