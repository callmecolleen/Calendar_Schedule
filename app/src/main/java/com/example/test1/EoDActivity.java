package com.example.test1;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class EoDActivity extends AppCompatActivity {

    private EditText change_item;
    private Spinner change_year;
    private Spinner change_month;
    private Spinner change_day;
    private Spinner change_hour;
    private Spinner change_min;
    private Spinner change_sec;
    private ImageView change_image;

    private String mYear;
    private String mMonth;
    private String mDay;
    private String mHour;
    private String mMin;
    private String mSec;

    private byte[] images;

    private List<String> day_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eo_d);

        change_item = (EditText) findViewById(R.id.change_item);
        change_year = (Spinner) findViewById(R.id.change_year);
        change_month = (Spinner) findViewById(R.id.change_month);
        change_day = (Spinner) findViewById(R.id.change_day);
        change_hour = (Spinner) findViewById(R.id.change_hour);
        change_min = (Spinner) findViewById(R.id.change_min);
        change_sec = (Spinner) findViewById(R.id.change_sec);
        change_image = (ImageView) findViewById(R.id.change_picture);
        Button add_Pic = (Button) findViewById(R.id.choose_from_album);

        Button change = (Button) findViewById(R.id.change);
        Button delete = (Button) findViewById(R.id.delete);

        Intent intent = getIntent();
        final String getId = intent.getStringExtra("Id");

        List<Item> items = DataSupport.where("id = ?", getId).find(Item.class);
        for (Item item : items) {
            change_item.setText(item.getMy_item());
            mYear = String.valueOf(item.getIt_year());
           setSpinnerItemSelectedByValue(change_year, mYear);
           mMonth = String.valueOf(item.getIt_month());
           setSpinnerItemSelectedByValue(change_month, mMonth);
           mDay = String.valueOf(item.getIt_day());
           setDayList(day_list);
           mHour = String.valueOf(item.getAlarm_hour());
           setSpinnerItemSelectedByValue(change_hour, mHour);
           mMin =  String.valueOf(item.getAlarm_min());
           setSpinnerItemSelectedByValue(change_min,mMin);
           mSec = String.valueOf(item.getAlarm_sec());
           setSpinnerItemSelectedByValue(change_sec,mSec );
            images = item.getNew_image();
            if(images != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
                change_image.setImageBitmap(bitmap);
            }
        }

        change_year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mYear = change_year.getSelectedItem().toString();
                //  根据新选择的年重新设置日
                setDayList(day_list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        change_month.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMonth = change_month.getSelectedItem().toString();
                //  根据新选择的月重新设置日
                setDayList(day_list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        change_day.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDay = change_day.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        change_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mHour = change_hour.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        change_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMin = change_min.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        change_sec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSec = change_sec.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item item = new Item();
                item.setMy_item(change_item.getText().toString());
                item.setIt_year(Integer.parseInt(mYear));
                item.setIt_month(Integer.parseInt(mMonth));
                item.setIt_day(Integer.parseInt(mDay));
                item.setAlarm_hour(Integer.parseInt(mHour));
                item.setAlarm_min(Integer.parseInt(mMin));
                item.setAlarm_sec(Integer.parseInt(mSec));
                item.setNew_image(images);
                item.updateAll("id = ? ",getId);
                Intent intent1 = new Intent(EoDActivity.this, ShowItems.class);
                startActivity(intent1);
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Item.class, "id = ?", getId);
                Intent intent2 = new Intent(EoDActivity.this, ShowItems.class);
                startActivity(intent2);
                finish();
            }
        });

        add_Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(EoDActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(EoDActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });
    }

    //  获取打开文件
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "拒绝权限将无法使用程序", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    //  打开相册
    private void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2); // 打开相册
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= 19) {
                // 4.4及以上系统使用这个方法处理图片
                handleImageOnKitKat(data);
            } else {
                // 4.4以下系统使用这个方法处理图片
                handleImageBeforeKitKat(data);
            }
        }
    }


    //  图片处理
    @TargetApi(19)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("TAG", "handleImageOnKitKat: uri is " + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果是content类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // 如果是file类型的Uri，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath); // 根据图片路径显示图片
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri, null);
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    private void displayImage(String imagePath) {
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            //  把图片转化为字节流
            images = img(bitmap);

            change_image.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    //  把图片转换为字节
    private byte[] img(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //  设置下拉列表的默认值
    public static void setSpinnerItemSelectedByValue(Spinner spinner, String value) {

        //  int m = 0;
        SpinnerAdapter apsAdapter = spinner.getAdapter(); //得到SpinnerAdapter对象
        int k = apsAdapter.getCount();
        for (int i = 0; i < k; i++) {
            if (value.equals(apsAdapter.getItem(i).toString())) {
                spinner.setSelection(i, true);// 默认选中项
                break;
            }
        }
    }

    //  根据年月设置日的下拉菜单数目
    private void setDayList(List<String> day_list) {
        day_list = new ArrayList<>();
        int y = Integer.parseInt(mYear);
        int m = Integer.parseInt(mMonth);
        if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
            for (int i = 1; i <= 31; i++) {
                day_list.add(String.valueOf(i));
            }
        } else if (m == 2) {
            if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
                for (int i = 1; i <= 29; i++) {
                    day_list.add(String.valueOf(i));
                }
            } else {
                for (int i = 1; i <= 28; i++) {
                    day_list.add(String.valueOf(i));
                }
            }
        } else {
            for (int i = 1; i <= 30; i++) {
                day_list.add(String.valueOf(i));
            }
        }
        arr_adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, day_list);
        change_day.setAdapter(arr_adapter);
        setSpinnerItemSelectedByValue(change_day, mDay);
    }
}

