/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import cameras.Camera;
import exceptions.NoCameraImage;
import java.awt.image.*;

/**
 *
 * @author Hlengekile
 */
public interface ImageRetrieval 
{
   public BufferedImage fetchImage(Camera inCam) throws NoCameraImage;
}
