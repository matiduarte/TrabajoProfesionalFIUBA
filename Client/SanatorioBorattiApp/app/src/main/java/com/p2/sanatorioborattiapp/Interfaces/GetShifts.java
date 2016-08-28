package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.MedicalShift;

import java.util.List;

/**
 * Created by fran on 28/08/16.
 */
public interface GetShifts {
    public abstract void done(boolean success, List<MedicalShift> shifts);
}
