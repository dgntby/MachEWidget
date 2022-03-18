package com.example.khughes.machewidget;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.format.DateUtils;
import android.util.Log;

import androidx.preference.PreferenceManager;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class StatusReceiver extends BroadcastReceiver {
    private Context mContext;
    private StoredData appInfo;

    @Override
    public void onReceive(Context context, Intent arg1) {
        final int Millis = 1000;

        mContext = context;
        nextAlarm(mContext);

        String VIN = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getResources().getString(R.string.VIN_key), "");
        appInfo = new StoredData(context);
        long timeout = appInfo.getTokenTimeout(VIN);
        LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault());
        long nowtime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // Store time when we run the update;
        appInfo.setLastAlarmTime(VIN);

        ProgramStateMachine.States state = new ProgramStateMachine(appInfo.getProgramState(VIN)).getCurrentState();

        LogFile.d(mContext, MainActivity.CHANNEL_ID, "time is " + (timeout - nowtime) / Millis + ", state is " + state.name() +
                ", battery optimization is " + MainActivity.checkBatteryOptimizations(context));

        // Check whether credentials are being saved
        Boolean savingCredentials = PreferenceManager.getDefaultSharedPreferences(mContext)
                .getBoolean(mContext.getResources().getString(R.string.save_credentials_key), true);

        if (state.equals(ProgramStateMachine.States.INITIAL_STATE)) {
            LogFile.d(mContext, MainActivity.CHANNEL_ID, "Initial state: how'd did the alarm go off (manual refresh)?");
            cancelAlarm(context);
            appInfo.incCounter(StoredData.UGLY);
        } else if (state.equals(ProgramStateMachine.States.ATTEMPT_TO_GET_ACCESS_TOKEN)) {
            if (savingCredentials) {
                LogFile.d(mContext, MainActivity.CHANNEL_ID, "Ok, trying to log in; wish me luck");
                getAccess();
                appInfo.incCounter(StoredData.BAD);
            } else {
                appInfo.setProgramState(VIN, ProgramStateMachine.States.INITIAL_STATE);
                LogFile.d(mContext, MainActivity.CHANNEL_ID, "Log-in required but credentials are not being saved: cancelling alarm");
                cancelAlarm(context);
                appInfo.incCounter(StoredData.UGLY);
            }
        } else if (state.equals(ProgramStateMachine.States.ATTEMPT_TO_GET_VEHICLE_STATUS) ||
                state.equals(ProgramStateMachine.States.HAVE_TOKEN_AND_STATUS)) {
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
            int delayInMillis = new Integer(sharedPref.getString(context.getResources().getString(R.string.update_frequency_key), "10")) * 60 * Millis;

            // Since actions such as "Refresh" don't check the token's expiration, be sure to refresh if it would expire before
            // the next update.
            long calculation = (timeout - delayInMillis - 5 * Millis) - nowtime;
            LogFile.d(mContext, MainActivity.CHANNEL_ID, "Calculating time as " + calculation);
            if (timeout - delayInMillis - 5 * Millis < nowtime) {
                LogFile.d(mContext, MainActivity.CHANNEL_ID, "Need to refresh token");
                getRefresh(appInfo.getRefreshToken(VIN));
            } else {
                LogFile.d(mContext, MainActivity.CHANNEL_ID, "Token good? Just grab info");
                getStatus(appInfo.getAccessToken(VIN));
                getOTAStatus(appInfo.getAccessToken(VIN));
            }
            appInfo.incCounter(StoredData.GOOD);
        }
        // Since everything above should cover all the states, we should never get here
        else {
            if (savingCredentials) {
                LogFile.d(mContext, MainActivity.CHANNEL_ID, "Hmmm... How did I get here. Trying to login");
                getAccess();
            } else {
                LogFile.d(mContext, MainActivity.CHANNEL_ID, "Hmmm... How did I get here. Wish I could login");
            }
            appInfo.incCounter(StoredData.UGLY);
        }
        int good = appInfo.getCounter(StoredData.GOOD);
        int bad = appInfo.getCounter(StoredData.BAD);
        int ugly = appInfo.getCounter(StoredData.UGLY);

        LogFile.d(mContext, MainActivity.CHANNEL_ID, "good = " + good + ", bad = " + bad + ", ugly = " + ugly);
    }

    private Bundle bb = new Bundle();

    private void getAccess() {
        String VIN = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getResources().getString(R.string.VIN_key), "");
        Handler h = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                bb = msg.getData();
                String xx = bb.getString("action");
                ProgramStateMachine.States action = ProgramStateMachine.States.valueOf(bb.getString("action"));
                LogFile.i(mContext, MainActivity.CHANNEL_ID, "Access: " + action);
                appInfo.setProgramState(VIN, action);
                if (action.equals(ProgramStateMachine.States.HAVE_TOKEN_AND_STATUS)) {
                    String accessToken = bb.getString("access_token");
                    String refreshToken = bb.getString("refresh_token");
                    int expires = bb.getInt("expires", 0);
                    LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(expires);
                    long nextTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    appInfo.setTokenInfo(VIN, accessToken, refreshToken, nextTime);
                    getStatus(accessToken);
                    getOTAStatus(accessToken);
                }
            }
        };
        NetworkCalls.getAccessToken(h, mContext, appInfo.getUsername(VIN), appInfo.getPassword(VIN));
    }

    private void getRefresh(String refreshToken) {
        Handler h = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                String VIN = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getResources().getString(R.string.VIN_key), "");
                bb = msg.getData();
                ProgramStateMachine.States action = ProgramStateMachine.States.valueOf(bb.getString("action"));
                LogFile.i(mContext, MainActivity.CHANNEL_ID, "Refresh: " + action);
                appInfo.setProgramState(VIN, action);
                if (action.equals(ProgramStateMachine.States.ATTEMPT_TO_GET_VEHICLE_STATUS)) {
                    String accessToken = bb.getString("access_token");
                    String refreshToken = bb.getString("refresh_token");
                    int expires = bb.getInt("expires", 0);
                    LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(expires);
                    long nextTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                    appInfo.setTokenInfo(VIN, accessToken, refreshToken, nextTime);
                    getStatus(accessToken);
                    getOTAStatus(accessToken);
                } else if (action.equals(ProgramStateMachine.States.INITIAL_STATE)) {
                    getAccess();
                }
            }
        };
        NetworkCalls.refreshAccessToken(h, mContext, refreshToken);
    }

    private void getStatus(String accessToken) {
        String VIN = PreferenceManager.getDefaultSharedPreferences(mContext).getString(mContext.getResources().getString(R.string.VIN_key), "");
        Handler h = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                bb = msg.getData();
                ProgramStateMachine.States action = ProgramStateMachine.States.valueOf(bb.getString("action"));
                LogFile.i(mContext, MainActivity.CHANNEL_ID, "Status: " + action);
                appInfo.setProgramState(VIN, action);
                if (action.equals(ProgramStateMachine.States.HAVE_TOKEN_AND_STATUS)) {
                    MainActivity.updateWidget(mContext);
                }
            }
        };
        NetworkCalls.getStatus(h, mContext, accessToken);
    }

    private void getOTAStatus(String accessToken) {
        Handler h = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                MainActivity.updateWidget(mContext);
            }
        };
        NetworkCalls.getOTAStatus(h, mContext, accessToken);
    }

    public static void nextAlarm(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        int delay = new Integer(sharedPref.getString(context.getResources().getString(R.string.update_frequency_key), "10"));
        if (delay != 0) {
            nextAlarm(context, delay * 60);
        }
    }

    private static Intent getIntent(Context context) {
        return new Intent(context, StatusReceiver.class).setAction("StatusReceiver");
    }

    public static void nextAlarm(Context context, int delay) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        LocalDateTime time = LocalDateTime.now(ZoneId.systemDefault()).plusSeconds(delay);
        String timeText = time.format(DateTimeFormatter.ofPattern("MM/dd HH:mm:ss", Locale.US));
        LogFile.i(context, MainActivity.CHANNEL_ID, "next status alarm at " + timeText);
        long nextTime = time.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                getIntent(context), PendingIntent.FLAG_IMMUTABLE);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, nextTime, pendingIntent);
        alarmManager.setWindow(AlarmManager.RTC_WAKEUP, nextTime, DateUtils.SECOND_IN_MILLIS, pendingIntent);
    }

    public static void cancelAlarm(Context context) {
        final PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0,
                getIntent(context), PendingIntent.FLAG_IMMUTABLE);
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
        Log.d(MainActivity.CHANNEL_ID, "cancelling status alarm");
    }

    // If no alarm is pending, start one
    @SuppressLint("NewApi")
    public static void initateAlarm(Context context) {
        final AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (PendingIntent.getBroadcast(context, 0,
                getIntent(context), PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE) == null) {
            nextAlarm(context);
        } else {
            Log.d(MainActivity.CHANNEL_ID, "no pending status alarm");
        }
    }
}
