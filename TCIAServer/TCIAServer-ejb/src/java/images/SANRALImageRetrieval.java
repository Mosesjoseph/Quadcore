/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import cameras.Camera;
import exceptions.NoCameraImage;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Hlengekile
 */
public class SANRALImageRetrieval implements ImageRetrieval
{
    public String apiKey;

    public SANRALImageRetrieval(String apiKey) 
    {
        this.apiKey = apiKey;
    }
    
    @Override
    public BufferedImage fetchImage(Camera inCam) throws NoCameraImage
    {
        BufferedImage cameraImage = null;
        
        try
        {
            String requestURL = "https://www.itraffic.co.za/api/cameraimage?key=" + apiKey + "&format={format}&cameraID=" + inCam.getCameraID() + "&networkId=" + inCam.getNetworkID();
            
            URL url = new URL(requestURL);
            cameraImage = ImageIO.read(url);
           
        }
        catch(IOException e)
        {
                     
        }
        
        return cameraImage;
    }
    
}
