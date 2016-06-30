package mx.unam.jigm.practica2;

import android.content.Context;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import mx.unam.jigm.practica2.adapters.AdapterItemList;
import mx.unam.jigm.practica2.model.ModelItem;
import mx.unam.jigm.practica2.sql.ItemDataSource;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listView;
    private ItemDataSource itemDataSource;
    private boolean isWifi;
    private int counter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDataSource = new ItemDataSource(getApplicationContext());
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        get_list();
    }
    private void  get_list()
    {
        listView = (ListView) findViewById(R.id.listItems);
        if (itemDataSource.getAllItems() != null)
        {
            List<ModelItem> modelItemList = itemDataSource.getAllItems();
            isWifi = !(modelItemList.size() % 2 == 0);
            counter = modelItemList.size();
            Context context = this;
            listView.setAdapter(new AdapterItemList(context,modelItemList));
            if (counter == 0) {
                Context mcontext = this;
                Toast.makeText(mcontext, R.string.msjbddempty, Toast.LENGTH_SHORT).show();
            } else {
                Context mcontext = this;
                Toast.makeText(mcontext, R.string.msjwelcom, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Context mcontext = this;
            Toast.makeText(mcontext, R.string.msjbddempty, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn:
                ir();
                break;
        }
    }
    private void ir()
    {
        Intent intent = new Intent(getApplicationContext(), ActivityDetail.class);
        startActivity(intent);
    }
}
