/*****************************************************************c******************o*******v******id********
 * File: CustomIdentityStoreJPAHelper.java
 * Course materials (20F) CST 8277
 *
 * @author (original) Mike Norman
 *
 * update by : Anton Hrytsyk 040938383
 */
package com.algonquincollege.cst8277.security;

import static com.algonquincollege.cst8277.models.SecurityUser.SECURITY_USER_BY_NAME_QUERY;
import static com.algonquincollege.cst8277.utils.MyConstants.PARAM1;
import static com.algonquincollege.cst8277.utils.MyConstants.PU_NAME;
import static java.util.Collections.emptySet;

import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.algonquincollege.cst8277.models.SecurityRole;
import com.algonquincollege.cst8277.models.SecurityUser;

/*
 * Stateless Session bean should also be a Singleton
 */
@Stateless
@Singleton
public class CustomIdentityStoreJPAHelper {
    public static final String CUSTOMER_PU = "20f-groupProject-PU";

    @PersistenceContext(name = CUSTOMER_PU)
    protected EntityManager em;

    /**
     * Find user by name
     * @param username to search for
     * @return found user
     */
    public SecurityUser findUserByName(String username) {
        SecurityUser user = null;
        try {
            TypedQuery<SecurityUser> securityUserTypedQuery = em.createNamedQuery(SECURITY_USER_BY_NAME_QUERY, SecurityUser.class);
            securityUserTypedQuery.setParameter(PARAM1, username);
            user = securityUserTypedQuery.getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * search for a role
     * @param username role name
     * @return found role
     */
    public Set<String> findRoleNamesForUser(String username) {
        Set<String> roleNames = emptySet();
        SecurityUser securityUser = findUserByName(username);
        if (securityUser != null) {
            roleNames = securityUser.getRoles().stream().map(s -> s.getRoleName()).collect(Collectors.toSet());
        }
        return roleNames;
    }
    
    /**
     * persist security user
     * @param user security user
     */
    @Transactional
    public void saveSecurityUser(SecurityUser user) {
        em.persist(user);
    }
    
    /**
     * Persist security role
     * @param role
     */
    @Transactional
    public void saveSecurityRole(SecurityRole role) {
        em.persist(role);
    }
}