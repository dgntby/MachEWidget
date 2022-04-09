
package com.example.khughes.machewidget;

import java.io.Serializable;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class CarStatusCN implements Serializable
{

    @SerializedName("vehiclestatus")
    @Expose
    private Vehiclestatus vehiclestatus;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("version")
    @Expose
    private String version;
    private final static long serialVersionUID = -4239585218994015816L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public CarStatusCN() {
    }

    /**
     * 
     * @param vehiclestatus
     * @param version
     * @param status
     */
    public CarStatusCN(Vehiclestatus vehiclestatus, int status, String version) {
        super();
        this.vehiclestatus = vehiclestatus;
        this.status = status;
        this.version = version;
    }

    public Vehiclestatus getVehiclestatus() {
        return vehiclestatus;
    }

    public void setVehiclestatus(Vehiclestatus vehiclestatus) {
        this.vehiclestatus = vehiclestatus;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }


    // simpler getters go here
    public Double getOdometer() {
        try {
            return Double.valueOf(getVehiclestatus().getOdometer());
        } catch (NullPointerException e) {
            return null;
        }
    }



    public Integer getLVBVoltage() {
        try {
            return getVehiclestatus().getBatteryStatusActual();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLVBStatus() {
        try {
            return getVehiclestatus().getBatteryHealth();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLeftFrontTireStatus() {
        try {
            return getVehiclestatus().getLeftFrontTireStatus();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getRightFrontTireStatus() {
        try {
            return getVehiclestatus().getRightFrontTireStatus();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLeftRearTireStatus() {
        try {
            return getVehiclestatus().getOuterLeftRearTireStatus();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getRightRearTireStatus() {
        try {
            return getVehiclestatus().getOuterRightRearTireStatus();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLeftFrontTirePressure() {
        try {
            return getVehiclestatus().getLeftFrontTirePressure();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getRightFrontTirePressure() {
        try {
            return getVehiclestatus().getRightFrontTirePressure();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLeftRearTirePressure() {
        try {
            return getVehiclestatus().getOuterLeftRearTirePressure();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getRightRearTirePressure() {
        try {
            return getVehiclestatus().getOuterRightRearTirePressure();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLock() {
        try {
            return getVehiclestatus().getLockStatus();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public Boolean getRemoteStartStatus() {
        try {
            return getVehiclestatus().getRemoteStartStatus() == 1;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getAlarm() {
        try {
            return getVehiclestatus().getAlarm();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLatitude() {
        try {
            return getVehiclestatus().getLatitude();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLongitude() {
        try {
            return getVehiclestatus().getLongitude();
        } catch (NullPointerException e) {
            return null;
        }
    }

    public String getLastRefresh() {
        try {
            String lastRefresh = getVehiclestatus().getLastRefresh();
            if (lastRefresh.contains("01-01-2018")) {
                lastRefresh = getVehiclestatus().getLastModifiedDate();
            }
            return lastRefresh;
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CarStatusCN.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vehiclestatus");
        sb.append('=');
        sb.append(((this.vehiclestatus == null)?"<null>":this.vehiclestatus));
        sb.append(',');
        sb.append("status");
        sb.append('=');
        sb.append(this.status);
        sb.append(',');
        sb.append("version");
        sb.append('=');
        sb.append(((this.version == null)?"<null>":this.version));
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
        result = ((result* 31)+((this.vehiclestatus == null)? 0 :this.vehiclestatus.hashCode()));
        result = ((result* 31)+((this.version == null)? 0 :this.version.hashCode()));
        result = ((result* 31)+ this.status);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof CarStatusCN) == false) {
            return false;
        }
        CarStatusCN rhs = ((CarStatusCN) other);
        return ((((this.vehiclestatus == rhs.vehiclestatus)||((this.vehiclestatus!= null)&&this.vehiclestatus.equals(rhs.vehiclestatus)))&&((this.version == rhs.version)||((this.version!= null)&&this.version.equals(rhs.version))))&&(this.status == rhs.status));
    }

    // stubs
/*
    public String getChargingStatus() {
        return "NA";
    }

    public String getIgnition() {
        return "NA";

    }

    public Boolean getDeepSleep() {
        return false;
    }

    public Double getElVehDTE() {
        return 0.0;
    }

    public Boolean getPlugStatus() {
        return false;
    }

    public Double getHVBFillLevel() {
        return 0.0;
    }

    public Double getDistanceToEmpty() {
        return 0.0;

    }

    public Double getFuelLevel() {
        return 0.0;

    }*/
}
