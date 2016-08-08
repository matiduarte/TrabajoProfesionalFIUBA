package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.Medicine;

import java.util.List;

/**
 * Created by quimey on 31/05/16.
 */

public interface GetAllMedicines {
    public abstract void done(boolean success, List<Medicine> patients);
}