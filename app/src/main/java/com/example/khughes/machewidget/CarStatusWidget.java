package com.example.khughes.machewidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.preference.PreferenceManager;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.ParseException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CarStatusWidget extends AppWidgetProvider {
    public static final String WIDGET_IDS_KEY = "com.example.khughes.machewidget.CARSTATUSWIDGET";

    private static final String PROFILE_CLICK = "Profile";
    private static final String WIDGET_CLICK = "Widget";
    private static final String SETTINGS_CLICK = "SettingsButton";
    private static final String LEFT_BUTTON_CLICK = "FordPassButton";
    private static final String RIGHT_BUTTON_CLICK = "ChargerButton";
    private static final String IGNITION_CLICK = "IgnitionButton";
    private static final String LOCK_CLICK = "LockButton";
    private static final String REFRESH_CLICK = "Refresh";

    private static final String PADDING = "   ";
    private static final String CHARGING_STATUS_NOT_READY = "NotReady";
    private static final String CHARGING_STATUS_CHARGING_AC = "ChargingAC";
    private static final String CHARGING_STATUS_CHARGING_DC = "ChargingDC";
    private static final String CHARGING_STATUS_TARGET_REACHED = "ChargeTargetReached";
    private static final String CHARGING_STATUS_PRECONDITION = "CabinPreconditioning";
    private static final String CHARGING_STATUS_PAUSED = "EvsePaused";

    private void updateWindow(RemoteViews views, String window, int id, int drawable) {
        // If we can't confirm the window is open, draw nothing.
        if (window == null || window.toLowerCase().replaceAll("[^a-z0-9]", "").contains("fullyclosed")
                || window.toLowerCase().contains("undefined")) {
            drawable = R.drawable.filler;
        }
        views.setImageViewResource(id, drawable);
    }

    private void updateTire(RemoteViews views, String pressure, String status,
                            String units, Double conversion, int id) {
        // Set the textview background color based on the status
        int drawable;
        if (status != null && !status.equals("Normal")) {
            drawable = R.drawable.pressure_oval_red;
        } else {
            drawable = R.drawable.pressure_oval;
        }
        views.setInt(id, "setBackgroundResource", drawable);

        // Get the tire pressure and do any conversion necessary.
        if (pressure != null) {
            double value = Double.parseDouble(pressure);
            // Only display value if it's not ridiculous; after some OTA updates the
            // raw value is "65533"
            if (value < 2000) {
                // If conversion is really small, show value in tenths
                String pattern = conversion >= 0.1 ? "#" : "#.0";
                pressure = MessageFormat.format("{0}{1}", new DecimalFormat(pattern, // "#.0",
                        DecimalFormatSymbols.getInstance(Locale.US)).format(value * conversion), units);
                views.setInt(id, "setBackgroundResource", R.drawable.pressure_oval);
            } else {
                pressure = "N/A";
            }
        } else {
            pressure = "N/A";
        }
        views.setTextViewText(id, pressure);
    }

    private void updateLocation(Context context, RemoteViews views, String latitude, String longitude, int dataLine) {
        List<Address> addresses = null;
        String streetName = PADDING;
        String cityState = "";
        int[] ids = new int[]{R.id.DataLine1, R.id.DataLine2, R.id.DataLine3, R.id.DataLine4, R.id.DataLine5};

        views.setTextViewText(ids[dataLine], "Location:");

        Geocoder mGeocoder = new Geocoder(context, Locale.getDefault());
        double lat = Double.parseDouble(latitude);
        double lon = Double.parseDouble(longitude);
        try {
            addresses = mGeocoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            LogFile.e(context, MainActivity.CHANNEL_ID, "IOException in CarStatusWidget.updateAppWidget (normal)");
        }

        // If an address was found, go with the first entry
        if (addresses != null && addresses.size() > 0) {
            Address address = addresses.get(0);

            // Street address and name
            if (address.getSubThoroughfare() != null) {
                streetName += address.getSubThoroughfare() + " ";
            }
            if (address.getThoroughfare() != null) {
                streetName += address.getThoroughfare();
            }

            // Other locality info (state, province, etc)
            if (address.getLocality() != null && address.getAdminArea() != null) {
                String adminArea = address.getAdminArea();
                if (Utils.states.containsKey(adminArea)) {
                    adminArea = Utils.states.get(adminArea);
                }
                cityState = PADDING + address.getLocality() + ", " + adminArea;
            }

            // If no street, move city/state up
            if (streetName.equals(PADDING)) {
                streetName = cityState;
                cityState = "";
            }
        } else {
            streetName = PADDING + "N/A";
        }
//        streetName = PADDING + "45500 Fremont Blvd";
//        cityState = PADDING + "Fremont, CA";
        views.setTextViewText(ids[dataLine + 1], streetName);
        views.setTextViewText(ids[dataLine + 2], cityState);
    }

    // Check if a door is open or closed.  Undefined defaults to closed.
    private Boolean isDoorClosed(String status) {
        return (status == null || status.toLowerCase().contains("closed"));
    }

    // Set background transparency
    private void setBackground(Context context, RemoteViews views) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean useTranparency = sharedPref.getBoolean(context.getResources().getString(R.string.transp_bg_key), false);
        views.setInt(R.id.thewidget, "setBackgroundResource",
                useTranparency ? R.color.transparent_black : R.color.black);
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context, getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
    }

    private void setCallbacks(Context context, RemoteViews views) {
        // Define actions for clicking on various icons, including the widget itself
        views.setOnClickPendingIntent(R.id.thewidget, getPendingSelfIntent(context, WIDGET_CLICK));
        views.setOnClickPendingIntent(R.id.settings, getPendingSelfIntent(context, SETTINGS_CLICK));

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        boolean profilesActive = sharedPref.getBoolean(context.getResources().getString(R.string.show_profiles_key), false);

        if (profilesActive) {
            views.setOnClickPendingIntent(R.id.profile, getPendingSelfIntent(context, PROFILE_CLICK));
        }

        boolean showAppLinks = sharedPref.getBoolean(context.getResources().getString(R.string.show_app_links_key), true);
        if (showAppLinks) {
            views.setOnClickPendingIntent(R.id.leftappbutton, getPendingSelfIntent(context, LEFT_BUTTON_CLICK));
            views.setOnClickPendingIntent(R.id.rightappbutton, getPendingSelfIntent(context, RIGHT_BUTTON_CLICK));
        } else {
            views.setOnClickPendingIntent(R.id.leftappbutton, getPendingSelfIntent(context, WIDGET_CLICK));
            views.setOnClickPendingIntent(R.id.rightappbutton, getPendingSelfIntent(context, WIDGET_CLICK));
        }

        boolean enableCommands = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getResources().getString(R.string.enable_commands_key), false);
        if (enableCommands) {
            views.setOnClickPendingIntent(R.id.lock, getPendingSelfIntent(context, LOCK_CLICK));
            views.setOnClickPendingIntent(R.id.ignition, getPendingSelfIntent(context, IGNITION_CLICK));
        } else {
            views.setOnClickPendingIntent(R.id.lock, getPendingSelfIntent(context, WIDGET_CLICK));
            views.setOnClickPendingIntent(R.id.ignition, getPendingSelfIntent(context, WIDGET_CLICK));
        }
        views.setOnClickPendingIntent(R.id.lastRefresh, getPendingSelfIntent(context, REFRESH_CLICK));
    }

    // Based on the VIN, find the right widget layout
    private RemoteViews getWidgetView(Context context) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");
        return new RemoteViews(context.getPackageName(), Utils.getLayoutByVIN(VIN));
    }

    private void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                 int appWidgetId) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");
        int fuelType = Utils.getFuelType(VIN);
        boolean profilesActive = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getResources().getString(R.string.show_profiles_key), false);

        RemoteViews views = getWidgetView(context);

        // Make sure the left side is visible depending on the widget width
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        onResize(appWidgetOptions, views);

        // Setup actions for specific widgets
        setCallbacks(context, views);

        // Set background transparency
        setBackground(context, views);

        // Display profile name if active
        if (profilesActive) {
            views.setTextViewText(R.id.profile, "Profile: " + new StoredData(context).getProfileName(VIN));
        } else {
            views.setTextViewText(R.id.profile, "");
        }

        // If no status information, print something generic and return
        // TODO: also refresh the icons as if we're logged out?
        CarStatusCN carStatus = new StoredData(context).getCarStatus(VIN);
        if (carStatus == null || carStatus.getVehiclestatus() == null) {
            views.setTextViewText(R.id.lastRefresh, "Unable to retrieve status information.");
            appWidgetManager.updateAppWidget(appWidgetId, views);
            return;
        }

        // Fill in the last update time
        Calendar lastUpdateTime = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.STATUSTIMEFORMAT, Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            lastUpdateTime.setTime(sdf.parse(carStatus.getLastRefresh()));// all done
        } catch (ParseException e) {
            LogFile.e(context, MainActivity.CHANNEL_ID, "exception in CarStatusWidget.updateAppWidget: ", e);
        }
        Calendar currentTime = Calendar.getInstance();
        long minutes = (Duration.between(lastUpdateTime.toInstant(), currentTime.toInstant()).getSeconds() + 30) / 60;
        LogFile.i(context, MainActivity.CHANNEL_ID, "updateAppWidget(): last vehicle update was " + minutes + " minutes ago.");

        String refresh = "Last refresh:\n  ";
        boolean displayTime = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getResources().getString(R.string.last_refresh_time_key), false);
        if (displayTime) {
            sdf = new SimpleDateFormat(new StoredData(context).getTimeFormatByCountry(VIN), Locale.ENGLISH);
            refresh += sdf.format(lastUpdateTime.getTime());
        } else {
            // less than 1 minute
            if (minutes < 1) {
                refresh += "just now";
                // less than an hour
            } else if (minutes < 60) {
                refresh += minutes + " min ago";
                // less than a day
            } else if (minutes / 60 < 24) {
                refresh += (minutes / 60) + " hr";
                // right on the hour
                if ((minutes % 60) == 0) {
                    if (minutes == 60) {
                        refresh += " ago";
                    } else {
                        refresh += "s ago";
                    }
                    // hours and minutes
                } else {
                    if (minutes >= 120) {
                        refresh += "s";
                    }
                    refresh += ", " + (minutes % 60) + " min ago";
                }
            } else {
                long days = minutes / (24 * 60);
                // one day
                if (days == 1) {
                    refresh += " 1 day ago";
                    // multiple days
                } else {
                    refresh += days + " days ago";
                }
            }
        }
        views.setTextViewText(R.id.lastRefresh, refresh);

        // Get conversion factors for Metric vs Imperial measurement units
        StoredData appInfo = new StoredData(context);
        int units = Integer.parseInt(PreferenceManager.getDefaultSharedPreferences(context)
                .getString(
                        context.getResources().getString(R.string.units_key),
                        context.getResources().getString(R.string.units_system)
                ));

        double distanceConversion;
        String distanceUnits;
        if ((units == Constants.UNITS_SYSTEM && appInfo.getSpeedUnits(VIN).equals("MPH")) || units == Constants.UNITS_IMPERIAL) {
            distanceConversion = Constants.KMTOMILES;
            distanceUnits = "miles";
        } else {
            distanceConversion = 1.0;
            distanceUnits = "km";
        }
        double pressureConversion;
        String pressureUnits;
        if ((units == Constants.UNITS_SYSTEM && appInfo.getPressureUnits(VIN).equals("PSI")) || units == Constants.UNITS_IMPERIAL) {
            pressureConversion = Constants.KPATOPSI;
            pressureUnits = "psi";
        } else if (units == Constants.UNITS_SYSTEM && appInfo.getPressureUnits(VIN).equals("BAR")) {
            pressureConversion = Constants.KPATOBAR;
            pressureUnits = "bar";
        } else {
            pressureConversion = 1.0;
            pressureUnits = "kPa";
        }

        // Door locks
        String lockStatus = carStatus.getLock();
        if (lockStatus != null) {
            views.setImageViewResource(R.id.lock, lockStatus.equals("LOCKED") ?
                    R.drawable.locked_icon_green : R.drawable.unlocked_icon_red);
        }
/*
        // Ignition and remote start
        String ignition = carStatus.getIgnition();
        Boolean remote = carStatus.getRemoteStartStatus();
        if (remote != null && remote) {
            views.setImageViewResource(R.id.ignition, R.drawable.ignition_icon_yellow);
        } else if (ignition != null) {
            views.setImageViewResource(R.id.ignition, ignition.equals("Off") ?
                    R.drawable.ignition_icon_gray : R.drawable.ignition_icon_green);
        }

        // Motion alarm and deep sleep state
        String alarm = carStatus.getAlarm();
        Boolean sleep = carStatus.getDeepSleep();
        if (sleep != null && sleep) {
            views.setImageViewResource(R.id.alarm, R.drawable.bell_icon_zzz_red);
            views.setTextViewText(R.id.sleep, "Deep sleep: yes");
        } else {
            views.setTextViewText(R.id.sleep, "Deep sleep: no");
            if (alarm != null) {
                views.setImageViewResource(R.id.alarm, alarm.equals("NOTSET") ?
                        R.drawable.bell_icon_red : R.drawable.bell_icon_green);
            } else {
                views.setImageViewResource(R.id.alarm, R.drawable.bell_icon_gray);
            }
        }

        String rangeCharge = "N/A";
        if (fuelType == Utils.FUEL_ELECTRIC) {

            // Estimated range
            Double range = carStatus.getElVehDTE();
            if (range != null && range > 0) {
                rangeCharge = MessageFormat.format("{0} {1}", Math.round(range * distanceConversion), distanceUnits);
            }

            // Charging port
            Boolean pluggedIn = carStatus.getPlugStatus();
            views.setImageViewResource(R.id.plug, pluggedIn ?
                    R.drawable.plug_icon_green : R.drawable.plug_icon_gray);

            // High-voltage battery
            if (pluggedIn) {
                String chargeStatus = carStatus.getChargingStatus();
                switch (chargeStatus) {
                    case CHARGING_STATUS_NOT_READY:
                        views.setImageViewResource(R.id.HVBIcon, R.drawable.battery_icon_red);
                        break;
                    case CHARGING_STATUS_CHARGING_AC:
                    case CHARGING_STATUS_CHARGING_DC:
                        views.setImageViewResource(R.id.HVBIcon, R.drawable.battery_charging);
                        break;
                    case CHARGING_STATUS_TARGET_REACHED:
                    case CHARGING_STATUS_PRECONDITION:
                        views.setImageViewResource(R.id.HVBIcon, R.drawable.battery_icon_charged_green);
                        break;
                    case CHARGING_STATUS_PAUSED:
                        views.setImageViewResource(R.id.HVBIcon, R.drawable.battery_icon_yellow);
                        break;
                    default:
                        views.setImageViewResource(R.id.HVBIcon, R.drawable.battery_icon_gray);
                        break;
                }

                // Normally there will be something from the GOM; if so, display this info below it
                if (!rangeCharge.equals("")) {
                    rangeCharge += "\n";
                }
                if (chargeStatus.equals(CHARGING_STATUS_TARGET_REACHED)) {
                    rangeCharge += "Target Reached";
                } else if (chargeStatus.equals(CHARGING_STATUS_PRECONDITION)) {
                    rangeCharge += "Preconditioning";
                } else {
                    sdf = new SimpleDateFormat(Constants.STATUSTIMEFORMAT, Locale.US);
                    Calendar endChargeTime = Calendar.getInstance();
                    try {
                        endChargeTime.setTime(sdf.parse(carStatus.getVehiclestatus().getChargeEndTime()));

                        Calendar nowTime = Calendar.getInstance();
                        long min = Duration.between(nowTime.toInstant(), endChargeTime.toInstant()).getSeconds() / 60;
                        if (min > 0) {
                            int hours = (int) min / 60;
                            min %= 60;
                            if (hours > 0) {
                                rangeCharge += hours + " hr";
                                if (min > 0) {
                                    rangeCharge += ", ";
                                }
                            }
                            if (min > 0) {
                                rangeCharge += (int) min + " min";
                            }
                            rangeCharge += " left";
                        }
                    } catch (ParseException e) {
                        LogFile.e(context, MainActivity.CHANNEL_ID, "exception in CarStatusWidget.updateAppWidget: ", e);
                    }
                }
            } else {
                views.setImageViewResource(R.id.HVBIcon, R.drawable.battery_icon_gray);
            }
            views.setTextViewText(R.id.GOM, rangeCharge);

            // High-voltage battery charge levels
            Double chargeLevel = carStatus.getHVBFillLevel();
            if (chargeLevel != null) {
                views.setProgressBar(R.id.HBVChargeProgress, 100, (int) Math.round(chargeLevel + 0.5), false);
                views.setTextViewText(R.id.HVBChargePercent,
                        MessageFormat.format("{0}%", new DecimalFormat("#.0", // "#.0",
                                DecimalFormatSymbols.getInstance(Locale.US)).format(chargeLevel)));
            }
        } else {
            // Estimated range
            Double range = carStatus.getDistanceToEmpty();
            if (range != null && range >= 0) {
                appInfo.setLastDTE(VIN, range);
            } else {
                range = appInfo.getLastDTE(VIN);
                if (range == null) {
                    range = -1.0;
                    distanceConversion = 1.0;
                }
            }
            rangeCharge = MessageFormat.format("{0} {1}", Math.round(range * distanceConversion), distanceUnits);
            views.setTextViewText(R.id.distanceToEmpty, rangeCharge);

            // Fuel tank level
            Double fuelLevel = carStatus.getFuelLevel();
            if (fuelLevel != null && fuelLevel >= 0) {
                appInfo.setLastFuelLevel(VIN, fuelLevel);
            } else {
                fuelLevel = appInfo.getLastFuelLevel(VIN);
                if (fuelLevel == null) {
                    fuelLevel = -1.0;
                } else if (fuelLevel > 100.0) {
                    fuelLevel = 100.0;
                }
            }
            views.setProgressBar(R.id.fuelLevelProgress, 100, (int) Math.round(fuelLevel + 0.5), false);
            views.setTextViewText(R.id.fuelLevelPercent,
                    MessageFormat.format("{0}%", new DecimalFormat("#.0", // "#.0",
                            DecimalFormatSymbols.getInstance(Locale.US)).format(fuelLevel)));

            if (carStatus.getVehiclestatus() == null) {
                Toast.makeText(context, "carStatus.getVehiclestatus() is null", Toast.LENGTH_SHORT).show();
            } else if (carStatus.getVehiclestatus().getFuel() == null) {
                Toast.makeText(context, "carStatus.getVehiclestatus().getFuel() is null", Toast.LENGTH_SHORT).show();
            } else {
                if (carStatus.getVehiclestatus().getFuel().getDistanceToEmpty() == null) {
                    Toast.makeText(context, "carStatus.getVehiclestatus().getFuel().getDistanceToEmpty() is null", Toast.LENGTH_SHORT).show();
                }
                if (carStatus.getVehiclestatus().getFuel().getFuelLevel() == null) {
                    Toast.makeText(context, "carStatus.getVehiclestatus().getFuel().getFuelLevel() is null", Toast.LENGTH_SHORT).show();
                }
            }
        }*/

        // 12 volt battery status
        Integer LVBLevel = carStatus.getLVBVoltage();
        String LVBStatus = carStatus.getLVBStatus();
        if (LVBLevel != null && LVBStatus != null) {
            views.setTextColor(R.id.LVBVoltage,
                    context.getColor(LVBStatus.equals("STATUS_GOOD") ? R.color.white : R.color.red));
            views.setTextViewText(R.id.LVBVoltage, MessageFormat.format("LVB Volts: {0}V", LVBLevel));
        } else {
            views.setTextColor(R.id.LVBVoltage, context.getColor(R.color.white));
            views.setTextViewText(R.id.LVBVoltage, MessageFormat.format("LVB Volts: N/A", LVBLevel));
        }

        // Current Odometer reading
        Double odometer = carStatus.getOdometer();
        if (odometer != null && odometer > 0) {
            // FordPass truncates; go figure.
            views.setTextViewText(R.id.odometer,
                    MessageFormat.format("Odo: {0} {1}", Double.valueOf(odometer * distanceConversion).intValue(), distanceUnits));
        } else {
            views.setTextViewText(R.id.odometer, "Odo: ---");
        }

        // Tire pressures
        updateTire(views, carStatus.getLeftFrontTirePressure(), carStatus.getLeftFrontTireStatus(),
                pressureUnits, pressureConversion, R.id.lftire);
        updateTire(views, carStatus.getRightFrontTirePressure(), carStatus.getRightFrontTireStatus(),
                pressureUnits, pressureConversion, R.id.rftire);
        updateTire(views, carStatus.getLeftRearTirePressure(), carStatus.getLeftRearTireStatus(),
                pressureUnits, pressureConversion, R.id.lrtire);
        updateTire(views, carStatus.getRightRearTirePressure(), carStatus.getRightRearTireStatus(),
                pressureUnits, pressureConversion, R.id.rrtire);

/*        // Window statuses
        updateWindow(views, carStatus.getDriverWindow(), R.id.lfwindow, R.drawable.icons8_left_front_window_down_red);
        updateWindow(views, carStatus.getPassengerWindow(), R.id.rfwindow, R.drawable.icons8_right_front_window_down_red);
        updateWindow(views, carStatus.getLeftRearWindow(), R.id.lrwindow, R.drawable.icons8_left_rear_window_down_red);
        updateWindow(views, carStatus.getRightRearWindow(), R.id.rrwindow, R.drawable.icons8_right_rear_window_down_red);*/

        // Get the right images to use for this vehicle
        Map<String, Integer> vehicleImages = Utils.getVehicleDrawables(VIN);
/*

        // Hood, tailgate, and door statuses
        views.setImageViewResource(R.id.hood,
                isDoorClosed(carStatus.getFrunk()) ? R.drawable.filler : vehicleImages.get(Utils.HOOD));
        views.setImageViewResource(R.id.tailgate,
                isDoorClosed(carStatus.getTailgate()) ? R.drawable.filler : vehicleImages.get(Utils.TAILGATE));
        views.setImageViewResource(R.id.lt_ft_door,
                isDoorClosed(carStatus.getDriverDoor()) ? R.drawable.filler : vehicleImages.get(Utils.LEFT_FRONT_DOOR));
        views.setImageViewResource(R.id.rt_ft_door,
                isDoorClosed(carStatus.getPassengerDoor()) ? R.drawable.filler : vehicleImages.get(Utils.RIGHT_FRONT_DOOR));
        views.setImageViewResource(R.id.lt_rr_door,
                isDoorClosed(carStatus.getLeftRearDoor()) ? R.drawable.filler : vehicleImages.get(Utils.LEFT_REAR_DOOR));
        views.setImageViewResource(R.id.rt_rr_door,
                isDoorClosed(carStatus.getRightRearDoor()) ? R.drawable.filler : vehicleImages.get(Utils.RIGHT_REAR_DOOR));
        views.setImageViewResource(R.id.wireframe, vehicleImages.get(Utils.WIREFRAME));
*/

        views.setTextColor(R.id.DataLine2, context.getColor(R.color.white));
        // OTA status
        OTAStatus otaStatus = new StoredData(context).getOTAStatus(VIN);
        boolean displayOTA = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getResources().getString(R.string.show_OTA_key), true);
        int dataLine = 0;
        if (displayOTA && otaStatus != null) {
            // If the report doesn't say the vehicle DOESN'T support OTA, then try to display something
            String OTAAlertStatus = otaStatus.getOtaAlertStatus();
            if (OTAAlertStatus != null && !OTAAlertStatus.toLowerCase().replaceAll("[^a-z0-9]", "").contains("doesntsupport")) {
                views.setTextViewText(R.id.DataLine1, "OTA Status:");
                String OTArefresh;
                long lastOTATime = OTAViewActivity.getLastOTATimeInMillis(context);
                String currentUTCOTATime = otaStatus.getOTADateTime();
                if (currentUTCOTATime == null) {
                    OTArefresh = "Unknown";
                } else {
                    long currentOTATime = OTAViewActivity.convertDateToMillis(currentUTCOTATime);

                    // If there's new information, display that data/time in a different color
                    if (currentOTATime > lastOTATime) {
                        // if OTA failed, show it in red (that means something bad)
                        String OTAResult = otaStatus.getOTAAggregateStatus();
                        if (OTAResult != null && OTAResult.equals("failure")) {
                            views.setTextColor(R.id.DataLine2, context.getColor(R.color.red));
                        } else {
                            views.setTextColor(R.id.DataLine2, context.getColor(R.color.green));
                        }
                        OTArefresh = OTAViewActivity.convertMillisToDate(currentOTATime, new StoredData(context).getTimeFormatByCountry(VIN));
                        Notifications.newOTA(context);
                    } else {
                        OTArefresh = OTAViewActivity.convertMillisToDate(lastOTATime, new StoredData(context).getTimeFormatByCountry(VIN));
                    }
                }
                views.setTextViewText(R.id.DataLine2, PADDING + OTArefresh);
                dataLine += 2;
            }
        }

        // Location
        if (PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(context.getResources().getString(R.string.show_location_key), true)) {
            updateLocation(context, views, carStatus.getLatitude(), carStatus.getLongitude(), dataLine);
            dataLine += 3;
        }

        while (dataLine < 5) {
            views.setTextViewText(new int[]{R.id.DataLine1, R.id.DataLine2, R.id.DataLine3, R.id.DataLine4, R.id.DataLine5}[dataLine], "");
            dataLine += 1;
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void updateAppLogout(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");
        int fuelType = Utils.getFuelType(VIN);

        RemoteViews views = getWidgetView(context);

        // Make sure the left side is visible depending on the widget width
        Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
        onResize(appWidgetOptions, views);

        setCallbacks(context, views);

        // Set background transparency
        setBackground(context, views);

        views.setTextViewText(R.id.profile, new StoredData(context).getProfileName(VIN));

        // Reset everything else
        views.setTextViewText(R.id.lastRefresh, "Not logged in");
        views.setTextViewText(R.id.odometer, "Odo: N/A");
        views.setTextViewText(R.id.sleep, "Deep sleep: N/A");
        views.setTextViewText(R.id.LVBVoltage, "LVB Volts: N/A");
        views.setTextColor(R.id.DataLine2, context.getColor(R.color.white));
        views.setTextViewText(R.id.DataLine1, "");
        views.setTextViewText(R.id.DataLine2, "");
        views.setTextViewText(R.id.DataLine3, "");
        views.setTextViewText(R.id.DataLine4, "");
        views.setTextViewText(R.id.DataLine5, "");

        views.setProgressBar(R.id.HBVChargeProgress, 100, 0, false);
        views.setTextViewText(R.id.HVBChargePercent, "N/A");
        views.setTextViewText(R.id.GOM, "N/A");

        views.setImageViewResource(R.id.ignition, R.drawable.ignition_icon_gray);
        views.setImageViewResource(R.id.lock, R.drawable.locked_icon_gray);
        views.setImageViewResource(R.id.alarm, R.drawable.bell_icon_gray);

        views.setImageViewResource(R.id.lfwindow, R.drawable.filler);
        views.setImageViewResource(R.id.rfwindow, R.drawable.filler);
        views.setImageViewResource(R.id.lrwindow, R.drawable.filler);
        views.setImageViewResource(R.id.rrwindow, R.drawable.filler);

        updateTire(views, null, null, "", 1.0, R.id.lftire);
        updateTire(views, null, null, "", 1.0, R.id.rftire);
        updateTire(views, null, null, "", 1.0, R.id.lrtire);
        updateTire(views, null, null, "", 1.0, R.id.rrtire);

        if (fuelType != Utils.FUEL_GAS) {
            views.setTextColor(R.id.LVBVoltage, context.getColor(R.color.white));
            views.setImageViewResource(R.id.HVBIcon, R.drawable.battery_icon_gray);
            views.setImageViewResource(R.id.plug, R.drawable.plug_icon_gray);
        }

        // Get the wireframe for the vehicle
        Map<String, Integer> vehicleImages = Utils.getVehicleDrawables(VIN);
        views.setImageViewResource(R.id.wireframe, vehicleImages.get(Utils.WIREFRAME));

        views.setImageViewResource(R.id.hood, R.drawable.filler);
        views.setImageViewResource(R.id.tailgate, R.drawable.filler);
        views.setImageViewResource(R.id.lt_ft_door, R.drawable.filler);
        views.setImageViewResource(R.id.rt_ft_door, R.drawable.filler);
        views.setImageViewResource(R.id.lt_rr_door, R.drawable.filler);
        views.setImageViewResource(R.id.rt_rr_door, R.drawable.filler);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void setImageBitmap(RemoteViews views, Drawable icon, int id) {
        int size = Math.min(icon.getIntrinsicWidth(), 96);
        Bitmap bmp = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        icon.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        icon.draw(canvas);
        views.setImageViewBitmap(id, bmp);
    }

    private void setAppBitmap(Context context, RemoteViews views, String appPackageName, int id) {
        try {
            if (appPackageName != null) {
                Drawable icon = context.getApplicationContext().getPackageManager().getApplicationIcon(appPackageName);
                if (icon != null) {
                    setImageBitmap(views, icon, id);
                }
            } else {
                views.setImageViewResource(id, R.drawable.x_gray);
            }
        } catch (PackageManager.NameNotFoundException e) {
            views.setImageViewResource(id, R.drawable.x_gray);
        }
    }

    private void updateLinkedApps(Context context, AppWidgetManager appWidgetManager, int appWidgetId, String VIN) {
        RemoteViews views = getWidgetView(context);

        boolean showAppLinks = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(context.getResources().getString(R.string.show_app_links_key), true);
        if (showAppLinks) {
            setAppBitmap(context, views, new StoredData(context).getLeftAppPackage(VIN), R.id.leftappbutton);
            setAppBitmap(context, views, new StoredData(context).getRightAppPackage(VIN), R.id.rightappbutton);
        } else {
            views.setImageViewResource(R.id.leftappbutton, R.drawable.filler);
            views.setImageViewResource(R.id.rightappbutton, R.drawable.filler);
        }
        appWidgetManager.partiallyUpdateAppWidget(appWidgetId, views);
    }

    private Handler getHandler(Context context) {
        return new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String result = msg.getData().getString("action");
                if (result != null && result.equals(NetworkCalls.COMMAND_SUCCESSFUL)) {
                    awaitingUpdate = true;
                    StatusReceiver.nextAlarm(context, 2);
                } else {
                    awaitingUpdate = false;
                }
                Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void remoteStart(Context context) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");
        NetworkCalls.remoteStart(getHandler(context), context, new StoredData(context).getAccessToken(VIN));
    }

    private void remoteStop(Context context) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");
        NetworkCalls.remoteStop(getHandler(context), context, new StoredData(context).getAccessToken(VIN));
    }

    private void lock(Context context) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");
        NetworkCalls.lockDoors(getHandler(context), context, new StoredData(context).getAccessToken(VIN));
    }

    private void unlock(Context context) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");
        NetworkCalls.unlockDoors(getHandler(context), context, new StoredData(context).getAccessToken(VIN));
    }

    // Make the app widget and icon match the VIN
    private void matchWidgetWithVin(Context context, String VIN) {
        StoredData appInfo = new StoredData(context);

        // Get the current widget mode
        String mode = appInfo.getWidgetMode();

        // Get the mode for this VIN
        String newMode = Utils.getWMI(VIN);

        // If the widget doesn't match the VIN, switch modes
        if (!mode.equals(newMode)) {
            appInfo.setWidgetMode(newMode);
            PackageManager manager = context.getPackageManager();
            String packageName = context.getPackageName();
            String macheActivity = packageName + ".MainActivity";
            manager.setComponentEnabledSetting(new ComponentName(packageName, macheActivity),
                    Utils.isMachE(VIN) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            String f150Activity = packageName + ".F150MainActivity";
            manager.setComponentEnabledSetting(new ComponentName(packageName, f150Activity),
                    Utils.isF150(VIN) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            String broncoActivity = packageName + ".BroncoMainActivity";
            manager.setComponentEnabledSetting(new ComponentName(packageName, broncoActivity),
                    Utils.isBronco(VIN) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
            String explorerActivity = packageName + ".ExplorerMainActivity";
            manager.setComponentEnabledSetting(new ComponentName(packageName, explorerActivity),
                    Utils.isExplorer(VIN) ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED :
                            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                    PackageManager.DONT_KILL_APP);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        StoredData appInfo = new StoredData(context);
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");

        if (!VIN.equals("")) {
            matchWidgetWithVin(context, VIN);
        }

        String state = appInfo.getProgramState(VIN);
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            if (!state.equals(Constants.STATE_INITIAL_STATE)) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
            } else {
                updateAppLogout(context, appWidgetManager, appWidgetId);
            }
            updateLinkedApps(context, appWidgetManager, appWidgetId, VIN);
        }
    }

    private void onResize(Bundle newOptions, RemoteViews views) {
        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
//        Toast.makeText(context, "width = " + minWidth, Toast.LENGTH_SHORT).show();
        if (minWidth < 250) {
            views.setViewVisibility(R.id.leftside, View.GONE);
        } else {
            views.setViewVisibility(R.id.leftside, View.VISIBLE);
        }
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = getWidgetView(context);
        onResize(newOptions, views);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static long lastIgnitionClicktime = 0;
    private static long lastLockClicktime = 0;
    private static final long[] lastRefreshClicktime = {0, 0};

    // Flag to avoid executing a command before we receive a status update.
    private static Boolean awaitingUpdate = false;

    // This is called when SharedPreferences for car status is executed.
    public static void clearAwaitingFlag() {
        awaitingUpdate = false;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(context).getString(context.getResources().getString(R.string.VIN_key), "");

        String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE) && intent.hasExtra(WIDGET_IDS_KEY)) {
            int[] ids = intent.getExtras().getIntArray(WIDGET_IDS_KEY);
            this.onUpdate(context, AppWidgetManager.getInstance(context), ids);
        } else if (action.equals(PROFILE_CLICK)) {
            String alias = ProfileManager.changeProfile(context);
            MainActivity.updateWidget(context);
        } else if (action.equals(WIDGET_CLICK)) {
            String activity;
            switch (new StoredData(context).getWidgetMode()) {
                case Utils.WIDGETMODE_BRONCO:
                    activity = ".BroncoMainActivity";
                    break;
                case Utils.WIDGETMODE_F150:
                    activity = ".F150MainActivity";
                    break;
                case Utils.WIDGETMODE_EXPLORER:
                    activity = ".ExplorerMainActivity";
                    break;
                default:
                    activity = ".MainActivity";
                    break;
            }
            intent = new Intent();
            intent.setComponent(new ComponentName(context.getPackageName(), context.getPackageName() + activity));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if (action.equals(SETTINGS_CLICK)) {
            intent = new Intent(context, SettingsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } else if (action.equals(LEFT_BUTTON_CLICK)) {
            StoredData appInfo = new StoredData(context);
            String appPackageName = appInfo.getLeftAppPackage(VIN);
            if (appPackageName != null) {
                PackageManager pm = context.getApplicationContext().getPackageManager();
                intent = pm.getLaunchIntentForPackage(appPackageName);
                if (intent != null) {
                    context.startActivity(intent);
                } else {
                    appInfo.setLeftAppPackage(VIN, null);
                    MainActivity.updateWidget(context);
                    Toast.makeText(context, "App is no longer installed", Toast.LENGTH_LONG).show();
                }
            }
        } else if (action.equals(RIGHT_BUTTON_CLICK)) {
            StoredData appInfo = new StoredData(context);
            String appPackageName = appInfo.getRightAppPackage(VIN);
            if (appPackageName != null) {
                PackageManager pm = context.getApplicationContext().getPackageManager();
                intent = pm.getLaunchIntentForPackage(appPackageName);
                if (intent != null) {
                    context.startActivity(intent);
                } else {
                    appInfo.setRightAppPackage(VIN, null);
                    MainActivity.updateWidget(context);
                    Toast.makeText(context, "App is no longer installed", Toast.LENGTH_LONG).show();
                }
            }
        } else if (action.equals(LOCK_CLICK)) {
            // Avoid performing the action on a single press (in case the widget is accidentally
            // touched): require two presses within 500 ms of one another to activate.
            LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
            long nowtime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (nowtime - lastLockClicktime < 500) {
                CarStatusCN carStatus = new StoredData(context).getCarStatus(VIN);
                if (!awaitingUpdate && carStatus != null && carStatus.getLock() != null) {
                    if (carStatus.getLock().equals("LOCKED")) {
                        unlock(context);
                    } else {
                        lock(context);
                    }
                }
            }
            lastLockClicktime = nowtime;
/*        } else if (action.equals(IGNITION_CLICK)) {
            LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
            long nowtime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (nowtime - lastIgnitionClicktime < 500) {
                CarStatusCN carStatus = new StoredData(context).getCarStatus(VIN);
                if (!awaitingUpdate && carStatus != null && carStatus.getRemoteStartStatus() != null
                        && carStatus.getIgnition() != null && carStatus.getIgnition().equals("Off")) {
                    if (!carStatus.getRemoteStartStatus()) {
                        remoteStart(context);
                    } else {
                        remoteStop(context);
                    }
                }
            }
            lastIgnitionClicktime = nowtime;*/
        } else if (action.equals(REFRESH_CLICK)) {
            LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
            long nowtime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
            if (nowtime - lastRefreshClicktime[0] < 500) {
                long lastUpdateInMillis = new StoredData(context).getLastUpdateTime(VIN);
                String lastUpdate = OTAViewActivity.convertMillisToDate(lastUpdateInMillis, new StoredData(context).getTimeFormatByCountry(VIN));
                Toast.makeText(context, "Last update at " + lastUpdate, Toast.LENGTH_SHORT).show();
                long lastAlarmInMillis = new StoredData(context).getLastAlarmTime();
                String lastAlarm = OTAViewActivity.convertMillisToDate(lastAlarmInMillis, new StoredData(context).getTimeFormatByCountry(VIN));
                Toast.makeText(context, "Last alarm at " + lastAlarm, Toast.LENGTH_SHORT).show();
            }
            lastRefreshClicktime[0] = lastRefreshClicktime[1];
            lastRefreshClicktime[1] = nowtime;
        } else {
            super.onReceive(context, intent);
        }
    }
}
