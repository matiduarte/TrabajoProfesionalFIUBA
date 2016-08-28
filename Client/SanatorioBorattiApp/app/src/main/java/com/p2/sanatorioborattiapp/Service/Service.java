package com.p2.sanatorioborattiapp.Service;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.p2.sanatorioborattiapp.Entities.Bed;
import com.p2.sanatorioborattiapp.Entities.MedicalShift;
import com.p2.sanatorioborattiapp.Entities.Medicine;
import com.p2.sanatorioborattiapp.Entities.Study;
import com.p2.sanatorioborattiapp.Entities.Treatment;
import com.p2.sanatorioborattiapp.Entities.User;
import com.p2.sanatorioborattiapp.Interfaces.DeleteUserMedicine;
import com.p2.sanatorioborattiapp.Interfaces.GetAllMedicines;
import com.p2.sanatorioborattiapp.Interfaces.GetAllStudies;
import com.p2.sanatorioborattiapp.Interfaces.GetBeds;
import com.p2.sanatorioborattiapp.Interfaces.GetDoctorPatients;
import com.p2.sanatorioborattiapp.Interfaces.GetFloors;
import com.p2.sanatorioborattiapp.Interfaces.GetPatientStudies;
import com.p2.sanatorioborattiapp.Interfaces.GetShifts;
import com.p2.sanatorioborattiapp.Interfaces.GetUserMedicines;
import com.p2.sanatorioborattiapp.Interfaces.GetUserTreatments;
import com.p2.sanatorioborattiapp.Interfaces.LoginUser;
import com.p2.sanatorioborattiapp.Interfaces.SaveTreatment;
import com.p2.sanatorioborattiapp.Interfaces.SaveUserMedicine;
import com.p2.sanatorioborattiapp.Interfaces.SaveUserStudy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Se encarga de proveer servicios para realizar consultas a la API rest del servidor.
 */
public class Service {

    private static String BASE_URL = "http://192.168.1.106:8080/Server/boratti/";
    //private static String BASE_URL = "http://fierce-river-61114.herokuapp.com/boratti/";
    private String PATIENT_STUDIES_URI = "patientstudies/";
    private String DOCTOR_PATIENTS_URI = "doctorpatients/";
    private String USER_TREATMENTS_URI = "patienttreatments/";
    private String USER_MEDICINES_URI = "medicinesupply/";
    private String USER_LOGIN_URI= "userlogin/";
    private String BED_DIAGRAM_URI = "bedDiagram/";
    private String MEDICAL_SHIFTS_URI = "medicalshift/";
    private String TOTAL_FLOORS_URI = "totalfloors";
    private String USERNAME_PARAM = "userName";
    private String PASSWORD_PARAM = "password";
    private String DOCTOR_ID = "doctorId";
    private String PATIENT_ID = "patientId";
    private String MEDICINE_ID = "medicineId";
    private String PRIORITY = "priority";
    private String STUDY_TYPE = "studyType";
    private String ID = "id";
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
            JSONArray studies = new JSONArray();
            String studiesString = "";
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                studiesString = dataJson.getString("studies");
                studies = new JSONArray(studiesString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getPatientStudiesCallback.done(result, Study.getStudies(studies));
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }


    public void getAllStudiesInBackground(GetAllStudies getAllStudiesCallback){
        progressDialog.show();
        executeAsyncTask(new GetAllStudiesAsyncTask(getAllStudiesCallback));
    }

    public class GetAllStudiesAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        GetAllStudies getAllStudiesCallback;

        public GetAllStudiesAsyncTask(GetAllStudies getAllStudiesCallback){
            this.getAllStudiesCallback = getAllStudiesCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + PATIENT_STUDIES_URI;
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
            JSONArray studies = new JSONArray();
            String studiesString = "";
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                studiesString = dataJson.getString("studies");
                studies = new JSONArray(studiesString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getAllStudiesCallback.done(result, Study.getStudies(studies));
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }

    public void saveUserStudyInBackground(Study study, SaveUserStudy saveUserStudyCallback){
        //progressDialog.show();
        executeAsyncTask(new SaveUserStudyAsyncTask(study, saveUserStudyCallback));
    }

    public class SaveUserStudyAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        Study study;
        SaveUserStudy saveUserStudyCallback;

        public SaveUserStudyAsyncTask(Study study, SaveUserStudy saveUserStudyCallback){
            this.study = study;
            this.saveUserStudyCallback = saveUserStudyCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + PATIENT_STUDIES_URI;
            RestClient client = new RestClient(url);

            client.addParam(DOCTOR_ID, String.valueOf(study.getDoctorId()));
            client.addParam(PATIENT_ID, String.valueOf(study.getPatientId()));
            client.addParam(STUDY_TYPE, String.valueOf(study.getType()));
            client.addParam(PRIORITY, String.valueOf(study.getPriority()));
            client.addParam(OBSERVATIONS, String.valueOf(study.getObservations()));
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

            saveUserStudyCallback.done(result);

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
            client.addParam(PATIENT_ID, String.valueOf(treatment.getPatientId()));
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

    public void deleteUserMedicineInBackground(Medicine medicine, DeleteUserMedicine deleteUserMedicineCallback){
        progressDialog.show();
        executeAsyncTask(new DeleteUserMedicineAsyncTask(medicine, deleteUserMedicineCallback));
    }

    public class DeleteUserMedicineAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        Medicine medicine;
        DeleteUserMedicine deleteUserMedicineCallback;

        public DeleteUserMedicineAsyncTask(Medicine medicine, DeleteUserMedicine deleteUserMedicineCallback){
            this.medicine = medicine;
            this.deleteUserMedicineCallback = deleteUserMedicineCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + USER_MEDICINES_URI + "delete";
            RestClient client = new RestClient(url);
            client.addParam(ID, String.valueOf(medicine.getId()));
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
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            deleteUserMedicineCallback.done(result);
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }

    public void getAllMedicinesInBackground(GetAllMedicines getAllMedicinesCallback){
        progressDialog.show();
        executeAsyncTask(new GetAllMedicinesAsyncTask(getAllMedicinesCallback));
    }

    public class GetAllMedicinesAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        GetAllMedicines getAllMedicinesCallback;

        public GetAllMedicinesAsyncTask(GetAllMedicines getAllMedicinesCallback){
            this.getAllMedicinesCallback = getAllMedicinesCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + USER_MEDICINES_URI;
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

            getAllMedicinesCallback.done(result, Medicine.getMedidines(medicines));
            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }

    public void saveUserMedicineInBackground(Medicine medicine, SaveUserMedicine saveUserMedicineCallback){
        //progressDialog.show();
        executeAsyncTask(new SaveUserMedicineAsyncTask(medicine, saveUserMedicineCallback));
    }

    public class SaveUserMedicineAsyncTask extends AsyncTask<Void, Void, JSONObject>{
        Medicine medicine;
        SaveUserMedicine saveUserMedicineCallback;

        public SaveUserMedicineAsyncTask(Medicine medicine, SaveUserMedicine saveUserMedicineCallback){
            this.medicine = medicine;
            this.saveUserMedicineCallback = saveUserMedicineCallback;
        }

        @Override
        protected JSONObject doInBackground(Void... params) {
            String url = getBaseUrl(context) + USER_MEDICINES_URI;
            RestClient client = new RestClient(url);

            client.addParam(DOCTOR_ID, String.valueOf(medicine.getDoctorId()));
            client.addParam(PATIENT_ID, String.valueOf(medicine.getPatientId()));
            client.addParam(MEDICINE_ID, String.valueOf(medicine.getMedicineId()));
            client.addParam(OBSERVATIONS, medicine.getObservations());
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

            saveUserMedicineCallback.done(result);

            super.onPostExecute(jsonObject);
        }

        @Override
        protected void onPreExecute(){

        }
    }

    public void getBeds(int floor, GetBeds getFloorsCallbaack) {
        progressDialog.show();
        executeAsyncTask(new getBedsAsyncTask(floor, getFloorsCallbaack));
    }

    public class getBedsAsyncTask extends AsyncTask<Void,Void,JSONObject> {

        GetBeds getFloorsCallback;
        int floor;

        public getBedsAsyncTask(int floor, GetBeds getFloorsCallbaack) {
            this.getFloorsCallback = getFloorsCallbaack;
            this.floor = floor;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String url = getBaseUrl(context) + BED_DIAGRAM_URI + floor;
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
            JSONArray beds = new JSONArray();
            String bedsString = "";
            JSONArray patients = new JSONArray();
            String patientsString = "";
            String image = "";
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                bedsString = dataJson.getString("beds");
                beds = new JSONArray(bedsString);
                patientsString = dataJson.getString("patients");
                patients = new JSONArray(patientsString);
                image = dataJson.getString("image");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getFloorsCallback.done(result, Bed.getBeds(beds,patients),floor,image);
            super.onPostExecute(jsonObject);
        }
    }

    public void getFloors(GetFloors getFloorsCallbaack) {
        progressDialog.show();
        executeAsyncTask(new getFloorsAsyncTask(getFloorsCallbaack));
    }

    public class getFloorsAsyncTask extends AsyncTask<Void,Void,JSONObject> {

        GetFloors getFloorsCallback;

        public getFloorsAsyncTask( GetFloors getFloorsCallbaack) {
            this.getFloorsCallback = getFloorsCallbaack;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String url = getBaseUrl(context) + BED_DIAGRAM_URI + TOTAL_FLOORS_URI;
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
            int total = 0;
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                total = dataJson.getInt("floors");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getFloorsCallback.done(result,total);
            super.onPostExecute(jsonObject);
        }
    }

    public void getShifts(int doctorId, String date, GetShifts getShiftsCallbaack) {
        progressDialog.show();
        executeAsyncTask(new getShiftsAsyncTask(doctorId, date, getShiftsCallbaack));
    }

    public class getShiftsAsyncTask extends AsyncTask<Void,Void,JSONObject> {

        GetShifts getShiftsCallbaack;
        int doctorId;
        String date;

        public getShiftsAsyncTask(int doctorId, String date, GetShifts getShiftsCallbaack) {
            this.getShiftsCallbaack = getShiftsCallbaack;
            this.doctorId = doctorId;
            this.date = date;
        }

        @Override
        protected JSONObject doInBackground(Void... voids) {
            String url = getBaseUrl(context) + MEDICAL_SHIFTS_URI + doctorId + "/" + date;
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
            JSONArray shifts = new JSONArray();
            String shiftsString = "";
            try {
                result = jsonObject.getBoolean(KEY_SUCCESS);
                String data;
                data = jsonObject.getString(KEY_DATA);
                JSONObject dataJson = new JSONObject(data);
                shiftsString = dataJson.getString("shifts");
                shifts = new JSONArray(shiftsString);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            getShiftsCallbaack.done(result, MedicalShift.getShifts(shifts));
            super.onPostExecute(jsonObject);
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
