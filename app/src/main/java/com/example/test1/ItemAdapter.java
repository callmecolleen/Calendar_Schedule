package com.example.test1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;


public class ItemAdapter extends ArrayAdapter<Item> {
    private  int resourceId;

    public ItemAdapter(Context context, int textViewResourceId,
                         List<Item> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Item item = getItem(position);//  获取当前项的School实例
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();

            //  viewHolder.showId = (TextView) view.findViewById(R.id.id);
            viewHolder.showYear = (TextView)view.findViewById(R.id.Year);
            viewHolder.showMon = (TextView)view.findViewById(R.id.Month);
            viewHolder.showDay = (TextView)view.findViewById(R.id.Day);
            viewHolder.showHour = (TextView)view.findViewById(R.id.Hour);
            viewHolder.showMin = (TextView)view.findViewById(R.id.Min);
            viewHolder.showSec= (TextView)view.findViewById(R.id.Sec);
            viewHolder.showItem = (TextView)view.findViewById(R.id.specific_item);
            /*viewHolder.showImage = (ImageView)view.findViewById(R.id.small_image);*/
            view.setTag(viewHolder); //  将viewHolder存储在View中
        }
        else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag(); //  重新获取ViewHolder
        }

        //  viewHolder.showId.setText(String.valueOf(item.getId()));
        int is = item.getId();
        viewHolder.showYear.setText(String.valueOf(item.getIt_year()));
        viewHolder.showMon.setText(String.valueOf(item.getIt_month()));
        viewHolder.showDay.setText(String.valueOf(item.getIt_day()));
        viewHolder.showHour.setText(String.valueOf(item.getAlarm_hour()));
        viewHolder.showMin.setText(String.valueOf(item.getAlarm_min()));
        viewHolder.showSec.setText(String.valueOf(item.getAlarm_sec()));
        viewHolder.showItem.setText(item.getMy_item());

        /*byte[]images = item.getNew_image();
        if(images != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(images,0,images.length);
            viewHolder.showImage.setImageBitmap(bitmap);
        }*/
        return view;
    }

    class ViewHolder{
        //  TextView showId;
        TextView showYear;
        TextView showMon;
        TextView showDay;
        TextView showHour;
        TextView showMin;
        TextView showSec;
        TextView showItem;
        /*ImageView showImage;*/
    }
}
