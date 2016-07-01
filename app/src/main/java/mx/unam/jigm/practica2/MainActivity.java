package mx.unam.jigm.practica2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import mx.unam.jigm.practica2.adapters.AdapterItemList;
import mx.unam.jigm.practica2.model.ModelItem;
import mx.unam.jigm.practica2.sql.ItemDataSource;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ItemDataSource itemDataSource;
    private int counter;
    private int ModNumber;
   //para el activity request
    private static final int REQUEST_CODE_INSTALL_APP = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemDataSource = new ItemDataSource(getApplicationContext());
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ir();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        get_list();
    }

    //obtener lista de la bdd
    private void  get_list()
    {
        listView = (ListView) findViewById(R.id.listItems);
        if (itemDataSource.getAllItems() != null)
        {
            List<ModelItem> modelItemList = itemDataSource.getAllItems();
             counter = modelItemList.size();
            android.content.Context context = this;
            listView.setAdapter(new AdapterItemList(context,modelItemList));
            if (counter == 0)
            {
                Context mcontext = this;
                Toast.makeText(mcontext, R.string.msjbddempty, Toast.LENGTH_SHORT).show();
            } else
            {
                Context mcontext = this;
                Toast.makeText(mcontext, R.string.msjwelcom, Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Context mcontext = this;
            Toast.makeText(mcontext, R.string.msjbddempty, Toast.LENGTH_SHORT).show();
        }
        //para la edicion de elemento en la lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                AdapterItemList adapterlist = (AdapterItemList)  parent.getAdapter();
                ModelItem modelItem = adapterlist.getItem(position);
                Intent intent = new Intent(getApplicationContext(), DetailStoreApp.class);
                intent.putExtra("name_app", modelItem.nameapp);
                intent.putExtra("name_deploy", modelItem.deployment);
                intent.putExtra("detail_app", modelItem.detailapp);
                intent.putExtra("resouce_id", modelItem.resourceId);
                intent.putExtra("install_update", modelItem.InstalUpdate);
                startActivity(intent);
            }
        });
    }

//alta de u elemento en la lista
    private void ir()
    {
        Intent intent;
        intent = new Intent(getApplicationContext(), ActivityDetail.class);
        ModNumber=counter%2;
        intent.putExtra("nImage", ModNumber);
        startActivityForResult(intent,REQUEST_CODE_INSTALL_APP);
    }
    //regreso activity request
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_CODE_INSTALL_APP==requestCode && resultCode==RESULT_OK){
           get_list();
        }else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
