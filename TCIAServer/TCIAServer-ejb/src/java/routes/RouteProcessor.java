/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routes;

import exceptions.InvalidRoute;
import traffic.TrafficRequest;

/**
 *
 * @author Hlengekile
 */
public interface RouteProcessor 
{
   public void processRoute(TrafficRequest trafficRequest) throws InvalidRoute;
   
   public Long checkForRoute(String route);
   
   public Route accessRoute(Long routeID);
   
}
