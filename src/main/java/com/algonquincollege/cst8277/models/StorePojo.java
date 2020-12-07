/*****************************************************************c******************o*******v******id********
 * File: StorePojo.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 *
 * update by : Anton Hrytsyk, Vaughan Alex, Patrick Quinty
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import static com.algonquincollege.cst8277.models.StorePojo.ALL_STORES_QUERY_NAME;
import com.algonquincollege.cst8277.rest.ProductSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
*
* Description: model for the Store object
*/
@Entity(name = "Stores")
@Table(name = "STORES")
@AttributeOverride(name = "id", column = @Column(name = "STORE_ID"))
@NamedQuery(name = ALL_STORES_QUERY_NAME, query = "SELECT c FROM Stores c")
public class StorePojo extends PojoBase implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String ALL_STORES_QUERY_NAME = "allStores";
    protected String storeName;
    protected Set<ProductPojo> products = new HashSet<>();

    // JPA requires each @Entity class have a default constructor
    public StorePojo() {
    }

    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    
    @JsonSerialize(using = ProductSerializer.class)
      //Discovered what I think is a bug: you should be able to list them in any order,
      //but it turns out, EclipseLink's JPA implementation needs the @JoinColumn StorePojo's PK
      //first, the 'inverse' to ProductPojo's PK second
    @ManyToMany()
    @JoinTable(
        joinColumns = @JoinColumn(name = "STORE_ID", referencedColumnName = "STORE_ID"),
        inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
    )
    public Set<ProductPojo> getProducts() {
        return products;
    }
    public void setProducts(Set<ProductPojo> products) {
        this.products = products;
    }

}
