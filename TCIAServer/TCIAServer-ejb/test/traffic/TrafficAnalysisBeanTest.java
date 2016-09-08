/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traffic;

import cameras.Camera;
import java.util.ArrayList;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import routes.Route;

/**
 *
 * @author Hlengekile
 */
public class TrafficAnalysisBeanTest {
    
    public TrafficAnalysisBeanTest() 
    {
    }
    
    @BeforeClass
    public static void setUpClass() 
    {
    }
    
    @AfterClass
    public static void tearDownClass() 
    {
    }
    
    @Before
    public void setUp() 
    {
    }
    
    @After
    public void tearDown() 
    {
    }

    /**
     * Test of determineRouteTraffic method, of class TrafficAnalysisBean.
     */
    @Test
    public void testDetermineRouteTraffic() throws Exception 
    {
        System.out.println("determineRouteTraffic");
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        TrafficAnalysisBeanRemote instance = (TrafficAnalysisBeanRemote)container.getContext().lookup("java:global/classes/TrafficAnalysisBean");
        instance.determineRouteTraffic();
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRouteCameras method, of class TrafficAnalysisBean.
     */
    @Test
    public void testGetRouteCameras() throws Exception
    {
        System.out.println("getRouteCameras");
        Route inRoute = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        TrafficAnalysisBeanRemote instance = (TrafficAnalysisBeanRemote)container.getContext().lookup("java:global/classes/TrafficAnalysisBean");
        ArrayList<Camera> expResult = null;
        ArrayList<Camera> result = instance.getRouteCameras(inRoute);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
