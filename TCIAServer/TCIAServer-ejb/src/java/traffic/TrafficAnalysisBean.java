/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic;

import cameras.Camera;
import exceptions.NoCamerasOnRoute;
import java.util.ArrayList;
import javax.ejb.Stateless;
import routes.Route;

/**
 *
 * @author Hlengekile
 */
@Stateless
public class TrafficAnalysisBean implements TrafficAnalysisBeanRemote 
{

    @Override
    public void determineRouteTraffic() 
    {
    }

    @Override
    public ArrayList<Camera> getRouteCameras(Route inRoute) throws NoCamerasOnRoute
    {
        return null;
    }
   
    
}
