package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.Study;

import java.util.List;

/**
 * Created by quimey on 31/05/16.
 */

public interface GetAllStudies {
    public abstract void done(boolean success, List<Study> patients);
}