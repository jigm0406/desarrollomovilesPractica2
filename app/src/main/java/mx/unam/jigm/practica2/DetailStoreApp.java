package mx.unam.jigm.practica2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
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

import mx.unam.jigm.practica2.service.ServiceNotify;

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
    private Button btnUnistallaInstall,btnOpen;
    //para mostrar la informacion recibida
    TextView twNameApp,twDeploy,twDetail,twInstallUpdate;
    ImageView  twimg;
    //para las imagenes de picasso
    private final String url1="http://www.posgrado.unam.mx/sites/default/files/el_viaje_de_los_objetos02.jpg";
    private final String url2="http://www.posgrado.unam.mx/sites/default/files/lengua-larga02.jpg";
    static String myURL;
    //para el activity request
    private static final int REQUEST_CODE_INSTALL_APP = 1;
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_storeapp);
        //para escuchar los botones de unistall y abrir archivo
        findViewById(R.id.btnDetailStoreUnistall).setOnClickListener(this);
        findViewById(R.id.btnDetailStoreOpen).setOnClickListener(this);
        btnUnistallaInstall=(Button)findViewById(R.id.btnDetailStoreUnistall);
        btnOpen=(Button)findViewById(R.id.btnDetailStoreOpen);
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
                 btnUnistallaInstall.setText("UNINSTALL");
             }
             else
             {
                 btnUnistallaInstall.setText("UPDATE");
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
                startService(new Intent(getApplicationContext(), ServiceNotify.class));
                break;
        }

    }
}
