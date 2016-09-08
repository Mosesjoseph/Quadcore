/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

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
public class OCVImageProcessingTest {
    
    public OCVImageProcessingTest() {
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
     * Test of fetchCameraImage method, of class OCVImageProcessing.
     */
    @Test
    public void testFetchCameraImage() {
        System.out.println("fetchCameraImage");
        ImageRetrieval retriever = null;
        OCVImageProcessing instance = null;
        instance.fetchCameraImage(retriever);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of analyseImage method, of class OCVImageProcessing.
     */
    @Test
    public void testAnalyseImage() {
        System.out.println("analyseImage");
        OCVImageProcessing instance = null;
        instance.analyseImage();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeTrafficLevel method, of class OCVImageProcessing.
     */
    @Test
    public void testStoreTrafficLevel() {
        System.out.println("storeTrafficLevel");
        int trafficLevel = 0;
        OCVImageProcessing instance = null;
        instance.storeTrafficLevel(trafficLevel);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
