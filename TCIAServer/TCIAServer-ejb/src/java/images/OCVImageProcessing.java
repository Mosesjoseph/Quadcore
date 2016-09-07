/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import cameras.Camera;
import exceptions.NoCameraImage;
import java.awt.image.BufferedImage;
import javax.persistence.EntityManager;

/**
 *
 * @author Hlengekile
 */
public class OCVImageProcessing implements ImageProcessing
{
    public Camera imageCam;
    private EntityManager entityManager;
    public BufferedImage image;

    public OCVImageProcessing(Camera imageCam, EntityManager entityManager) 
    {
        this.imageCam = imageCam;
        this.entityManager = entityManager;
    }

    @Override
    public void fetchCameraImage(ImageRetrieval retriever) 
    {
        try
        {
            image = retriever.fetchImage(imageCam);
        }
        catch(NoCameraImage e)
        {
        }
    }

    @Override
    public void analyseImage()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void storeTrafficLevel(int trafficLevel) 
    {
        TrafficLevel level = new TrafficLevel();
        level.setImageCamera(imageCam);
        level.setTrafficLevel(trafficLevel);
        
        entityManager.getTransaction().begin();
        entityManager.persist(level);
        entityManager.getTransaction().commit();
    }
    
}
