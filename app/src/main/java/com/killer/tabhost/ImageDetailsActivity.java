package com.killer.tabhost;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.killer.image.ZoomImageView;
import com.killer.util.ImageLoader;

public class ImageDetailsActivity extends AppCompatActivity {

    private ZoomImageView mZoomImageView;
    private ImageLoader mImageLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        mZoomImageView = (ZoomImageView) findViewById(R.id.activity_image_details);
        // 得到传过来的图片路径
        String imagePath = getIntent().getStringExtra("image_path");
        // 直接读取内存中的图片避免再次下载
        mImageLoader = ImageLoader.getInstance();
        Bitmap bitmap = mImageLoader.getBitmapFromMemoryCache(imagePath);
        mZoomImageView.setImageBitmap(bitmap);
    }
}
