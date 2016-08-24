package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.Bed;

import java.util.List;

/**
 * Created by fran on 21/08/16.
 */
public interface GetBeds {
    public abstract void done(boolean success, List<Bed> bedList, int currentFloor, String image);
}
