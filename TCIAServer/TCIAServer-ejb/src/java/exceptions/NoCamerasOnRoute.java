/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exceptions;

/**
 *
 * @author Hlengekile
 */
public class NoCamerasOnRoute extends TrafficAnalysisException
{

    public NoCamerasOnRoute() {
    }

    public NoCamerasOnRoute(String message) {
        super(message);
    }

    public NoCamerasOnRoute(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCamerasOnRoute(Throwable cause) {
        super(cause);
    }
    
}
