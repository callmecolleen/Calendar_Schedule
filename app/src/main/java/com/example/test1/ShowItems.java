package com.example.test1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class ShowItems extends AppCompatActivity {

    private List<Item> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_items);

        initItems();

        ItemAdapter adapter = new ItemAdapter(ShowItems.this, R.layout.item, itemList);
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Item item = itemList.get(position);
                Intent intent = new Intent(ShowItems.this, EoDActivity.class);
                String noedit_id = String.valueOf(item.getId());
                intent.putExtra("Id", noedit_id);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initItems(){
        List<Item> items = DataSupport.findAll(Item.class);
        for(Item item:items){
            Item item1 = new Item(item.getId(), item.getIt_year(), item.getIt_month(), item.getIt_day(),
                    item.getAlarm_hour(), item.getAlarm_min(), item.getAlarm_sec(),item.getMy_item()/*, item.getAlarm()*/);
            itemList.add(item1);
        }
    }

    //  设置目录
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.Calendar_item:
                Intent intent = new Intent(ShowItems.this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.Todo_item:
                Toast.makeText(this, "你已经在日程界面！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.DelectAll:
                DataSupport.deleteAll(Item.class);
                Intent intent2 = new Intent(ShowItems.this, ShowItems.class);
                startActivity(intent2);
                finish();
                break;
            default:
        }
        return true;
    }
}

