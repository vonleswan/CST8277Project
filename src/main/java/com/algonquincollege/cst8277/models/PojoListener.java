/*****************************************************************c******************o*******v******id********
 * File: PojoListener.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : Vaughan Alexander 040937187
 */
package com.algonquincollege.cst8277.models;

import java.time.LocalDateTime;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class PojoListener {
    @PrePersist
    public void setCreatedOnDate(PojoBase pojo){
        LocalDateTime now = LocalDateTime.now();
        pojo.setCreatedDate(now);
        pojo.setUpdatedDate(now);
    }

    @PreUpdate
    public void setUpdatedOnDate(PojoBase pojo){
        pojo.setUpdatedDate(LocalDateTime.now());
    }
}