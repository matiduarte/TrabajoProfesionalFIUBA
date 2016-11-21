package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.Bed;
import com.p2.sanatorioborattiapp.Entities.Floor;

import java.util.List;

/**
 * Created by fran on 12/08/16.
 */
public interface GetFloors {
    public abstract void done(boolean success, List<Floor> floors);
}
