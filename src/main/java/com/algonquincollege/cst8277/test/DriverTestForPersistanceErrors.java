/**
 *  update by : Anton Hrytsyk, Vaughan Alex, Patrick Quinty
 */
package com.algonquincollege.cst8277.test;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class DriverTestForPersistanceErrors {
    public static final String PU_NAME = "20f-groupProject-PU";

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(PU_NAME);
        emf.close();
    }
}
