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
public class InvalidRoute extends TrafficAnalysisException
{

    public InvalidRoute() {
    }

    public InvalidRoute(String message) {
        super(message);
    }

    public InvalidRoute(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRoute(Throwable cause) {
        super(cause);
    }
    
}
