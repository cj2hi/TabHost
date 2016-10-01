package com.killer.tabhost;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.killer.tabhost.pagewidget.PageWidget;
import com.killer.tabhost.pagewidget.PageWidgetAdapter;
import com.killer.tabhost.pagewidget.PageWidgetAdapter1;


/**
 *使用翻页插件，模拟书本翻页效果
 */
public class Fragment2 extends Fragment {

    private PageWidget mPageWidget;// 翻页插件
    private SwipeRefreshLayout mSwipeRefreshLayout; //下拉刷新控件
    private boolean isSetA; // 使用适配器判断


    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        mPageWidget = (PageWidget) view.findViewById(R.id.pagewidget);

        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

        mSwipeRefreshLayout.setColorSchemeColors(Color.BLUE,Color.GREEN,Color.BLACK);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                // 模拟加载，真实情况不用延时使用
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(isSetA){
                            PageWidgetAdapter adapter = new PageWidgetAdapter(getContext());
                            mPageWidget.setAdapter(adapter);
                            isSetA = false;
                        }else{
                            PageWidgetAdapter1 adapter1 = new PageWidgetAdapter1(getContext());
                            mPageWidget.setAdapter(adapter1);
                            isSetA = true;
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                },3000);


            }
        });

        // 简单图片翻页
//        PageWidgetAdapter adapter = new PageWidgetAdapter(view.getContext());
//        mPageWidget.setAdapter(adapter);
        // 模拟图书翻页效果
        PageWidgetAdapter1 adapter1 = new PageWidgetAdapter1(view.getContext());
        mPageWidget.setAdapter(adapter1);
        isSetA = true;


        return view;
    }

}
