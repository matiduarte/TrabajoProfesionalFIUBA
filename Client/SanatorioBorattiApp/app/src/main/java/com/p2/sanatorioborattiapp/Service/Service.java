package com.p2.sanatorioborattiapp.Service;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.GetPatientStudies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Se encarga de proveer servicios para realizar consultas a la API rest del servidor.
 */
public class Service {

    private static String BASE_URL = "http://localhost:8080/boratti/";
    private String PATIENT_STUDIES_URL = "patientstudies/";

    ProgressDialog progressDialog;
    private Context context;

    //KEYS
    private String KEY_SUCCESS = "success";


    private static ArrayList<AsyncTask> currentActiveServices = new ArrayList<AsyncTask>();


    public Service(Context context){
        this.context = context;
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Procesando");
        progressDialog.setMessage("Por favor espere...");
    }

    public void cancelCurrentServices(){
        for (int i = 0; i < currentActiveServices.size(); i++) {
            currentActiveServices.get(i).cancel(true);
        }

        currentActiveServices = new ArrayList<AsyncTask>();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    static public <T> void executeAsyncTask(AsyncTask<T, ?, ?> task, T... params) {
        currentActiveServices.add(task);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        } else {
            task.execute(params);
        }
    }

    /**
     * Se encarga de actualizar la información de perfil del usuario.
     * @param user El usuario con la información nueva a ser actualizada.
     * @param getPatientStudiesCallback La respuesta que trae el serivico del servidor.
     */
    public void getPatientStudiesInBackground(User user, GetPatientStudies getPatientStudiesCallback){
        progressDialog.show();
        executeAsyncTask(new GetPatientStudiesAsyncTask(user, getPatientStudiesCallback));
    }

    public class GetPatientStudiesAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        User user;
        GetPatientStudies getPatientStudiesCallback;

        public GetPatientStudiesAsyncTask(User user, GetPatientStudies getPatientStudiesCallback){
            this.user = user;
            this.getPatientStudiesCallback = getPatientStudiesCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + PATIENT_STUDIES_URL + user.getUserId();
            RestClient client = new RestClient(url);

            try {
                client.execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }

            String response = client.getResponse();
            JSONObject jObject = new JSONObject();
            if (response != null) {
                try {
                    jObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return jObject;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            progressDialog.dismiss();
            boolean result = false;
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getPatientStudiesCallback.done(result);
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }



    public Context getContext() {
        return context;
    }

    public static String getBaseUrl(Context context){
        if(BASE_URL != null){
            return BASE_URL;
        }

        return "";
    }

}
