package com.p2.sanatorioborattiapp.Service;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.p2.sanatorioborattiapp.Entities.Medicine;
import com.p2.sanatorioborattiapp.Entities.Treatment;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.GetDoctorPatients;
import com.p2.sanatorioborattiapp.Interfaces.GetPatientStudies;
import com.p2.sanatorioborattiapp.Interfaces.GetUserMedicines;
import com.p2.sanatorioborattiapp.Interfaces.GetUserTreatments;
import com.p2.sanatorioborattiapp.Interfaces.LoginUser;
import com.p2.sanatorioborattiapp.Interfaces.SaveTreatment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Se encarga de proveer servicios para realizar consultas a la API rest del servidor.
 */
public class Service {

    private static String BASE_URL = "http://192.168.0.21:8086/Server/boratti/";
    //private static String BASE_URL = "http://fierce-river-61114.herokuapp.com/boratti/";
    private String PATIENT_STUDIES_URI = "patientstudies/";
    private String DOCTOR_PATIENTS_URI = "doctorpatients/";
    private String USER_TREATMENTS_URI = "patienttreatments/";
    private String USER_MEDICINES_URI = "medicinesupply/";
    private String USER_LOGIN_URI= "userlogin/";
    private String USERNAME_PARAM = "userName";
    private String PASSWORD_PARAM = "password";
    private String DOCTOR_ID = "doctorId";
    private String PATIEN_ID = "patientId";
    private String OBSERVATIONS = "observations";

    ProgressDialog progressDialog;
    private Context context;

    //KEYS
    private String KEY_SUCCESS = "success";
    private String KEY_MESSAGE = "message";
    private String KEY_DATA = "data";


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
     * Se encarga de obtener los studios de un paciente
     * @param user El usuario al que se van a buscar sus estudios
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
            String url = getBaseUrl(context) + PATIENT_STUDIES_URI + user.getUserId();
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


    public void loginUserInBackground(User user, LoginUser loginUserCallback){
        progressDialog.show();
        executeAsyncTask(new LoginUserAsyncTask(user, loginUserCallback));
    }

    public class LoginUserAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        User user;
        LoginUser loginUserCallback;

        public LoginUserAsyncTask(User user, LoginUser loginUserCallback){
            this.user = user;
            this.loginUserCallback = loginUserCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + USER_LOGIN_URI;
            RestClient client = new RestClient(url);

            client.addParam(USERNAME_PARAM, user.getUserName());
            client.addParam(PASSWORD_PARAM, user.getPassword());
            try {
                client.execute(RestClient.RequestMethod.POST);
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
            int id = 0;
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);

                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);

                id = dataJson.getInt("id");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            loginUserCallback.done(result, id);

            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }


    public void saveTreatmentInBackground(Treatment treatment, SaveTreatment saveTreatmentCallback){
        //progressDialog.show();
        executeAsyncTask(new SaveTreatmentAsyncTask(treatment, saveTreatmentCallback));
    }

    public class SaveTreatmentAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        Treatment treatment;
        SaveTreatment saveTreatmentCallback;

        public SaveTreatmentAsyncTask(Treatment treatment, SaveTreatment saveTreatmentCallback){
            this.treatment = treatment;
            this.saveTreatmentCallback = saveTreatmentCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + USER_TREATMENTS_URI;
            RestClient client = new RestClient(url);

            client.addParam(DOCTOR_ID, String.valueOf(treatment.getDoctorId()));
            client.addParam(PATIEN_ID, String.valueOf(treatment.getPatientId()));
            client.addParam(OBSERVATIONS, treatment.getObservations());
            try {
                client.execute(RestClient.RequestMethod.POST);
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
            //progressDialog.dismiss();
            boolean result = false;
            int id = 0;
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            saveTreatmentCallback.done(result);

            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }


    public void getDoctorPatientsInBackground(User user, GetDoctorPatients getDoctorPatientsCallback){
        progressDialog.show();
        executeAsyncTask(new GetDoctorPatientsAsyncTask(user, getDoctorPatientsCallback));
    }

    public class GetDoctorPatientsAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        User user;
        GetDoctorPatients getDoctorPatientsCallback;

        public GetDoctorPatientsAsyncTask(User user, GetDoctorPatients getDoctorPatientsCallback){
            this.user = user;
            this.getDoctorPatientsCallback = getDoctorPatientsCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + DOCTOR_PATIENTS_URI + user.getUserId();
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
            JSONArray patients = new JSONArray();
            String patientsString = "";
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                patientsString = dataJson.getString("patients");
                patients = new JSONArray(patientsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getDoctorPatientsCallback.done(result, User.getPatients(patients));
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }

    public void getUserTreatmentsInBackground(User user, GetUserTreatments getUserTreatmentsCallback){
        progressDialog.show();
        executeAsyncTask(new GetUserTreatmentsAsyncTask(user, getUserTreatmentsCallback));
    }

    public class GetUserTreatmentsAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        User user;
        GetUserTreatments getUserTreatmentsCallback;

        public GetUserTreatmentsAsyncTask(User user, GetUserTreatments getUserTreatmentsCallback){
            this.user = user;
            this.getUserTreatmentsCallback = getUserTreatmentsCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + USER_TREATMENTS_URI + user.getUserId();
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
            JSONArray treatments = new JSONArray();
            String treatmentsString = "";
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                treatmentsString = dataJson.getString("treatments");
                treatments = new JSONArray(treatmentsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getUserTreatmentsCallback.done(result, Treatment.getTreatments(treatments));
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }

    public void getUserMedicinesInBackground(User user, GetUserMedicines getUserMedicinesCallback){
        progressDialog.show();
        executeAsyncTask(new GetUserMedicinesAsyncTask(user, getUserMedicinesCallback));
    }

    public class GetUserMedicinesAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        User user;
        GetUserMedicines getUserMedicinesCallback;

        public GetUserMedicinesAsyncTask(User user, GetUserMedicines getUserMedicinesCallback){
            this.user = user;
            this.getUserMedicinesCallback = getUserMedicinesCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + USER_MEDICINES_URI + user.getUserId();
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
            JSONArray medicines = new JSONArray();
            String medidinesString = "";
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                medidinesString = dataJson.getString("medicines");
                medicines = new JSONArray(medidinesString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getUserMedicinesCallback.done(result, Medicine.getMedidines(medicines));
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
