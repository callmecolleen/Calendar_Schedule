package com.example.test1;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ShowableListMenu;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddItems extends AppCompatActivity {

    final Calendar mCalendar = Calendar.getInstance();

    private Button add_Pic;

    private Button confirm_add;

    private Button cancel;

    private EditText your_item;
    private ImageView your_pic;

    private String added_item;

    private Bitmap new_add_image;
    private byte[] images;


    private Spinner select_yy;
    private Spinner select_mon;
    private Spinner select_dd;

    private String mYear;
    private String mMonth;
    private String mDay;

    private Spinner select_hh;
    private Spinner select_min;
    private Spinner select_ss;

    private String mHour;
    private String mMin;
    private String mSec;

    /*private CheckBox alarm_or_not;*/
    /*private boolean alarm;*/

    private List<String> day_list;
    private ArrayAdapter<String> arr_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        confirm_add = (Button) findViewById(R.id.confirm_add);
        cancel = (Button) findViewById(R.id.cancel);
        add_Pic = (Button) findViewById(R.id.choose_from_album);
        /*alarm_or_not = (CheckBox) findViewById(R.id.alarm);*/

        your_item = (EditText) findViewById(R.id.edit_item);
        your_pic = (ImageView) findViewById(R.id.add_picture);

        select_yy = (Spinner) findViewById(R.id.select_year);
        select_mon = (Spinner) findViewById(R.id.select_month);
        select_dd = (Spinner) findViewById(R.id.select_day);
        select_hh = (Spinner) findViewById(R.id.select_hour);
        select_min = (Spinner) findViewById(R.id.select_min);
        select_ss = (Spinner) findViewById(R.id.select_sec);

        images = null; //  添加图片默认为空
        added_item = null;
        //  long time=System.currentTimeMillis();
        //  mCalendar.setTimeInMillis(time);

        //  下拉菜单默认时间设置为当前时间
        mHour = String.valueOf(mCalendar.get(Calendar.HOUR_OF_DAY));
        setSpinnerItemSelectedByValue(select_hh, mHour);
        mMin = String.valueOf(mCalendar.get(Calendar.MINUTE));
        setSpinnerItemSelectedByValue(select_min, mMin);
        mSec = String.valueOf(mCalendar.get(Calendar.SECOND));
        setSpinnerItemSelectedByValue(select_ss, mSec);

        Intent intent = getIntent();

        //  从MainActivity获得当前选中的年、月、日
        mYear = intent.getStringExtra("now_Year");
        setSpinnerItemSelectedByValue(select_yy, mYear);
        mMonth = intent.getStringExtra("now_Month");
        setSpinnerItemSelectedByValue(select_mon, mMonth);
        mDay = intent.getStringExtra("now_Day");
        setDayList(day_list);

        select_yy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mYear = select_yy.getSelectedItem().toString();
                //  根据新选择的年重新设置日
                setDayList(day_list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        select_mon.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMonth = select_mon.getSelectedItem().toString();
                //  根据新选择的月重新设置日
                setDayList(day_list);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        select_dd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mDay = select_dd.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        select_hh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mHour = select_hh.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select_min.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMin = select_min.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        select_ss.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSec = select_ss.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //  按钮点击事件
        //  添加图片
        add_Pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(AddItems.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(AddItems.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    openAlbum();
                }
            }
        });

        //  点击确认添加日程
        confirm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  获取edit的字符串
                added_item = your_item.getText().toString();

                //  空日程处理——异常
                if (added_item.equals("")) {
                    /*ShowDialog();*/
                    added_item = "空白项";
                }
                //   获取复选框状态
                /*alarm_or_not.setOnCheckedChangeListener(new AlarmOnCheckedChangeListener());*/

                Item item = new Item();

                item.setIt_year(Integer.parseInt(mYear));
                item.setIt_month(Integer.parseInt(mMonth));
                item.setIt_day(Integer.parseInt(mDay));
                item.setAlarm_hour(Integer.parseInt(mHour));
                item.setAlarm_min(Integer.parseInt(mMin));
                item.setAlarm_sec(Integer.parseInt(mSec));
                /*item.setAlarm(alarm);*/
                //  item.setId();
                item.setMy_item(added_item);
                item.setNew_image(images);

                item.save();
                Intent intent1 = new Intent(AddItems.this, ShowItems.class);
                startActivity(intent1);
                finish();
            }
        });

        //  点击取消
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(AddItems.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }
        });

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

            your_pic.setImageBitmap(bitmap);
        } else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }
    }

    /*private class AlarmOnCheckedChangeListener implements CompoundButton.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(CompoundButton button, boolean isChecked) {
            if (alarm_or_not.isChecked()) {
                alarm = true;
            } else {
                alarm = false;
            }
        }
    }*/

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
        select_dd.setAdapter(arr_adapter);
        setSpinnerItemSelectedByValue(select_dd, mDay);
    }

    //Android数据库中存取图片通常使用两种方式，一种是保存图片所在路径，二是将图片以二进制的形式存储（sqlite3支持BLOB数据类型)

    //  把图片转换为字节
    private byte[] img(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    //  空日程提示对话框
    /*private void ShowDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddItems.this);
        builder.setTitle("提示");
        builder.setMessage("日程不能为空!");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }*/
}
