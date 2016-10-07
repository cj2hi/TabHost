package com.killer.tabhost;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.killer.util.QRCodeUtil;
import com.killer.zxing.RGBLuminanceSource2;

import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


/**
 *
 */
public class Fragment4 extends Fragment implements View.OnClickListener {

    private static final int CHOOSE_PIC = 0;
    private static final int PHOTO_PIC = 1;
    private String imgPath;

    private Spinner mSpinner;
    private String[] dataArray = {"蓝色", "黑色", "红色"};
    private View mView;
    private ImageView mImageView; // 二维码图片
    private EditText mEditTextUrl; // 输入生成二维码的内容
    private Button mButtonCreate, mButtonBitmapDecode,mButtonCaptureDecode; // 点击生成二维码

    public Fragment4() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_fragment4, container, false);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getSpinner(view);

        // 生成二维码图片
        mImageView = (ImageView) view.findViewById(R.id.fragment4_imageview);
        mEditTextUrl = (EditText) view.findViewById(R.id.fragment4_editurl);
        mButtonCreate = (Button) view.findViewById(R.id.fragment4_button_create);
        mButtonBitmapDecode = (Button) view.findViewById(R.id.fragment4_button_bitmap_decode);
        mButtonCaptureDecode = (Button) view.findViewById(R.id.fragment4_button_capture_decode);

        mButtonCreate.setOnClickListener(this);
        mButtonBitmapDecode.setOnClickListener(this);
        mButtonCaptureDecode.setOnClickListener(this);


    }

    private void getSpinner(View view) {
        mSpinner = (Spinner) view.findViewById(R.id.fragment4_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext()
                , R.layout.spinner_item, dataArray);

        mSpinner.setPrompt("请选择标题栏颜色：");
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent("com.killer.tabhost.fragment4");
                intent.putExtra("actionbar_bg_num", i);
                // 本地广播要用本地广播收听
                LocalBroadcastManager.getInstance(Fragment4.this.getContext()).sendBroadcast(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }

        });
    }

    // 所有点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment4_button_create:
                // 得到输入的内容
                String url = mEditTextUrl.getText().toString();
                if (!"".equals(url)) {
                    try {
                        Bitmap bitmapQc = QRCodeUtil.createQRCode(url, 450);
                        mImageView.setImageBitmap(bitmapQc);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                } else {
                    Snackbar.make(view, "内容不能为空！", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.fragment4_button_bitmap_decode:

//                Bitmap bitmap = ((BitmapDrawable)mImageView.getDrawable()).getBitmap();
//                handleQRCodeFormPhoto(bitmap);

                //跳转到图片选择界面去选择一张二维码图片
                Intent intent1 = new Intent();
                intent1.setAction(Intent.ACTION_PICK);

                intent1.setType("image/*");

                Intent intent2 = Intent.createChooser(intent1, "选择二维码图片");
                startActivityForResult(intent2, CHOOSE_PIC);
                break;
            case R.id.fragment4_button_capture_decode:
                Intent intent3 = new Intent(getContext(), MipcaActivityCapture.class);
                startActivityForResult(intent3, PHOTO_PIC);
                break;
        }
    }


    //解析二维码图片,返回结果封装在Result对象中
    private com.google.zxing.Result parseQRcodeBitmap(String bitmapPath) {
        //解析转换类型UTF-8
        Map<DecodeHintType, String> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        //获取到待解析的图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        //如果我们把inJustDecodeBounds设为true，那么BitmapFactory.decodeFile(String path, Options opt)
        //并不会真的返回一个Bitmap给你，它仅仅会把它的宽，高取回来给你
        options.inJustDecodeBounds = true;
        //此时的bitmap是null，这段代码之后，options.outWidth 和 options.outHeight就是我们想要的宽和高了
        Bitmap bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //我们现在想取出来的图片的边长（二维码图片是正方形的）设置为400像素
        /**
         options.outHeight = 400;
         options.outWidth = 400;
         options.inJustDecodeBounds = false;
         bitmap = BitmapFactory.decodeFile(bitmapPath, options);
         */

        //以上这种做法，虽然把bitmap限定到了我们要的大小，但是并没有节约内存，如果要节约内存，我们还需要使用inSimpleSize这个属性
        options.inSampleSize = (int) (options.outHeight / 400F);
        if (options.inSampleSize <= 0) {
            options.inSampleSize = 1; //防止其值小于或等于0
        }
        /**
         * 辅助节约内存设置
         *
         * options.inPreferredConfig = Bitmap.Config.ARGB_4444;    // 默认是Bitmap.Config.ARGB_8888
         * options.inPurgeable = true;
         * options.inInputShareable = true;
         */
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(bitmapPath, options);
        //得到图片的宽高
//        int width = bitmap.getWidth();
//        int height = bitmap.getHeight();
//        Log.i("wh:", width + "|" + height);
//        //得到图片的像素
//        int[] pixels = new int[width * height];
        //新建一个RGBLuminanceSource对象，将bitmap图片传给此对象
        RGBLuminanceSource2 rgbLuminanceSource = new RGBLuminanceSource2(bitmap);
        //将图片转换成二进制图片
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(rgbLuminanceSource));
        //初始化解析对象
        QRCodeReader reader = new QRCodeReader();
        //开始解析
        Result result = null;
        try {
            result = reader.decode(binaryBitmap, hints);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imgPath = null;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHOOSE_PIC:
                    String[] proj = new String[]{MediaStore.Images.Media.DATA};
                    Cursor cursor = Fragment4.this.getActivity().getContentResolver()
                            .query(data.getData(), proj, null, null, null);

                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                        System.out.println(columnIndex);
                        //获取到用户选择的二维码图片的绝对路径
                        imgPath = cursor.getString(columnIndex);
                    }
                    cursor.close();
                    Log.i("path", imgPath);

                    //获取解析结果
                    Result ret = parseQRcodeBitmap(imgPath);
                    if (ret != null) {

                        Toast.makeText(getContext(), "解析结果：" + ret.toString(), Toast.LENGTH_LONG).show();
                    }else{
                        Snackbar.make(getView(),"无法解析此图片",Snackbar.LENGTH_SHORT).show();
                    }
                    break;
                case PHOTO_PIC:
                    String result = data.getExtras().getString("result");
                    Toast.makeText(getContext(), "解析结果：" + result, Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }

    }
}
