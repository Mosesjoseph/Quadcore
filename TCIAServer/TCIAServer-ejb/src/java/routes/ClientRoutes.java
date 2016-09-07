/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routes;

import exceptions.InvalidRoute;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import traffic.TrafficRequest;

/**
 *
 * @author mpho
 */
public class ClientRoutes implements RouteProcessor
{
    private final EntityManager entityManager;

    public ClientRoutes(EntityManager entityManager) 
    {
        this.entityManager = entityManager;
    }
    
    @Override
    public void processRoute(TrafficRequest inRequest) throws InvalidRoute 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Long checkForRoute(String route) 
    {
        Long routeID = null;
        
        return routeID;
    }

    @Override
    public Route accessRoute(Long routeID) 
    {
        Route routeFound = null;
        
        Query query = entityManager.createNamedQuery("Find Route by cameraID");
        query.setParameter("id", routeID);
        
        if(!(query.getResultList().isEmpty()))
        {
            routeFound = (Route) query.getSingleResult();
        }
        
        return routeFound;
    }
    
    public void storeRoute(Route inRoute) 
    {
        entityManager.getTransaction().begin();
        entityManager.persist(inRoute);
        entityManager.getTransaction().commit();
    }

    
    
}
