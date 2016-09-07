/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import cameras.Camera;
import java.awt.image.BufferedImage;
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
public class SANRALImageRetrievalTest {
    
    public SANRALImageRetrievalTest() {
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
     * Test of fetchImage method, of class SANRALImageRetrieval.
     */
    @Test
    public void testFetchImage() throws Exception {
        System.out.println("fetchImage");
        Camera inCam = null;
        SANRALImageRetrieval instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.fetchImage(inCam);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
