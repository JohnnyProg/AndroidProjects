package com.example.projekt3;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AsyncGetInfo extends AsyncTask<String, Integer, String[]> {

    Activity mActivity;
    public AsyncGetInfo(Activity activity) {
        mActivity = activity;
    }

    @Override
    protected String[] doInBackground(String... strings) {
        String adres_url = strings[0].toString();
        //publishProgress(0);
        int mRozmiar = 0;
        String mTyp = null;
        HttpsURLConnection polaczenie = null;
        try {
            URL url = new URL(adres_url);
            polaczenie = (HttpsURLConnection) url.openConnection();
            polaczenie.setRequestMethod("GET");
            InputStream ans = polaczenie.getInputStream();

            mRozmiar = polaczenie.getContentLength();
            mTyp = polaczenie.getContentType();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (polaczenie != null) polaczenie.disconnect();
        }

        return new String[] {Integer.toString(mRozmiar), mTyp};
    }

    @Override
    protected void onPostExecute(String[] s) {
        super.onPostExecute(s);
        TextView size = mActivity.findViewById(R.id.text_size_result);
        TextView type = mActivity.findViewById(R.id.text_type_result);
        size.setText(s[0]);
        type.setText(s[1]);
    }
}
