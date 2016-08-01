package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.Treatment;

import java.util.List;

/**
 * Created by quimey on 31/05/16.
 */

public interface GetUserTreatments {
    public abstract void done(boolean success, List<Treatment> patients);
}