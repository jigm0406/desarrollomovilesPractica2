package mx.unam.jigm.practica2.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import mx.unam.jigm.practica2.DetailStoreApp;
import mx.unam.jigm.practica2.R;

/**
 * Created by Mario on 03/07/2016.
 */
public class ServiceNotify extends Service {
    private MyAsyncTask myAsyncTask;
    private int id;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getExtras()!=null && intent.getExtras().containsKey("Key_id"))
        {
            id=intent.getExtras().getInt("key_id");
        }
        if(myAsyncTask==null)
        {
            myAsyncTask=new MyAsyncTask();
            myAsyncTask.execute();
        }
        return START_STICKY;
    }

    private class MyAsyncTask extends AsyncTask<Integer,Integer,Boolean>
    {
        private NotificationCompat.Builder mNotifi;

        @Override
        protected void onPreExecute()
        {
            mNotifi = (NotificationCompat.Builder) new NotificationCompat
                    .Builder(getApplicationContext())
                    .setContentTitle("Instalando")
                    .setContentText("istalando APP")
                    .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_file_cloud_done))
                    .setSmallIcon(android.R.drawable.ic_dialog_email);
        }
        @Override
        protected Boolean doInBackground(Integer... params)
        {
            for (int i=0;i<6;i++)
            {
                publishProgress(i);
                try
                {
                    Thread.sleep(1000*1);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                    return false;
                }
            }
            return true;
        }

        @Override
        protected void onProgressUpdate(Integer... values)
        {
            mNotifi.setProgress(6,values[0],false);

            NotificationManager manager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);

            manager.notify(id,mNotifi.build());
        }

        @Override
        protected void onPostExecute(Boolean result)
        {
            if(result)
            {
                //elimina progress cuando lo seteamos a 0
                mNotifi.setProgress(0,0,false);
                mNotifi.setContentTitle("desinstalación completa");
                mNotifi.setContentText("se ha completado en su totalidad la desinstalación");
                mNotifi.setContentInfo("Desinstalado");
                mNotifi.setAutoCancel(true);
                mNotifi.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("se ha completado la desinstalación."));
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                        0,new Intent(getApplicationContext(),
                                DetailStoreApp.class),PendingIntent.FLAG_UPDATE_CURRENT);
                mNotifi.setContentIntent(pendingIntent);

                //PendingIntent piService=PendingIntent.getService(getApplicationContext(),1,
                //        new Intent(getApplicationContext(),ServiceNotify.class)
                //        .putExtra("key_id",id+1)
                //,PendingIntent.FLAG_UPDATE_CURRENT);
                //mNotifi.addAction(android.R.drawable.ic_input_add,"instalar otra vez"
                //        ,piService);
                NotificationManager manager =(NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);
                manager.notify(id,mNotifi.build());
            }

            myAsyncTask=null;
            stopSelf();
        }

    }
}