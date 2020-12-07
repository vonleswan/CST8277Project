/*****************************************************************c******************o*******v******id********
 * File: BillingAddressPojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 *
 * update by : Anton Hrytsyk, Vaughan Alex, Patrick Quinty
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
public class BillingAddressPojo extends AddressPojo implements Serializable {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    protected boolean isAlsoShipping;

    // JPA requires each @Entity class have a default constructor
    public BillingAddressPojo() {
    }

    public boolean isAlsoShipping() {
        return isAlsoShipping;
    }
    public void setAlsoShipping(boolean isAlsoShipping) {
        this.isAlsoShipping = isAlsoShipping;
    }
    
}