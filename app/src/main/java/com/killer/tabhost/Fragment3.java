package com.killer.tabhost;


import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.killer.adapter.SimpleRecyclerCardAdapter;
import com.killer.image.Images;
import com.killer.util.ImageLoader;
import com.killer.util.Utils;

import java.util.ArrayList;
import java.util.Locale;


/**
 *广告条展示，瀑布流布局
 */
public class Fragment3 extends Fragment implements BannerClickListener {


    private View mView;
    private BannerFlipper mBannerFlipper; //自定义广告条
    private TextView tv_flipper;

    private RecyclerView mRecyclerView; // 原生效果控件
//    private ScrollRecyclerView mRecyclerView; // 此为自定义可和其他控件一起滚动作用
    private SimpleRecyclerCardAdapter mSimpleRecyclerAdapter;

    private ImageLoader mImageLoader = ImageLoader.getInstance();


    public Fragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_fragment3, container, false);
        }

        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initBannerFlipper(view); // 初始化广告条内容

        initDataAndView(view);// 初始化瀑布墙
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initDataAndView(View view) {

        if (mRecyclerView == null) {

            mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment3_recyclerview);

            mSimpleRecyclerAdapter = new SimpleRecyclerCardAdapter(view.getContext(), Images.imageUrls);
            //设置网格布局管理器
            mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
            mRecyclerView.setAdapter(mSimpleRecyclerAdapter);
            mSimpleRecyclerAdapter.setOnItemActionListener(new SimpleRecyclerCardAdapter.OnItemActionListener() {

                @Override
                public boolean onItemLongClickListener(View v, int pos) {
                    return false;
                }

                @Override
                public void onItemClickListener(View v, int pos) {

                    if (mImageLoader.getBitmapFromMemoryCache(Images.imageUrls[pos]) != null) {
                        // 单独的Activity来显示图片
                        Intent intent = new Intent(v.getContext(), ImageDetailsActivity.class);
                        intent.putExtra("image_path", Images.imageUrls[pos]);

                        v.getContext().startActivity(intent);
                    }
                }
            });


        }
    }


    private void initBannerFlipper(View view) {

        if (mBannerFlipper == null) {

            mBannerFlipper = (BannerFlipper) view.findViewById(R.id.banner_pager);
            tv_flipper = (TextView) view.findViewById(R.id.tv_flipper);

            ViewGroup.LayoutParams params = mBannerFlipper.getLayoutParams();
            Point point = Utils.getSize(view.getContext());
            params.height = (int) (point.x * 250f / 640f); // 根据banner图片大小比例调整高度
            mBannerFlipper.setLayoutParams(params);

            // 加入图片数据
            ArrayList<Integer> bannerArray = new ArrayList<>();
            bannerArray.add(R.drawable.banner_1);
            bannerArray.add(R.drawable.banner_2);
            bannerArray.add(R.drawable.banner_3);
            bannerArray.add(R.drawable.banner_4);
            bannerArray.add(R.drawable.banner_5);
            mBannerFlipper.setImage(bannerArray);

            // 设置点击监听
            mBannerFlipper.setOnBannerListener(this);
        }
    }

    // 点击广告监听
    @Override
    public void onBannerClick(int position) {
        String desc = String.format(Locale.CHINA, "您点击了第%d张图片", position + 1);
        tv_flipper.setText(desc);
    }


}
