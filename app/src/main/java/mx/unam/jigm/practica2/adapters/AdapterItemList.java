package mx.unam.jigm.practica2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import mx.unam.jigm.practica2.R;
import mx.unam.jigm.practica2.model.ModelItem;

/**
 * Created by Mario on 27/06/2016.
 */
public class AdapterItemList extends ArrayAdapter<ModelItem>
{
    private final String url1="http://www.posgrado.unam.mx/sites/default/files/el_viaje_de_los_objetos02.jpg";
    private final String url2="http://www.posgrado.unam.mx/sites/default/files/cepe-tucson02.jpg";

    public AdapterItemList(Context context, List <ModelItem> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list,parent,false);
        }
        TextView txtItemNameApp= (TextView) convertView.findViewById(R.id.txtItemNameAPP);
        TextView txtItemnamedesarroller = (TextView) convertView.findViewById(R.id.txtItemNameDeployment);
        ImageView img = (ImageView) convertView.findViewById(R.id.row_image_view);
        TextView txtDetailApp = (TextView) convertView.findViewById(R.id.txtItemDetailAPP);
        TextView txtinstallupdate = (TextView) convertView.findViewById(R.id.txtItemInstalActalizer);
        ModelItem modelItem=getItem(position);

        Boolean bimagen=false;
        if (modelItem.resourceId==1)
        {
            bimagen=true;
        }
        else
        {
            bimagen=false;
        }
        Picasso.with(getContext()).load(bimagen?url1:url2).into(img);
        txtItemnamedesarroller.setText(modelItem.deployment);
        txtItemNameApp.setText(modelItem.nameapp);
         txtDetailApp.setText(modelItem.detailapp);
        if (modelItem.InstalUpdate==1)
        {
            txtinstallupdate.setText(R.string.txtInstallList);
        }
        else
        {
            txtinstallupdate.setText(R.string.txtUpdateList);
        }
       return convertView;
    }
}
