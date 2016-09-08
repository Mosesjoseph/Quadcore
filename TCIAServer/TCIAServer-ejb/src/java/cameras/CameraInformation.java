/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cameras;

/**
 *
 * @author Hlengekile
 */
public interface CameraInformation 
{
    public void loadCamerasInfo();
    
    public String checkForCamera(double latLocation, double longLocation);
    
    public Camera getCamera(String cameraID);
}
