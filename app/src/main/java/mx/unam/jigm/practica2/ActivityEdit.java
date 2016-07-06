package mx.unam.jigm.practica2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import mx.unam.jigm.practica2.model.ModelItem;
import mx.unam.jigm.practica2.sql.ItemDataSource;

/**
 * Created by Mario on 01/07/2016.
 */
public class ActivityEdit extends AppCompatActivity implements View.OnClickListener {
    //para recibir datos
    private int iditem;
    private String sNameApp;
    private String sDeployment;
    private Integer iInstallUpdate;
    //para asignnar valores de objetos xml
    private EditText txtnameapp;
    private EditText txtdeployment;
    private CheckBox chkinstallaupdate;
    //para el accesoa la bdd
    private ItemDataSource itemDataSource;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //para defir el xml a usar
        setContentView(R.layout.activity_edit);
        if (getIntent()!=null)
        {
            //obteniendo datos del intent
            iditem=getIntent().getExtras().getInt("idItem");
            sNameApp=getIntent().getExtras().getString("name_app");
            sDeployment=getIntent().getExtras().getString("name_deploy");
            iInstallUpdate=getIntent().getExtras().getInt("install_update");
        }
        itemDataSource = new ItemDataSource(getApplicationContext());
        //para asignar los elementos del xml a variables java
        txtnameapp = (EditText) findViewById(R.id.NameAPP);
        txtdeployment=(EditText)findViewById(R.id.NameDeployment);
        chkinstallaupdate=(CheckBox) findViewById(R.id.chkInstallUpdate);
        findViewById(R.id.btnEditDetail).setOnClickListener(this);
        //mostrando los datos
        txtnameapp.setText(sNameApp);
        txtdeployment.setText(sDeployment);
        if (iInstallUpdate==1)
        {
            chkinstallaupdate.setSelected(true);
            chkinstallaupdate.setEnabled(false);
        }
        else
        {
            chkinstallaupdate.setSelected(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnEditDetail:
                //guardar e ir de regreso
                  saveUpData();
                  break;

        }
    }

    private void saveUpData()
    {
        //asigna a variables locales valores de los datos capturados
        String nameappsave = txtnameapp.getText().toString();
        String namedeploysave=txtdeployment.getText().toString();
        Integer chkinstallupdate;
        if (chkinstallaupdate.isChecked()) {
            //1 cuando ya fue instalado
            chkinstallupdate=1;
        }
        else
        {
            //0 cuando no ha sido instalado
            chkinstallupdate=0;
        }

        //valida campos llenos
        if (!TextUtils.isEmpty(nameappsave) && !TextUtils.isEmpty(namedeploysave))
        {
            //guardar datos a la bdd
            ModelItem itemlist = new ModelItem();
            itemlist.id=iditem;
            itemlist.nameapp= nameappsave;
            itemlist.deployment=namedeploysave;
            itemlist.InstalUpdate=chkinstallupdate;
            //guardar datos en db
            itemDataSource.updateItemList(itemlist);
            //inicializar textview y checkbox
            txtnameapp.setText("");
            txtdeployment.setText("");
            chkinstallaupdate.setSelected(false);
            Intent intent = new Intent(getApplicationContext(), DetailStoreApp.class);
            intent.putExtra("idItem", itemlist.id);
            intent.putExtra("name_app", itemlist.nameapp);
            intent.putExtra("name_deploy", itemlist.deployment);
            intent.putExtra("install_update", itemlist.InstalUpdate);
            setResult(RESULT_OK,intent);
            startActivity(intent);
            //
        }
    }
}
