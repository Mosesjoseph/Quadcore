/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cameras;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 *
 * @author Hlengekile
 */
public class SANRALCameraInformation implements CameraInformation
{
    public String apiKey;
    private final EntityManager entityManager;
    
    public SANRALCameraInformation(String apiKey, EntityManager entityManager) 
    {
        this.apiKey = apiKey;
        this.entityManager = entityManager;
    }

    @Override
    public void loadCamerasInfo() 
    {
        try
        {
            String fileName = "cameras.xml";
            File downFile = new File(fileName);
            String requestURL = "https://www.itraffic.co.za/api/cameras?key=" + apiKey + "&format=json";
            URL url = new URL(requestURL);
            BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
            FileOutputStream outputStream = new FileOutputStream(downFile);
            byte[] buffer = new byte[1024];
            int length=0;
            while((length = inputStream.read(buffer,0,1024)) != -1)
            {
                outputStream.write(buffer, 0, length);
            }

            inputStream.close();
            outputStream.close();
            
            processInfo(downFile);
            
        }
        catch(IOException e)
        {
        }
    }

    @Override
    public String checkForCamera(double latLocation, double longLocation) 
    {
        String cameraFoundID = null;
        
        //Make use of GeoLocation class by Jan Philip Matuschek
        
        return cameraFoundID;
    }

    @Override
    public Camera getCamera(String cameraID) 
    {
        Camera cameraFound = null;
        
        Query query = entityManager.createNamedQuery("Find Camera by cameraID");
        query.setParameter("id", cameraID);
        
        if(!(query.getResultList().isEmpty() && !(query.getResultList().size() > 1)))
        {
            cameraFound = (Camera) query.getSingleResult();
        }
        
        return cameraFound;
    }
    
    protected void processInfo(File inFile)
    {
        try 
        {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(inFile);

            NodeList cameras = doc.getElementsByTagName("Camera");
            
            for(int i = 0; i < cameras.getLength(); i++)
            {
                Camera currentCam = new Camera();
                
                NodeList cameraElements = cameras.item(i).getChildNodes();
                
                int colonPos = cameraElements.item(1).getNodeValue().indexOf("::");
                String networkID = cameraElements.item(1).getNodeValue().substring(0, colonPos - 1);
                String cameraID = cameraElements.item(1).getNodeValue().substring(colonPos + 1);
                   
                currentCam.setNetworkID(networkID);
                currentCam.setCameraID(cameraID);
                currentCam.setLatitude(Double.parseDouble(cameraElements.item(2).getNodeValue()));
                currentCam.setLongitude(Double.parseDouble(cameraElements.item(3).getNodeValue()));
                currentCam.setCameraName(cameraElements.item(4).getNodeValue());
                currentCam.setRoadwayName(cameraElements.item(5).getNodeValue());
                
                storeCameraInfo(currentCam);
            }

	}
        catch(ParserConfigurationException | SAXException | IOException | DOMException e)
        {
            
        }
    
    }

    public void storeCameraInfo(Camera inCamera)
    {
        entityManager.getTransaction().begin();
        entityManager.persist(inCamera);
        entityManager.getTransaction().commit();
    }
    
}
