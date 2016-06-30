package mx.unam.jigm.practica2;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Mario on 29/06/2016.
 */
public class DetailStoreApp extends AppCompatActivity implements View.OnClickListener{
    private String sNameApp;
    private String sDeployment;
    private String sDetail;
    private Integer iResource;
    private Integer iInstallUpdate;
    private final String url1="http://www.posgrado.unam.mx/sites/default/files/el_viaje_de_los_objetos02.jpg";
    private final String url2="http://www.posgrado.unam.mx/sites/default/files/cepe-tucson02.jpg";

    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_storeapp);
        //para escuchar los botones de unistall y abrir archivo
        findViewById(R.id.btnDetailStoreUnistall).setOnClickListener(this);
        findViewById(R.id.btnDetailStoreOpen).setOnClickListener(this);
        Toast.makeText(getApplicationContext(),getResources().getText(R.string.msj_error_data),Toast.LENGTH_SHORT).show();
        if (getIntent()!=null)
        {
            //obteniendo datos del intent
            sNameApp=getIntent().getExtras().getString("name_app");
            sDeployment=getIntent().getExtras().getString("name_deploy");
            sDetail=getIntent().getExtras().getString("detail_app");
            iResource=getIntent().getExtras().getInt("resouce_id");
            iInstallUpdate=getIntent().getExtras().getInt("install_update");
            //para mostrar los datos en los controles
            TextView twNameApp=(TextView) findViewById(R.id.txtItemNameAPP);
            TextView twDeploy=(TextView) findViewById(R.id.txtItemNameDeployment);
            TextView twDetail=(TextView) findViewById(R.id.txtItemDetailAPP);
            ImageView twimg=(ImageView) findViewById(R.id.row_image_view);
            TextView twInstallUpdate=(TextView) findViewById(R.id.txtItemInstalActalizer);
            //mostrando los datos
            twNameApp.setText(sNameApp);
            twDeploy.setText(sDeployment);
            twDetail.setText(sDetail);
            // obtener la imagen por picasso
            Boolean bimagen=false;
            if (iResource==1)
            {
                bimagen=true;
            }
            else
            {
                bimagen=false;
            }
            Context mcontext = this;
            Picasso.with(mcontext).load(bimagen?url1:url2).into(twimg);
            //para cambiar el boton de unistall a update
           // if (iInstallUpdate==1)
           // {
           //     txtinstallupdate.setText(R.string.txtInstallList);
           // }
           // else
           // {
           //     txtinstallupdate.setText(R.string.txtUpdateList);
           // }

        }
        else
        {
            Toast.makeText(getApplicationContext(),getResources().getText(R.string.msj_error_data),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View v) {

    }
}
