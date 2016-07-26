package com.p2.sanatorioborattiapp.Activities.Model;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData() {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();

        List<String> lisItems = new ArrayList<String>();
        lisItems.add("Tratamiento");
        lisItems.add("Medicacion");
        lisItems.add("Estudios");

        expandableListDetail.put("Juan Perez", lisItems);
        expandableListDetail.put("Alberto Suarez", lisItems);
        expandableListDetail.put("Lionel Messi", lisItems);
        expandableListDetail.put("Gerard Pique", lisItems);
        expandableListDetail.put("Cristiano Ronaldo", lisItems);
        expandableListDetail.put("Juan Roman Riquelme", lisItems);
        expandableListDetail.put("Fran Nery", lisItems);
        expandableListDetail.put("Matias Duarte", lisItems);
        expandableListDetail.put("Arturo Servetto", lisItems);

        return expandableListDetail;
    }
}