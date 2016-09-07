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
public class TrafficAnalysisException extends Exception
{

    public TrafficAnalysisException() {
    }

    public TrafficAnalysisException(String message) {
        super(message);
    }

    public TrafficAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }

    public TrafficAnalysisException(Throwable cause) {
        super(cause);
    }
    
}
