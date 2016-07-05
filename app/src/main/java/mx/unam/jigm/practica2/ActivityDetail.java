package mx.unam.jigm.practica2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

import mx.unam.jigm.practica2.model.ModelItem;
import mx.unam.jigm.practica2.sql.ItemDataSource;

/**
 * Created by Mario on 27/06/2016.
 */
public class ActivityDetail extends AppCompatActivity implements View.OnClickListener {
   //para asignnar valores de objetos xml
    private EditText txtnameapp;
    private EditText txtdeployment;
    private EditText txtdetailapp;
    private CheckBox chkinstallaupdate;
    private boolean isWifi;
    private ItemDataSource itemDataSource;
    private int nImage;

    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //para defir el xml a usar
        setContentView(R.layout.activity_detail);
        //para obtener la imagen aleatoriemente
        if (getIntent()!=null)
        {
            //obteniendo datos del intent
            nImage=getIntent().getExtras().getInt("nImage");
        }
        itemDataSource = new ItemDataSource(getApplicationContext());
        //para asignar los elementos del xml a variables java
        txtnameapp = (EditText) findViewById(R.id.NameAPP);
        txtdeployment=(EditText)findViewById(R.id.NameDeployment);
        txtdetailapp=(EditText) findViewById(R.id.DetailApp);
        chkinstallaupdate=(CheckBox) findViewById(R.id.chkInstallUpdate);
        findViewById(R.id.btnAdd).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnAdd:
                savedata();
                break;
        }
    }

    private void savedata()
    {
         //asigna a variables locales valores de los datos capturados
        String nameappsave = txtnameapp.getText().toString();
        String namedeploysave=txtdeployment.getText().toString();
        String detailappsave= txtdetailapp.getText().toString();
        Integer chkinstallupdate;
        if (chkinstallaupdate.isChecked()) {
            //1 cuando ya fue instalado
            chkinstallupdate=0;
        }
        else
        {
            //0 cuando no ha sido instalado
            chkinstallupdate=1;
        }

        //valida campos llenos
        if (!TextUtils.isEmpty(nameappsave) && !TextUtils.isEmpty(namedeploysave) && !TextUtils.isEmpty(detailappsave))
        {
            //guardar datos a la bdd
            ModelItem itemlist = new ModelItem();
            itemlist.nameapp= nameappsave;
            itemlist.deployment=namedeploysave;
            itemlist.detailapp=detailappsave;
            itemlist.InstalUpdate=chkinstallupdate;
            itemlist.resourceId=nImage;//isWifi?0:1;
              //guardar datos en db
            itemDataSource.saveItemList(itemlist);
            //inicializar textview y checkbox
            txtnameapp.setText("");
            txtdeployment.setText("");
            txtdetailapp.setText("");
            chkinstallaupdate.setSelected(false);
            Intent i = new Intent();
             setResult(RESULT_OK,i);
             finish();
        }
    }


}
