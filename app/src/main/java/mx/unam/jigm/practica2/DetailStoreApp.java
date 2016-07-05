package mx.unam.jigm.practica2;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import mx.unam.jigm.practica2.model.ModelItem;
import mx.unam.jigm.practica2.service.ServiceNotify;
import mx.unam.jigm.practica2.service.ServiceNotify2;
import mx.unam.jigm.practica2.sql.ItemDataSource;

/**
 * Created by Mario on 29/06/2016.
 */
public class DetailStoreApp extends AppCompatActivity implements View.OnClickListener{
   //para recibir datos
    private String sNameApp;
    private String sDeployment;
    private String sDetail;
    private Integer iResource;
    private int iInstallUpdate;
    private int iditem;
    private Button btnUnistallaInstall,btnOpen,btnUpdate;
    //para mostrar la informacion recibida
    TextView twNameApp,twDeploy,twDetail,twInstallUpdate;
    ImageView  twimg;
    //para las imagenes de picasso
    private final String url1="http://www.posgrado.unam.mx/sites/default/files/el_viaje_de_los_objetos02.jpg";
    private final String url2="http://www.posgrado.unam.mx/sites/default/files/lengua-larga02.jpg";
    static String myURL;
    //para el activity request
    private static final int REQUEST_CODE_INSTALL_APP = 1;
    //para el accesoa la bdd
    private ItemDataSource itemDataSource;
    //para el dialogho del desisntalar
    AlertDialog.Builder dialogo1;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_storeapp);
        //para escuchar los botones de unistall y abrir archivo
        findViewById(R.id.btnDetailStoreUnistall).setOnClickListener(this);
        findViewById(R.id.btnDetailStoreOpen).setOnClickListener(this);
        findViewById(R.id.btnDetailStoreUpdate).setOnClickListener(this);
        btnUnistallaInstall=(Button)findViewById(R.id.btnDetailStoreUnistall);
        btnOpen=(Button)findViewById(R.id.btnDetailStoreOpen);
        btnUpdate=(Button)findViewById(R.id.btnDetailStoreUpdate);
        itemDataSource = new ItemDataSource(getApplicationContext());
        //preparando el dialogo de confirmación
        dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle(R.string.detailstore_confirm_title);
        dialogo1.setMessage(R.string.detailstore_confirm_question);
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton(R.string.detailstore_confirm_confirm, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                acept_desinstall();
            }
        });
        dialogo1.setNegativeButton(R.string.detailstore_confirm_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                //cancelar();
            }
        });
        //definir toolbar
        Toolbar toolbar =(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.txtTitleNotifi);
        getSupportActionBar().setIcon(android.R.drawable.sym_def_app_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        if (getIntent()!=null)
        {
            //obteniendo datos del intent
            iditem=getIntent().getExtras().getInt("idItem");
            sNameApp=getIntent().getExtras().getString("name_app");
            sDeployment=getIntent().getExtras().getString("name_deploy");
            sDetail=getIntent().getExtras().getString("detail_app");
            iResource=getIntent().getExtras().getInt("resouce_id");
            iInstallUpdate=getIntent().getExtras().getInt("install_update");

            //para mostrar los datos en los controles
             twNameApp=(TextView) findViewById(R.id.txtItemNameAPP);
             twDeploy=(TextView) findViewById(R.id.txtItemNameDeployment);
             twDetail=(TextView) findViewById(R.id.txtItemDetailAPP);
             twimg=(ImageView) findViewById(R.id.row_image_view);
             twInstallUpdate=(TextView) findViewById(R.id.txtItemInstalActalizer);
            //mostrando los datos
            twNameApp.setText(sNameApp);
            twDeploy.setText(sDeployment);
            twDetail.setText(sDetail);
             if (iInstallUpdate==1)
             {
                 btnUpdate.setText("UPDATE");
                 btnUpdate.setEnabled(true);
             }
             else
             {
                 btnUpdate.setEnabled(false);
             }
            // obtener la imagen por picasso
            Boolean bimagen=false;
            if (iResource==1)
            {
                myURL=url1;
            }
            else
            {
                myURL=url2;
            }
            Context mcontext = this;
            Picasso.with(mcontext).load(myURL).into(twimg);
        }
        else
        {
            Toast.makeText(getApplicationContext(),getResources().getText(R.string.msj_error_data),Toast.LENGTH_SHORT).show();
        }
    }

    private void acept_desinstall(){
        startService(new Intent(getApplicationContext(), ServiceNotify.class));
        //borrar datos a la bdd
        ModelItem itemlist = new ModelItem();
        itemlist.id=iditem;
        itemDataSource.deleteItem(itemlist);
        //para regresarse activity main
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_detailedit,menu);
        return true;
    }
//para seleccion de las opciones de menu
    @Override
    public boolean onOptionsItemSelected(MenuItem itemmenu) {
        switch (itemmenu.getItemId())
        {
            case R.id.Itemmenuedit:
               //activity edit
                ir();
                return true;
            case R.id.itemMenuReturn:
                //activity main
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(itemmenu);
    }

    //edita de un elemento en la lista de nombre app, deploy
    private void ir()
    {
        Intent intent;
        intent = new Intent(getApplicationContext(), ActivityEdit.class);
        intent.putExtra("idItem",iditem);
        intent.putExtra("name_app", sNameApp);
        intent.putExtra("name_deploy",sDeployment);
        intent.putExtra("install_update", iInstallUpdate);
        startActivityForResult(intent,REQUEST_CODE_INSTALL_APP);
    }
    //regreso activity request del edit
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(REQUEST_CODE_INSTALL_APP==requestCode && resultCode==RESULT_OK){
            //recuperando datos para mostrarlos en los textview
            if (getIntent()!=null) {
                twNameApp.setText(getIntent().getExtras().getString("name_app"));
                twDeploy.setText(getIntent().getExtras().getString("name_deploy"));
                if (getIntent().getExtras().getInt("install_update") == 1) {
                    btnUnistallaInstall.setText("UNINSTALL");
                } else {
                    btnUnistallaInstall.setText("UPDATE");
                }
            }
        }else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
//para irse a una url apartir del boton abrir
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnDetailStoreOpen:
                //para enviar una pagina web desde un boton
                Uri uri = Uri.parse("http://www.posgrado.unam.mx");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                break;
            case R.id.btnDetailStoreUnistall:
                dialogo1.show();
                break;
            case R.id.btnDetailStoreUpdate:
                startService(new Intent(getApplicationContext(), ServiceNotify2.class));
                //actualizar en bdd
                break;
        }

    }
}
