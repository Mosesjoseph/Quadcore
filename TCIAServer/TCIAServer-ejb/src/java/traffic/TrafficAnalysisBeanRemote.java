/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic;

import cameras.Camera;
import exceptions.NoCamerasOnRoute;
import java.util.ArrayList;
import javax.ejb.Remote;
import routes.Route;

/**
 *
 * @author Hlengekile
 */
@Remote
public interface TrafficAnalysisBeanRemote
{

    void determineRouteTraffic();

    ArrayList<Camera> getRouteCameras(Route inRoute) throws NoCamerasOnRoute;
    
}
