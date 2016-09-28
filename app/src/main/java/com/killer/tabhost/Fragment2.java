package com.killer.tabhost;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.killer.tabhost.pagewidget.PageWidget;
import com.killer.tabhost.pagewidget.PageWidgetAdapter1;


/**
 *使用翻页插件，模拟书本翻页效果
 */
public class Fragment2 extends Fragment {

    private PageWidget mPageWidget;// 翻页插件


    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);
        mPageWidget = (PageWidget) view.findViewById(R.id.pagewidget);

        // 简单图片翻页
//        PageWidgetAdapter adapter = new PageWidgetAdapter(view.getContext());
//        mPageWidget.setAdapter(adapter);
        // 模拟图书翻页效果
        PageWidgetAdapter1 adapter1 = new PageWidgetAdapter1(view.getContext());
        mPageWidget.setAdapter(adapter1);


        return view;
    }

}
