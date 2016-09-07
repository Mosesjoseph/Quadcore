/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package images;

import cameras.Camera;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

/**
 *
 * @author Hlengekile
 */
@Entity
@NamedQuery(query = "Select level from TrafficLevel level where level.imageCamera = :camera", name = "Find TrafficLevel by Camera")
public class TrafficLevel implements Serializable 
{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long levelID;
    private int trafficLevel;
    
    @OneToOne
    private Camera imageCamera;

    public Long getLevelID() {
        return levelID;
    }

    public void setLevelID(Long levelID) {
        this.levelID = levelID;
    }

    public int getTrafficLevel() {
        return trafficLevel;
    }

    public void setTrafficLevel(int trafficLevel) {
        this.trafficLevel = trafficLevel;
    }

    public Camera getImageCamera() {
        return imageCamera;
    }

    public void setImageCamera(Camera imageCamera) {
        this.imageCamera = imageCamera;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (levelID != null ? levelID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the levelID fields are not set
        if (!(object instanceof TrafficLevel)) {
            return false;
        }
        TrafficLevel other = (TrafficLevel) object;
        if ((this.levelID == null && other.levelID != null) || (this.levelID != null && !this.levelID.equals(other.levelID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "images.TrafficLevelCache[ levelID=" + levelID + " ]";
    }
    
}
