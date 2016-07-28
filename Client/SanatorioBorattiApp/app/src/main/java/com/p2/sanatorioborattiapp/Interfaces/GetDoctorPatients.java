package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.User;

import java.util.List;

/**
 * Created by quimey on 31/05/16.
 */

public interface GetDoctorPatients {
    public abstract void done(boolean success, List<User> patients);
}