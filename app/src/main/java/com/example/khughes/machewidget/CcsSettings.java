
package com.example.khughes.machewidget;

import java.io.Serializable;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CcsSettings implements Serializable
{

    @SerializedName("location")
    @Expose
    private int location;
    @SerializedName("vehicleConnectivity")
    @Expose
    private int vehicleConnectivity;
    @SerializedName("vehicleData")
    @Expose
    private int vehicleData;
    @SerializedName("drivingCharacteristics")
    @Expose
    private int drivingCharacteristics;
    @SerializedName("contacts")
    @Expose
    private int contacts;
    private final static long serialVersionUID = 7516366321534820402L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CcsSettings() {
    }

    /**
     * 
     * @param drivingCharacteristics
     * @param vehicleData
     * @param location
     * @param vehicleConnectivity
     * @param contacts
     */
    public CcsSettings(int location, int vehicleConnectivity, int vehicleData, int drivingCharacteristics, int contacts) {
        super();
        this.location = location;
        this.vehicleConnectivity = vehicleConnectivity;
        this.vehicleData = vehicleData;
        this.drivingCharacteristics = drivingCharacteristics;
        this.contacts = contacts;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getVehicleConnectivity() {
        return vehicleConnectivity;
    }

    public void setVehicleConnectivity(int vehicleConnectivity) {
        this.vehicleConnectivity = vehicleConnectivity;
    }

    public int getVehicleData() {
        return vehicleData;
    }

    public void setVehicleData(int vehicleData) {
        this.vehicleData = vehicleData;
    }

    public int getDrivingCharacteristics() {
        return drivingCharacteristics;
    }

    public void setDrivingCharacteristics(int drivingCharacteristics) {
        this.drivingCharacteristics = drivingCharacteristics;
    }

    public int getContacts() {
        return contacts;
    }

    public void setContacts(int contacts) {
        this.contacts = contacts;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CcsSettings.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("location");
        sb.append('=');
        sb.append(this.location);
        sb.append(',');
        sb.append("vehicleConnectivity");
        sb.append('=');
        sb.append(this.vehicleConnectivity);
        sb.append(',');
        sb.append("vehicleData");
        sb.append('=');
        sb.append(this.vehicleData);
        sb.append(',');
        sb.append("drivingCharacteristics");
        sb.append('=');
        sb.append(this.drivingCharacteristics);
        sb.append(',');
        sb.append("contacts");
        sb.append('=');
        sb.append(this.contacts);
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+ this.location);
        result = ((result* 31)+ this.vehicleConnectivity);
        result = ((result* 31)+ this.drivingCharacteristics);
        result = ((result* 31)+ this.vehicleData);
        result = ((result* 31)+ this.contacts);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CcsSettings) == false) {
            return false;
        }
        CcsSettings rhs = ((CcsSettings) other);
        return (((((this.location == rhs.location)&&(this.vehicleConnectivity == rhs.vehicleConnectivity))&&(this.drivingCharacteristics == rhs.drivingCharacteristics))&&(this.vehicleData == rhs.vehicleData))&&(this.contacts == rhs.contacts));
    }

}
