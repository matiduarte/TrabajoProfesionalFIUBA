package com.p2.sanatorioborattiapp.Activities.Model;


import com.p2.sanatorioborattiapp.Entities.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<Integer, User> getData(List<User> users) {
        HashMap<Integer, User> expandableListDetail = new HashMap<Integer, User>();

        List<String> lisItems = new ArrayList<String>();
        lisItems.add("Tratamiento");
        lisItems.add("Medicacion");
        lisItems.add("Estudios");

        for(int i=0; i < users.size(); i++){
            expandableListDetail.put(i, users.get(i));
        }

        return expandableListDetail;
    }
}