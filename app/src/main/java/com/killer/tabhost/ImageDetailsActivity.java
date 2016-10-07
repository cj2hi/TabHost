package com.killer.tabhost;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.killer.image.ZoomImageView;
import com.killer.util.ImageLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ImageDetailsActivity extends AppCompatActivity {

    private ZoomImageView mZoomImageView;
    private ImageLoader mImageLoader;
    String imagePath;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);

        mZoomImageView = (ZoomImageView) findViewById(R.id.activity_image_details);
        // 得到传过来的图片路径
        imagePath = getIntent().getStringExtra("image_path");
        // 直接读取内存中的图片避免再次下载
        mImageLoader = ImageLoader.getInstance();
        bitmap = mImageLoader.getBitmapFromMemoryCache(imagePath);
        mZoomImageView.setImageBitmap(bitmap);

        //检查权限并取得
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }

        mZoomImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                new AlertDialog.Builder(view.getContext())
                        .setMessage("图片保存在手机SD卡的image目录下")
                        .setNegativeButton("取消",null)
                        .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                    saveImage();
                            }
                        }).create().show();
                return true;
            }
        });
    }

    // 定义右上角菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("保存图片").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                saveImage();
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    // 保存所选图片到SD卡下
    private void saveImage() {

        // 判断是否有SD卡
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path = Environment.getExternalStorageDirectory().toString() + "/image";
            String fileName = imagePath.substring(imagePath.lastIndexOf("/") + 1);
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(path, fileName);
            if (!file.exists()) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    Toast.makeText(ImageDetailsActivity.this,"文件保存成功！",Toast.LENGTH_SHORT).show();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(ImageDetailsActivity.this,"文件已存在！",Toast.LENGTH_SHORT).show();
            }



        }
    }
}
