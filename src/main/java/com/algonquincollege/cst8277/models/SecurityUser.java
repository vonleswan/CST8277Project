/*****************************************************************c******************o*******v******id********
 * File: SecurityUser.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 * 
 * update by : I. Am. A. Student 040nnnnnnn
 */
package com.algonquincollege.cst8277.models;

import java.io.Serializable;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import com.algonquincollege.cst8277.rest.SecurityRoleSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.models.SecurityUser.USER_FOR_OWNING_CUST_QUERY;

/**
 * User class used for (JSR-375) Java EE Security authorization/authentication
 */
@Entity(name = "SecurityUser")
@Table(name = "SECURITY_USER")
@NamedQueries({
        @NamedQuery(name = USER_FOR_OWNING_CUST_QUERY, query = "SELECT user FROM SecurityUser user WHERE user.customer.id = :param1"),
        @NamedQuery(name = SECURITY_USER_BY_NAME_QUERY, query = "SELECT user FROM SecurityUser user WHERE user.username = :param1")
})
public class SecurityUser implements Serializable, Principal {
    /** explicit set serialVersionUID */
    private static final long serialVersionUID = 1L;

    public static final String USER_FOR_OWNING_CUST_QUERY =
        "userForOwningCust";
    public static final String SECURITY_USER_BY_NAME_QUERY =
        "userByName";

    protected int id;
    protected String username;
    protected String pwHash;
    protected Set<SecurityRole> roles = new HashSet<>();
    protected CustomerPojo cust;

    public SecurityUser() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPwHash() {
        return pwHash;
    }
    public void setPwHash(String pwHash) {
        this.pwHash = pwHash;
    }
    
    @JsonInclude(Include.NON_NULL)
    @JsonSerialize(using = SecurityRoleSerializer.class)
    @ManyToMany(targetEntity = SecurityRole.class, cascade = CascadeType.PERSIST)
    @JoinTable(name="SECURITY_USER_SECURITY_ROLE",
            joinColumns=@JoinColumn(name="USER_ID", referencedColumnName="USER_ID"),
            inverseJoinColumns=@JoinColumn(name="ROLE_ID", referencedColumnName="ROLE_ID"))
    public Set<SecurityRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<SecurityRole> roles) {
        this.roles = roles;
    }

    @OneToOne
    @JoinColumn(name = "CUST_ID")
    public CustomerPojo getCustomer() {
        return cust;
    }
    public void setCustomer(CustomerPojo cust) {
        this.cust = cust;
    }

    //Principal
    @JsonIgnore
    @Override
    public String getName() {
        return getUsername();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SecurityUser other = (SecurityUser)obj;
        return id == other.id;
    }

}