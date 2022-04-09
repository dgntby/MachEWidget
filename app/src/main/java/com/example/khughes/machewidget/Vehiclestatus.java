
package com.example.khughes.machewidget;

import java.io.Serializable;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("jsonschema2pojo")
public class Vehiclestatus implements Serializable
{

    @SerializedName("vin")
    @Expose
    private String vin;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("remoteStartStatus")
    @Expose
    private int remoteStartStatus;
    @SerializedName("remoteStartDuration")
    @Expose
    private int remoteStartDuration;
    @SerializedName("remoteStartTime")
    @Expose
    private int remoteStartTime;
    @SerializedName("lockStatus")
    @Expose
    private String lockStatus;
    @SerializedName("alarm")
    @Expose
    private String alarm;
    @SerializedName("batteryHealth")
    @Expose
    private String batteryHealth;
    @SerializedName("odometer")
    @Expose
    private int odometer;
    @SerializedName("fuelLevel")
    @Expose
    private int fuelLevel;
    @SerializedName("oilLife")
    @Expose
    private String oilLife;
    @SerializedName("tirePressure")
    @Expose
    private String tirePressure;
    @SerializedName("authorization")
    @Expose
    private String authorization;
    @SerializedName("gpsState")
    @Expose
    private String gpsState;
    @SerializedName("lastRefresh")
    @Expose
    private String lastRefresh;
    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;
    @SerializedName("oilLifeActual")
    @Expose
    private int oilLifeActual;
    @SerializedName("batteryStatusActual")
    @Expose
    private int batteryStatusActual;
    @SerializedName("serverTime")
    @Expose
    private String serverTime;
    @SerializedName("distanceToEmpty")
    @Expose
    private int distanceToEmpty;
    @SerializedName("tirePressureSystemStatus")
    @Expose
    private String tirePressureSystemStatus;
    @SerializedName("recommendedFrontTirePressure")
    @Expose
    private int recommendedFrontTirePressure;
    @SerializedName("recommendedRearTirePressure")
    @Expose
    private int recommendedRearTirePressure;
    @SerializedName("leftFrontTireStatus")
    @Expose
    private String leftFrontTireStatus;
    @SerializedName("leftFrontTirePressure")
    @Expose
    private String leftFrontTirePressure;
    @SerializedName("rightFrontTireStatus")
    @Expose
    private String rightFrontTireStatus;
    @SerializedName("rightFrontTirePressure")
    @Expose
    private String rightFrontTirePressure;
    @SerializedName("innerLeftRearTireStatus")
    @Expose
    private String innerLeftRearTireStatus;
    @SerializedName("innerLeftRearTirePressure")
    @Expose
    private String innerLeftRearTirePressure;
    @SerializedName("innerRightRearTireStatus")
    @Expose
    private String innerRightRearTireStatus;
    @SerializedName("innerRightRearTirePressure")
    @Expose
    private String innerRightRearTirePressure;
    @SerializedName("outerLeftRearTireStatus")
    @Expose
    private String outerLeftRearTireStatus;
    @SerializedName("outerLeftRearTirePressure")
    @Expose
    private String outerLeftRearTirePressure;
    @SerializedName("outerRightRearTireStatus")
    @Expose
    private String outerRightRearTireStatus;
    @SerializedName("outerRightRearTirePressure")
    @Expose
    private String outerRightRearTirePressure;
    @SerializedName("tirePressureByLocation")
    @Expose
    private int tirePressureByLocation;
    @SerializedName("dualRearWheel")
    @Expose
    private int dualRearWheel;
    @SerializedName("ccsSettings")
    @Expose
    private CcsSettings ccsSettings;
    private final static long serialVersionUID = -1891649796660335439L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Vehiclestatus() {
    }

    /**
     * 
     * @param innerRightRearTirePressure
     * @param remoteStartTime
     * @param fuelLevel
     * @param batteryStatusActual
     * @param tirePressure
     * @param rightFrontTireStatus
     * @param latitude
     * @param leftFrontTireStatus
     * @param outerLeftRearTirePressure
     * @param authorization
     * @param remoteStartDuration
     * @param lockStatus
     * @param recommendedRearTirePressure
     * @param alarm
     * @param rightFrontTirePressure
     * @param outerRightRearTireStatus
     * @param outerRightRearTirePressure
     * @param vin
     * @param serverTime
     * @param tirePressureByLocation
     * @param batteryHealth
     * @param innerRightRearTireStatus
     * @param distanceToEmpty
     * @param longitude
     * @param gpsState
     * @param innerLeftRearTireStatus
     * @param outerLeftRearTireStatus
     * @param odometer
     * @param ccsSettings
     * @param lastRefresh
     * @param lastModifiedDate
     * @param innerLeftRearTirePressure
     * @param leftFrontTirePressure
     * @param tirePressureSystemStatus
     * @param remoteStartStatus
     * @param recommendedFrontTirePressure
     * @param oilLifeActual
     * @param oilLife
     * @param dualRearWheel
     */
    public Vehiclestatus(String vin, String longitude, String latitude, int remoteStartStatus, int remoteStartDuration, int remoteStartTime, String lockStatus, String alarm, String batteryHealth, int odometer, int fuelLevel, String oilLife, String tirePressure, String authorization, String gpsState, String lastRefresh, String lastModifiedDate, int oilLifeActual, int batteryStatusActual, String serverTime, int distanceToEmpty, String tirePressureSystemStatus, int recommendedFrontTirePressure, int recommendedRearTirePressure, String leftFrontTireStatus, String leftFrontTirePressure, String rightFrontTireStatus, String rightFrontTirePressure, String innerLeftRearTireStatus, String innerLeftRearTirePressure, String innerRightRearTireStatus, String innerRightRearTirePressure, String outerLeftRearTireStatus, String outerLeftRearTirePressure, String outerRightRearTireStatus, String outerRightRearTirePressure, int tirePressureByLocation, int dualRearWheel, CcsSettings ccsSettings) {
        super();
        this.vin = vin;
        this.longitude = longitude;
        this.latitude = latitude;
        this.remoteStartStatus = remoteStartStatus;
        this.remoteStartDuration = remoteStartDuration;
        this.remoteStartTime = remoteStartTime;
        this.lockStatus = lockStatus;
        this.alarm = alarm;
        this.batteryHealth = batteryHealth;
        this.odometer = odometer;
        this.fuelLevel = fuelLevel;
        this.oilLife = oilLife;
        this.tirePressure = tirePressure;
        this.authorization = authorization;
        this.gpsState = gpsState;
        this.lastRefresh = lastRefresh;
        this.lastModifiedDate = lastModifiedDate;
        this.oilLifeActual = oilLifeActual;
        this.batteryStatusActual = batteryStatusActual;
        this.serverTime = serverTime;
        this.distanceToEmpty = distanceToEmpty;
        this.tirePressureSystemStatus = tirePressureSystemStatus;
        this.recommendedFrontTirePressure = recommendedFrontTirePressure;
        this.recommendedRearTirePressure = recommendedRearTirePressure;
        this.leftFrontTireStatus = leftFrontTireStatus;
        this.leftFrontTirePressure = leftFrontTirePressure;
        this.rightFrontTireStatus = rightFrontTireStatus;
        this.rightFrontTirePressure = rightFrontTirePressure;
        this.innerLeftRearTireStatus = innerLeftRearTireStatus;
        this.innerLeftRearTirePressure = innerLeftRearTirePressure;
        this.innerRightRearTireStatus = innerRightRearTireStatus;
        this.innerRightRearTirePressure = innerRightRearTirePressure;
        this.outerLeftRearTireStatus = outerLeftRearTireStatus;
        this.outerLeftRearTirePressure = outerLeftRearTirePressure;
        this.outerRightRearTireStatus = outerRightRearTireStatus;
        this.outerRightRearTirePressure = outerRightRearTirePressure;
        this.tirePressureByLocation = tirePressureByLocation;
        this.dualRearWheel = dualRearWheel;
        this.ccsSettings = ccsSettings;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getRemoteStartStatus() {
        return remoteStartStatus;
    }

    public void setRemoteStartStatus(int remoteStartStatus) {
        this.remoteStartStatus = remoteStartStatus;
    }

    public int getRemoteStartDuration() {
        return remoteStartDuration;
    }

    public void setRemoteStartDuration(int remoteStartDuration) {
        this.remoteStartDuration = remoteStartDuration;
    }

    public int getRemoteStartTime() {
        return remoteStartTime;
    }

    public void setRemoteStartTime(int remoteStartTime) {
        this.remoteStartTime = remoteStartTime;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getBatteryHealth() {
        return batteryHealth;
    }

    public void setBatteryHealth(String batteryHealth) {
        this.batteryHealth = batteryHealth;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public int getFuelLevel() {
        return fuelLevel;
    }

    public void setFuelLevel(int fuelLevel) {
        this.fuelLevel = fuelLevel;
    }

    public String getOilLife() {
        return oilLife;
    }

    public void setOilLife(String oilLife) {
        this.oilLife = oilLife;
    }

    public String getTirePressure() {
        return tirePressure;
    }

    public void setTirePressure(String tirePressure) {
        this.tirePressure = tirePressure;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getGpsState() {
        return gpsState;
    }

    public void setGpsState(String gpsState) {
        this.gpsState = gpsState;
    }

    public String getLastRefresh() {
        return lastRefresh;
    }

    public void setLastRefresh(String lastRefresh) {
        this.lastRefresh = lastRefresh;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public int getOilLifeActual() {
        return oilLifeActual;
    }

    public void setOilLifeActual(int oilLifeActual) {
        this.oilLifeActual = oilLifeActual;
    }

    public int getBatteryStatusActual() {
        return batteryStatusActual;
    }

    public void setBatteryStatusActual(int batteryStatusActual) {
        this.batteryStatusActual = batteryStatusActual;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public int getDistanceToEmpty() {
        return distanceToEmpty;
    }

    public void setDistanceToEmpty(int distanceToEmpty) {
        this.distanceToEmpty = distanceToEmpty;
    }

    public String getTirePressureSystemStatus() {
        return tirePressureSystemStatus;
    }

    public void setTirePressureSystemStatus(String tirePressureSystemStatus) {
        this.tirePressureSystemStatus = tirePressureSystemStatus;
    }

    public int getRecommendedFrontTirePressure() {
        return recommendedFrontTirePressure;
    }

    public void setRecommendedFrontTirePressure(int recommendedFrontTirePressure) {
        this.recommendedFrontTirePressure = recommendedFrontTirePressure;
    }

    public int getRecommendedRearTirePressure() {
        return recommendedRearTirePressure;
    }

    public void setRecommendedRearTirePressure(int recommendedRearTirePressure) {
        this.recommendedRearTirePressure = recommendedRearTirePressure;
    }

    public String getLeftFrontTireStatus() {
        return leftFrontTireStatus;
    }

    public void setLeftFrontTireStatus(String leftFrontTireStatus) {
        this.leftFrontTireStatus = leftFrontTireStatus;
    }

    public String getLeftFrontTirePressure() {
        return leftFrontTirePressure;
    }

    public void setLeftFrontTirePressure(String leftFrontTirePressure) {
        this.leftFrontTirePressure = leftFrontTirePressure;
    }

    public String getRightFrontTireStatus() {
        return rightFrontTireStatus;
    }

    public void setRightFrontTireStatus(String rightFrontTireStatus) {
        this.rightFrontTireStatus = rightFrontTireStatus;
    }

    public String getRightFrontTirePressure() {
        return rightFrontTirePressure;
    }

    public void setRightFrontTirePressure(String rightFrontTirePressure) {
        this.rightFrontTirePressure = rightFrontTirePressure;
    }

    public String getInnerLeftRearTireStatus() {
        return innerLeftRearTireStatus;
    }

    public void setInnerLeftRearTireStatus(String innerLeftRearTireStatus) {
        this.innerLeftRearTireStatus = innerLeftRearTireStatus;
    }

    public String getInnerLeftRearTirePressure() {
        return innerLeftRearTirePressure;
    }

    public void setInnerLeftRearTirePressure(String innerLeftRearTirePressure) {
        this.innerLeftRearTirePressure = innerLeftRearTirePressure;
    }

    public String getInnerRightRearTireStatus() {
        return innerRightRearTireStatus;
    }

    public void setInnerRightRearTireStatus(String innerRightRearTireStatus) {
        this.innerRightRearTireStatus = innerRightRearTireStatus;
    }

    public String getInnerRightRearTirePressure() {
        return innerRightRearTirePressure;
    }

    public void setInnerRightRearTirePressure(String innerRightRearTirePressure) {
        this.innerRightRearTirePressure = innerRightRearTirePressure;
    }

    public String getOuterLeftRearTireStatus() {
        return outerLeftRearTireStatus;
    }

    public void setOuterLeftRearTireStatus(String outerLeftRearTireStatus) {
        this.outerLeftRearTireStatus = outerLeftRearTireStatus;
    }

    public String getOuterLeftRearTirePressure() {
        return outerLeftRearTirePressure;
    }

    public void setOuterLeftRearTirePressure(String outerLeftRearTirePressure) {
        this.outerLeftRearTirePressure = outerLeftRearTirePressure;
    }

    public String getOuterRightRearTireStatus() {
        return outerRightRearTireStatus;
    }

    public void setOuterRightRearTireStatus(String outerRightRearTireStatus) {
        this.outerRightRearTireStatus = outerRightRearTireStatus;
    }

    public String getOuterRightRearTirePressure() {
        return outerRightRearTirePressure;
    }

    public void setOuterRightRearTirePressure(String outerRightRearTirePressure) {
        this.outerRightRearTirePressure = outerRightRearTirePressure;
    }

    public int getTirePressureByLocation() {
        return tirePressureByLocation;
    }

    public void setTirePressureByLocation(int tirePressureByLocation) {
        this.tirePressureByLocation = tirePressureByLocation;
    }

    public int getDualRearWheel() {
        return dualRearWheel;
    }

    public void setDualRearWheel(int dualRearWheel) {
        this.dualRearWheel = dualRearWheel;
    }

    public CcsSettings getCcsSettings() {
        return ccsSettings;
    }

    public void setCcsSettings(CcsSettings ccsSettings) {
        this.ccsSettings = ccsSettings;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Vehiclestatus.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("vin");
        sb.append('=');
        sb.append(((this.vin == null)?"<null>":this.vin));
        sb.append(',');
        sb.append("longitude");
        sb.append('=');
        sb.append(((this.longitude == null)?"<null>":this.longitude));
        sb.append(',');
        sb.append("latitude");
        sb.append('=');
        sb.append(((this.latitude == null)?"<null>":this.latitude));
        sb.append(',');
        sb.append("remoteStartStatus");
        sb.append('=');
        sb.append(this.remoteStartStatus);
        sb.append(',');
        sb.append("remoteStartDuration");
        sb.append('=');
        sb.append(this.remoteStartDuration);
        sb.append(',');
        sb.append("remoteStartTime");
        sb.append('=');
        sb.append(this.remoteStartTime);
        sb.append(',');
        sb.append("lockStatus");
        sb.append('=');
        sb.append(((this.lockStatus == null)?"<null>":this.lockStatus));
        sb.append(',');
        sb.append("alarm");
        sb.append('=');
        sb.append(((this.alarm == null)?"<null>":this.alarm));
        sb.append(',');
        sb.append("batteryHealth");
        sb.append('=');
        sb.append(((this.batteryHealth == null)?"<null>":this.batteryHealth));
        sb.append(',');
        sb.append("odometer");
        sb.append('=');
        sb.append(this.odometer);
        sb.append(',');
        sb.append("fuelLevel");
        sb.append('=');
        sb.append(this.fuelLevel);
        sb.append(',');
        sb.append("oilLife");
        sb.append('=');
        sb.append(((this.oilLife == null)?"<null>":this.oilLife));
        sb.append(',');
        sb.append("tirePressure");
        sb.append('=');
        sb.append(((this.tirePressure == null)?"<null>":this.tirePressure));
        sb.append(',');
        sb.append("authorization");
        sb.append('=');
        sb.append(((this.authorization == null)?"<null>":this.authorization));
        sb.append(',');
        sb.append("gpsState");
        sb.append('=');
        sb.append(((this.gpsState == null)?"<null>":this.gpsState));
        sb.append(',');
        sb.append("lastRefresh");
        sb.append('=');
        sb.append(((this.lastRefresh == null)?"<null>":this.lastRefresh));
        sb.append(',');
        sb.append("lastModifiedDate");
        sb.append('=');
        sb.append(((this.lastModifiedDate == null)?"<null>":this.lastModifiedDate));
        sb.append(',');
        sb.append("oilLifeActual");
        sb.append('=');
        sb.append(this.oilLifeActual);
        sb.append(',');
        sb.append("batteryStatusActual");
        sb.append('=');
        sb.append(this.batteryStatusActual);
        sb.append(',');
        sb.append("serverTime");
        sb.append('=');
        sb.append(((this.serverTime == null)?"<null>":this.serverTime));
        sb.append(',');
        sb.append("distanceToEmpty");
        sb.append('=');
        sb.append(this.distanceToEmpty);
        sb.append(',');
        sb.append("tirePressureSystemStatus");
        sb.append('=');
        sb.append(((this.tirePressureSystemStatus == null)?"<null>":this.tirePressureSystemStatus));
        sb.append(',');
        sb.append("recommendedFrontTirePressure");
        sb.append('=');
        sb.append(this.recommendedFrontTirePressure);
        sb.append(',');
        sb.append("recommendedRearTirePressure");
        sb.append('=');
        sb.append(this.recommendedRearTirePressure);
        sb.append(',');
        sb.append("leftFrontTireStatus");
        sb.append('=');
        sb.append(((this.leftFrontTireStatus == null)?"<null>":this.leftFrontTireStatus));
        sb.append(',');
        sb.append("leftFrontTirePressure");
        sb.append('=');
        sb.append(((this.leftFrontTirePressure == null)?"<null>":this.leftFrontTirePressure));
        sb.append(',');
        sb.append("rightFrontTireStatus");
        sb.append('=');
        sb.append(((this.rightFrontTireStatus == null)?"<null>":this.rightFrontTireStatus));
        sb.append(',');
        sb.append("rightFrontTirePressure");
        sb.append('=');
        sb.append(((this.rightFrontTirePressure == null)?"<null>":this.rightFrontTirePressure));
        sb.append(',');
        sb.append("innerLeftRearTireStatus");
        sb.append('=');
        sb.append(((this.innerLeftRearTireStatus == null)?"<null>":this.innerLeftRearTireStatus));
        sb.append(',');
        sb.append("innerLeftRearTirePressure");
        sb.append('=');
        sb.append(((this.innerLeftRearTirePressure == null)?"<null>":this.innerLeftRearTirePressure));
        sb.append(',');
        sb.append("innerRightRearTireStatus");
        sb.append('=');
        sb.append(((this.innerRightRearTireStatus == null)?"<null>":this.innerRightRearTireStatus));
        sb.append(',');
        sb.append("innerRightRearTirePressure");
        sb.append('=');
        sb.append(((this.innerRightRearTirePressure == null)?"<null>":this.innerRightRearTirePressure));
        sb.append(',');
        sb.append("outerLeftRearTireStatus");
        sb.append('=');
        sb.append(((this.outerLeftRearTireStatus == null)?"<null>":this.outerLeftRearTireStatus));
        sb.append(',');
        sb.append("outerLeftRearTirePressure");
        sb.append('=');
        sb.append(((this.outerLeftRearTirePressure == null)?"<null>":this.outerLeftRearTirePressure));
        sb.append(',');
        sb.append("outerRightRearTireStatus");
        sb.append('=');
        sb.append(((this.outerRightRearTireStatus == null)?"<null>":this.outerRightRearTireStatus));
        sb.append(',');
        sb.append("outerRightRearTirePressure");
        sb.append('=');
        sb.append(((this.outerRightRearTirePressure == null)?"<null>":this.outerRightRearTirePressure));
        sb.append(',');
        sb.append("tirePressureByLocation");
        sb.append('=');
        sb.append(this.tirePressureByLocation);
        sb.append(',');
        sb.append("dualRearWheel");
        sb.append('=');
        sb.append(this.dualRearWheel);
        sb.append(',');
        sb.append("ccsSettings");
        sb.append('=');
        sb.append(((this.ccsSettings == null)?"<null>":this.ccsSettings));
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
        result = ((result* 31)+((this.innerRightRearTirePressure == null)? 0 :this.innerRightRearTirePressure.hashCode()));
        result = ((result* 31)+ this.remoteStartTime);
        result = ((result* 31)+ this.fuelLevel);
        result = ((result* 31)+ this.batteryStatusActual);
        result = ((result* 31)+((this.tirePressure == null)? 0 :this.tirePressure.hashCode()));
        result = ((result* 31)+((this.rightFrontTireStatus == null)? 0 :this.rightFrontTireStatus.hashCode()));
        result = ((result* 31)+((this.latitude == null)? 0 :this.latitude.hashCode()));
        result = ((result* 31)+((this.leftFrontTireStatus == null)? 0 :this.leftFrontTireStatus.hashCode()));
        result = ((result* 31)+((this.outerLeftRearTirePressure == null)? 0 :this.outerLeftRearTirePressure.hashCode()));
        result = ((result* 31)+((this.authorization == null)? 0 :this.authorization.hashCode()));
        result = ((result* 31)+ this.remoteStartDuration);
        result = ((result* 31)+((this.lockStatus == null)? 0 :this.lockStatus.hashCode()));
        result = ((result* 31)+ this.recommendedRearTirePressure);
        result = ((result* 31)+((this.alarm == null)? 0 :this.alarm.hashCode()));
        result = ((result* 31)+((this.rightFrontTirePressure == null)? 0 :this.rightFrontTirePressure.hashCode()));
        result = ((result* 31)+((this.outerRightRearTireStatus == null)? 0 :this.outerRightRearTireStatus.hashCode()));
        result = ((result* 31)+((this.outerRightRearTirePressure == null)? 0 :this.outerRightRearTirePressure.hashCode()));
        result = ((result* 31)+((this.vin == null)? 0 :this.vin.hashCode()));
        result = ((result* 31)+((this.serverTime == null)? 0 :this.serverTime.hashCode()));
        result = ((result* 31)+ this.tirePressureByLocation);
        result = ((result* 31)+((this.batteryHealth == null)? 0 :this.batteryHealth.hashCode()));
        result = ((result* 31)+((this.innerRightRearTireStatus == null)? 0 :this.innerRightRearTireStatus.hashCode()));
        result = ((result* 31)+ this.distanceToEmpty);
        result = ((result* 31)+((this.longitude == null)? 0 :this.longitude.hashCode()));
        result = ((result* 31)+((this.gpsState == null)? 0 :this.gpsState.hashCode()));
        result = ((result* 31)+((this.innerLeftRearTireStatus == null)? 0 :this.innerLeftRearTireStatus.hashCode()));
        result = ((result* 31)+((this.outerLeftRearTireStatus == null)? 0 :this.outerLeftRearTireStatus.hashCode()));
        result = ((result* 31)+ this.odometer);
        result = ((result* 31)+((this.ccsSettings == null)? 0 :this.ccsSettings.hashCode()));
        result = ((result* 31)+((this.lastRefresh == null)? 0 :this.lastRefresh.hashCode()));
        result = ((result* 31)+((this.lastModifiedDate == null)? 0 :this.lastModifiedDate.hashCode()));
        result = ((result* 31)+((this.innerLeftRearTirePressure == null)? 0 :this.innerLeftRearTirePressure.hashCode()));
        result = ((result* 31)+((this.leftFrontTirePressure == null)? 0 :this.leftFrontTirePressure.hashCode()));
        result = ((result* 31)+((this.tirePressureSystemStatus == null)? 0 :this.tirePressureSystemStatus.hashCode()));
        result = ((result* 31)+ this.remoteStartStatus);
        result = ((result* 31)+ this.recommendedFrontTirePressure);
        result = ((result* 31)+ this.oilLifeActual);
        result = ((result* 31)+((this.oilLife == null)? 0 :this.oilLife.hashCode()));
        result = ((result* 31)+ this.dualRearWheel);
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Vehiclestatus) == false) {
            return false;
        }
        Vehiclestatus rhs = ((Vehiclestatus) other);
        return ((((((((((((((((((((((((((((((((((((((((this.innerRightRearTirePressure == rhs.innerRightRearTirePressure)||((this.innerRightRearTirePressure!= null)&&this.innerRightRearTirePressure.equals(rhs.innerRightRearTirePressure)))&&(this.remoteStartTime == rhs.remoteStartTime))&&(this.fuelLevel == rhs.fuelLevel))&&(this.batteryStatusActual == rhs.batteryStatusActual))&&((this.tirePressure == rhs.tirePressure)||((this.tirePressure!= null)&&this.tirePressure.equals(rhs.tirePressure))))&&((this.rightFrontTireStatus == rhs.rightFrontTireStatus)||((this.rightFrontTireStatus!= null)&&this.rightFrontTireStatus.equals(rhs.rightFrontTireStatus))))&&((this.latitude == rhs.latitude)||((this.latitude!= null)&&this.latitude.equals(rhs.latitude))))&&((this.leftFrontTireStatus == rhs.leftFrontTireStatus)||((this.leftFrontTireStatus!= null)&&this.leftFrontTireStatus.equals(rhs.leftFrontTireStatus))))&&((this.outerLeftRearTirePressure == rhs.outerLeftRearTirePressure)||((this.outerLeftRearTirePressure!= null)&&this.outerLeftRearTirePressure.equals(rhs.outerLeftRearTirePressure))))&&((this.authorization == rhs.authorization)||((this.authorization!= null)&&this.authorization.equals(rhs.authorization))))&&(this.remoteStartDuration == rhs.remoteStartDuration))&&((this.lockStatus == rhs.lockStatus)||((this.lockStatus!= null)&&this.lockStatus.equals(rhs.lockStatus))))&&(this.recommendedRearTirePressure == rhs.recommendedRearTirePressure))&&((this.alarm == rhs.alarm)||((this.alarm!= null)&&this.alarm.equals(rhs.alarm))))&&((this.rightFrontTirePressure == rhs.rightFrontTirePressure)||((this.rightFrontTirePressure!= null)&&this.rightFrontTirePressure.equals(rhs.rightFrontTirePressure))))&&((this.outerRightRearTireStatus == rhs.outerRightRearTireStatus)||((this.outerRightRearTireStatus!= null)&&this.outerRightRearTireStatus.equals(rhs.outerRightRearTireStatus))))&&((this.outerRightRearTirePressure == rhs.outerRightRearTirePressure)||((this.outerRightRearTirePressure!= null)&&this.outerRightRearTirePressure.equals(rhs.outerRightRearTirePressure))))&&((this.vin == rhs.vin)||((this.vin!= null)&&this.vin.equals(rhs.vin))))&&((this.serverTime == rhs.serverTime)||((this.serverTime!= null)&&this.serverTime.equals(rhs.serverTime))))&&(this.tirePressureByLocation == rhs.tirePressureByLocation))&&((this.batteryHealth == rhs.batteryHealth)||((this.batteryHealth!= null)&&this.batteryHealth.equals(rhs.batteryHealth))))&&((this.innerRightRearTireStatus == rhs.innerRightRearTireStatus)||((this.innerRightRearTireStatus!= null)&&this.innerRightRearTireStatus.equals(rhs.innerRightRearTireStatus))))&&(this.distanceToEmpty == rhs.distanceToEmpty))&&((this.longitude == rhs.longitude)||((this.longitude!= null)&&this.longitude.equals(rhs.longitude))))&&((this.gpsState == rhs.gpsState)||((this.gpsState!= null)&&this.gpsState.equals(rhs.gpsState))))&&((this.innerLeftRearTireStatus == rhs.innerLeftRearTireStatus)||((this.innerLeftRearTireStatus!= null)&&this.innerLeftRearTireStatus.equals(rhs.innerLeftRearTireStatus))))&&((this.outerLeftRearTireStatus == rhs.outerLeftRearTireStatus)||((this.outerLeftRearTireStatus!= null)&&this.outerLeftRearTireStatus.equals(rhs.outerLeftRearTireStatus))))&&(this.odometer == rhs.odometer))&&((this.ccsSettings == rhs.ccsSettings)||((this.ccsSettings!= null)&&this.ccsSettings.equals(rhs.ccsSettings))))&&((this.lastRefresh == rhs.lastRefresh)||((this.lastRefresh!= null)&&this.lastRefresh.equals(rhs.lastRefresh))))&&((this.lastModifiedDate == rhs.lastModifiedDate)||((this.lastModifiedDate!= null)&&this.lastModifiedDate.equals(rhs.lastModifiedDate))))&&((this.innerLeftRearTirePressure == rhs.innerLeftRearTirePressure)||((this.innerLeftRearTirePressure!= null)&&this.innerLeftRearTirePressure.equals(rhs.innerLeftRearTirePressure))))&&((this.leftFrontTirePressure == rhs.leftFrontTirePressure)||((this.leftFrontTirePressure!= null)&&this.leftFrontTirePressure.equals(rhs.leftFrontTirePressure))))&&((this.tirePressureSystemStatus == rhs.tirePressureSystemStatus)||((this.tirePressureSystemStatus!= null)&&this.tirePressureSystemStatus.equals(rhs.tirePressureSystemStatus))))&&(this.remoteStartStatus == rhs.remoteStartStatus))&&(this.recommendedFrontTirePressure == rhs.recommendedFrontTirePressure))&&(this.oilLifeActual == rhs.oilLifeActual))&&((this.oilLife == rhs.oilLife)||((this.oilLife!= null)&&this.oilLife.equals(rhs.oilLife))))&&(this.dualRearWheel == rhs.dualRearWheel));
    }

    // stubs
    public String getChargeEndTime() {
        return "04-09-2022 02:45:01";
    }
}
