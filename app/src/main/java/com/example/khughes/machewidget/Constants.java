package com.example.khughes.machewidget;

public class Constants {
    public static final String APID = "35F9024B-010E-4FE7-B202-62D941F8681C";
    // public static final String APID = "71A3AD0A-CF46-4CCF-B473-FC7FE5BC4592";
    // public static final String CLIENTID = "9fb503e0-715b-47e8-adfd-ad4b7770f73b";
    public static final String CLIENTID = "5d5b31ff-1b07-43fc-ae8b-c67aec565f52";
    public static final String DEVICEID = "CDBF071B-2A6D-41A0-88DD-D99CD04C91BF";
    public static final String OTATIMEFORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"; // 2022-01-19T05:10:35.239+0000
    public static final String STATUSTIMEFORMAT = "MM-dd-yyyy HH:mm:ss"; // 01-19-2022 13:00:00
    public static final String LOCALTIMEFORMATUS = "MMM d, HH:mm z"; // Jan 29, 13:00 PST
    public static final String LOCALTIMEFORMAT = "d MMM, HH:mm z"; // 29 Jan, 13:00 PST
    public static final String OLDLOCALTIMEFORMAT = "yyyy-MM-dd HH:mm:ss z"; // 2022-01-29 13:00:00 PST
    public static final Double KMTOMILES = 0.6213711922;
    public static final Double KPATOPSI = 0.14503774;
    public static final Double KPATOBAR = 0.01;

    public static final String STATE_INITIAL_STATE = "INITIAL_STATE";
    public static final String STATE_ATTEMPT_TO_GET_ACCESS_TOKEN = "ATTEMPT_TO_GET_ACCESS_TOKEN";
    public static final String STATE_ATTEMPT_TO_REFRESH_ACCESS_TOKEN = "ATTEMPT_TO_REFRESH_ACCESS_TOKEN";
    public static final String STATE_ATTEMPT_TO_GET_VEHICLE_STATUS = "ATTEMPT_TO_GET_VEHICLE_STATUS";
    public static final String STATE_ATTEMPT_TO_GET_VIN_AGAIN = "ATTEMPT_TO_GET_VIN_AGAIN";
    public static final String STATE_HAVE_TOKEN_AND_STATUS = "HAVE_TOKEN_AND_STATUS";

    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_SERVER_ERROR = 402;

    // Make sure these match values in arrays.xml
    public static final int UNITS_SYSTEM = 0;
    public static final int UNITS_METRIC = 1;
    public static final int UNITS_IMPERIAL = 2;

    public static final String REPOURL = "https://github.com/khpylon/MachEWidget";
}
