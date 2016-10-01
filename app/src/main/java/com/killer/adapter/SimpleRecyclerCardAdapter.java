package com.killer.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.killer.tabhost.R;
import com.killer.util.ImageLoader;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SimpleRecyclerCardAdapter  extends RecyclerView.Adapter<SimpleCardViewHolder>{

	private Context mCtx;
	private LayoutInflater mInflater;
	private final String[] mImageDatas;
	private OnItemActionListener mOnItemActionListener;
	private ImageLoader imageLoader; //使用工具类通过内存加载图片
	Bitmap bitmap; // 默认图片
	
	public SimpleRecyclerCardAdapter(Context mCtx, String[] imagedatas) {
		super();
		this.mCtx = mCtx;
		mInflater = LayoutInflater.from(mCtx);
		this.mImageDatas = imagedatas;
		imageLoader = ImageLoader.getInstance();
		bitmap = BitmapFactory.decodeResource(mCtx.getResources(),R.mipmap.ic_launcher);
	}
	@Override
	public int getItemCount() {
		return mImageDatas.length;
	}
	@Override
	public void onBindViewHolder(final SimpleCardViewHolder viewHolder,  int i) {

		String imageUrl= mImageDatas[i];
		Bitmap mBitmap = imageLoader.getBitmapFromMemoryCache(imageUrl);
		String imageName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
		viewHolder.itemTv.setText(imageName);
		if (mBitmap != null){
			viewHolder.itemIv.setImageBitmap(mBitmap);
		}else if (cancelPotentialTask(imageUrl,viewHolder.itemIv)){
			//执行下载操作
			DownLoadTask task = new DownLoadTask(viewHolder.itemIv);
			AsyncDrawable asyncDrawable = new AsyncDrawable(mCtx.getResources(),bitmap,task);
			viewHolder.itemIv.setImageDrawable(asyncDrawable);
			task.execute(imageUrl);
		}

		if(mOnItemActionListener!=null)
		{
			viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				       //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
					mOnItemActionListener.onItemClickListener(v,viewHolder.getLayoutPosition());
				}
			});
			viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
				 //注意这里必须使用viewHolder.getPosition()而不能用i，因为为了保证动画，没有使用NotifyDatasetChanged更新位置数据
					return mOnItemActionListener.onItemLongClickListener(v, viewHolder.getPosition());
				}
			});
		}
	}

	@Override
	public SimpleCardViewHolder onCreateViewHolder(ViewGroup viewgroup, int i) {
		
		View v =  mInflater.inflate(R.layout.cardlayout, viewgroup,false);
		SimpleCardViewHolder simpleViewHolder = new SimpleCardViewHolder(v);
//		simpleViewHolder.setIsRecyclable(true);
		
		return simpleViewHolder;
	}
	/**********定义点击事件**********/
	public   interface OnItemActionListener
	{
		 void onItemClickListener(View v,int pos);
		 boolean onItemLongClickListener(View v,int pos);
	}
	public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
		this.mOnItemActionListener = onItemActionListener;
	}

	/**
	 * 检查复用的ImageView中是否存在其他图片的下载任务，如果存在就取消并且返回ture 否则返回 false
	 * @param imageUrl
	 * @param imageView
	 * @return
	 */
	private boolean cancelPotentialTask(String imageUrl, ImageView imageView) {
		DownLoadTask task = getDownLoadTask(imageView);
		if (task != null) {
			String url = task.url;
			if (url == null || !url .equals(imageUrl)){
				task.cancel(true);
			}else{
				return false;
			}
		}
		return true;
	}
	/**
	 * 获取当前ImageView 的图片下载任务
	 * @param imageView
	 * @return
	 */
	private DownLoadTask getDownLoadTask(ImageView imageView){
		if (imageView != null){
			Drawable drawable  = imageView.getDrawable();
			if (drawable instanceof AsyncDrawable ){
				return  ((AsyncDrawable) drawable).getDownLoadTaskFromAsyncDrawable();
			}
		}
		return null;
	}
	/**
	 * 异步加载图片
	 * DownLoadTash 和 ImagaeView建立弱引用关联。
	 */
	class DownLoadTask extends AsyncTask<String ,Void,Bitmap> {
		String url;
		private WeakReference<ImageView> imageViewWeakReference;

		public DownLoadTask(ImageView imageView) {
			imageViewWeakReference = new WeakReference<>(imageView);
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			url = params[0];
			Bitmap bitmap = downLoadBitmap(url);
//			BitmapDrawable drawable = new BitmapDrawable(mCtx.getResources(), bitmap);
			imageLoader.addBitmapToMemoryCache(url, bitmap);
			return bitmap;
		}

		/**
		 * 验证ImageView 中的下载任务是否相同 如果相同就返回
		 *
		 * @return
		 */
		private ImageView getAttachedImageView() {
			ImageView imageView = imageViewWeakReference.get();
			if (imageView != null) {
				DownLoadTask task = getDownLoadTask(imageView);
				if (this == task) {
					return imageView;
				}
			}
			return null;
		}

		/**
		 * 下载图片 这里使用google 推荐使用的OkHttp
		 *
		 * @param url
		 * @return
		 */
		private Bitmap downLoadBitmap(String url) {
			Bitmap bitmap = null;
			OkHttpClient client = new OkHttpClient.Builder()
					.connectTimeout(5000, TimeUnit.SECONDS)
					.build();

			Request request = new Request.Builder().url(url).build();
			try {
				Response response = client.
						newCall(request)
						.execute();
				if(response.isSuccessful())
				bitmap = BitmapFactory.decodeStream(response.body().byteStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap1) {
			super.onPostExecute(bitmap1);
			ImageView imageView = getAttachedImageView();
			if ( imageView != null && bitmap1 != null){
				imageView.setImageBitmap(bitmap1);
			}
		}

	}

	/**
	 * 新建一个类 继承BitmapDrawable
	 * 目的： BitmapDrawable 和DownLoadTask建立弱引用关联
	 */
	class AsyncDrawable extends  BitmapDrawable{
		private  WeakReference<DownLoadTask> downLoadTaskWeakReference;

		public AsyncDrawable(Resources resources, Bitmap bitmap, DownLoadTask downLoadTask){
			super(resources,bitmap);
			downLoadTaskWeakReference = new WeakReference<>(downLoadTask);
		}

		private DownLoadTask getDownLoadTaskFromAsyncDrawable(){
			return downLoadTaskWeakReference.get();
		}
	}
}
class SimpleCardViewHolder extends RecyclerView.ViewHolder
{
	 TextView itemTv;
	 ImageView itemIv;

	 SimpleCardViewHolder(View layout) {
		super(layout);
		itemTv = (TextView) layout.findViewById(R.id.item_title);
		itemIv = (ImageView) layout.findViewById(R.id.item_img);
	}

}

