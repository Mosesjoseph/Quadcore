/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cameras;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hlengekile
 */
public class SANRALCameraInformationTest {
    
    public SANRALCameraInformationTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadCamerasInfo method, of class SANRALCameraInformation.
     */
    @Test
    public void testLoadCamerasInfo() {
        System.out.println("loadCamerasInfo");
        SANRALCameraInformation instance = null;
        instance.loadCamerasInfo();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkForCamera method, of class SANRALCameraInformation.
     */
    @Test
    public void testCheckForCamera() {
        System.out.println("checkForCamera");
        double latLocation = 0.0;
        double longLocation = 0.0;
        SANRALCameraInformation instance = null;
        String expResult = "";
        String result = instance.checkForCamera(latLocation, longLocation);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getCamera method, of class SANRALCameraInformation.
     */
    @Test
    public void testGetCamera() {
        System.out.println("getCamera");
        String cameraID = "";
        SANRALCameraInformation instance = null;
        Camera expResult = null;
        Camera result = instance.getCamera(cameraID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of processInfo method, of class SANRALCameraInformation.
     */
    @Test
    public void testProcessInfo() {
        System.out.println("processInfo");
        File inFile = null;
        SANRALCameraInformation instance = null;
        instance.processInfo(inFile);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeCameraInfo method, of class SANRALCameraInformation.
     */
    @Test
    public void testStoreCameraInfo() {
        System.out.println("storeCameraInfo");
        Camera inCamera = null;
        SANRALCameraInformation instance = null;
        instance.storeCameraInfo(inCamera);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
