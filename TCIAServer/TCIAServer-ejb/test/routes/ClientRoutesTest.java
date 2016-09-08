/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package routes;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import traffic.TrafficRequest;

/**
 *
 * @author Hlengekile
 */
public class ClientRoutesTest {
    
    public ClientRoutesTest() {
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
     * Test of processRoute method, of class ClientRoutes.
     */
    @Test
    public void testProcessRoute() throws Exception {
        System.out.println("processRoute");
        TrafficRequest inRequest = null;
        ClientRoutes instance = null;
        instance.processRoute(inRequest);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkForRoute method, of class ClientRoutes.
     */
    @Test
    public void testCheckForRoute() {
        System.out.println("checkForRoute");
        String route = "";
        ClientRoutes instance = null;
        Long expResult = null;
        Long result = instance.checkForRoute(route);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of accessRoute method, of class ClientRoutes.
     */
    @Test
    public void testAccessRoute() {
        System.out.println("accessRoute");
        Long routeID = null;
        ClientRoutes instance = null;
        Route expResult = null;
        Route result = instance.accessRoute(routeID);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of storeRoute method, of class ClientRoutes.
     */
    @Test
    public void testStoreRoute() {
        System.out.println("storeRoute");
        Route inRoute = null;
        ClientRoutes instance = null;
        instance.storeRoute(inRoute);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
