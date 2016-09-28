package com.killer.tabhost;


import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import uk.co.senab.actionbarpulltorefresh.library.ActionBarPullToRefresh;
import uk.co.senab.actionbarpulltorefresh.library.Options;
import uk.co.senab.actionbarpulltorefresh.library.PullToRefreshLayout;
import uk.co.senab.actionbarpulltorefresh.library.listeners.OnRefreshListener;


/**
 *
 */
public class Fragment1 extends Fragment {

    private TextView mTextView;
    private PullToRefreshLayout mPullToRefreshLayout;

    public Fragment1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        mTextView = (TextView) view.findViewById(R.id.fragment1_textview);

        // 使用第三方库得到下拉刷新效果
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.my_layout);

        // 设置 PullToRefreshLayout
        ActionBarPullToRefresh.from(this.getActivity())
                .options(Options.create().scrollDistance(0.4f).build())
                // 将所有Children加入
                .allChildrenArePullable()
                // 设置 OnRefreshListener
                .listener(new OnRefreshListener() {
                    @Override
                    public void onRefreshStarted(View view) {
                        //此处可以刷新数据,最好是开新线程异步更新

                        new GetDataTask().execute();

                    }
                })

        .setup(mPullToRefreshLayout);


        SpannableString spannableString = new SpannableString("这只是一个TextView!向下滑动可得到当前时间");
        //改变0－6位置的字大小，样式（数字代表数组下标，后面一位不包含该位置，并且汉字在这里面算一个位置）
        spannableString.setSpan(new RelativeSizeSpan(1.8f), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD_ITALIC), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //改变"TextView"字颜色，背景色
        spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), 5, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new BackgroundColorSpan(Color.LTGRAY), 5, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        //改变后面字的大小，字颜色，下划线
        spannableString.setSpan(new RelativeSizeSpan(1.2f), 14, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.RED), 14, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new UnderlineSpan(), 14, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        Drawable d = ContextCompat.getDrawable(getContext(),R.drawable.tab1_normal);
        d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
        ImageSpan imageSpan = new ImageSpan(d,ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(imageSpan,14,15,Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTextView.setText(spannableString);

        return view;
    }

    // 异步更新UI数据
    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            //模拟后台处理数据
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new String[]{Calendar.getInstance().getTime().toString()};
        }

        //
        @Override
        protected void onPostExecute(String[] result) {
            // 更新UI内容并设置刷新结束语句
            mTextView.append("\n" + result[0]);

            mPullToRefreshLayout.setRefreshComplete();
            super.onPostExecute(result);
        }
    }

}
