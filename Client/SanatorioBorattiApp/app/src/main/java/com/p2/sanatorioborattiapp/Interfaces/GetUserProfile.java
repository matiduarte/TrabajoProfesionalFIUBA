package com.p2.sanatorioborattiapp.Interfaces;

import com.p2.sanatorioborattiapp.Entities.User;

/**
 * Created by quimey on 31/05/16.
 */

public interface GetUserProfile {
    public abstract void done(boolean success, User user);
}