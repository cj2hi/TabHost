package com.killer.tabhost.pagewidget;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.killer.tabhost.R;

public class PageWidgetAdapter extends BaseAdapter {
	private final static String TAG = "PageWidgetAdapter";

	private Context mContext;
	private int count;
	private LayoutInflater inflater;
	private Integer[] imgs = { R.drawable.scene1, R.drawable.scene2, R.drawable.scene3,
			  R.drawable.scene4, R.drawable.scene5, R.drawable.scene6};
	
	public PageWidgetAdapter(Context context) {
		Log.d(TAG, "PageWidgetAdapter");
		mContext = context;
		inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		count = imgs.length;
	}
	@Override
	public int getCount() {
		return count;
	}

	@Override
	public Object getItem(int position) {
		return imgs[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Log.d(TAG, "getView position="+position);
		ViewGroup layout;
		if(convertView == null) {
			layout = (ViewGroup) inflater.inflate(R.layout.item_page, null);
		} else {
			layout = (ViewGroup) convertView;
		}
		//position有可能超出范围
		if (position>=0 && position < imgs.length) {
			setViewContent(layout, position);
		}
		
		return layout;
	}
	
	private void setViewContent(ViewGroup group, int position) {
		TextView tv_txt = (TextView) group.findViewById(R.id.tv_pagenum);
		tv_txt.setText(String.valueOf(position+1));
		ImageView iv_img = (ImageView) group.findViewById(R.id.iv_img);
		iv_img.setImageResource(imgs[position]);
	}

}
