package za.co.quadcore.trafficanalysisapp;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class TCIAUnitTest
{
    GoogleRequest googleRequest;
    GoogleJSONParser googleJSONParser;

    @Test
    public void testURL()
    {
        assertEquals("https://maps.googleapis.com/maps/api/directions/json?origin=pretoria&" +
                "destination=johannesburg&key=AIzaSyBxzMOzDddAIUKR3RlINgbhtTReEGCvEKI", new GoogleRequest("pretoria", "johannesburg").getUrl());
        assertEquals("https://maps.googleapis.com/maps/api/directions/json?origin=the+brown+cat&" +
                "destination=ha+ha%2C&key=AIzaSyBxzMOzDddAIUKR3RlINgbhtTReEGCvEKI", new GoogleRequest("the brown cat", "ha ha,").getUrl());
    }

}