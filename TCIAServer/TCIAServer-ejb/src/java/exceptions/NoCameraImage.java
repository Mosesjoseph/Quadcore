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
public class NoCameraImage extends TrafficAnalysisException
{

    public NoCameraImage() {
    }

    public NoCameraImage(String message) {
        super(message);
    }

    public NoCameraImage(String message, Throwable cause) {
        super(message, cause);
    }

    public NoCameraImage(Throwable cause) {
        super(cause);
    }
    
}
