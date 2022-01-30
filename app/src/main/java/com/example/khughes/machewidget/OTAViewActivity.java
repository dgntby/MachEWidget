package com.example.khughes.machewidget;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class OTAViewActivity extends AppCompatActivity {

    public static String convertDate(String UTCdate, String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.OTATIMEFORMAT, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            cal.setTime(sdf.parse(UTCdate));
            sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            return sdf.format(cal.getTime());
        } catch (ParseException e) {
            Log.e(MainActivity.CHANNEL_ID, "exception in OTAViewActivity.convertDate: ", e);
            return "";
        }
    }
    public static long convertDateToMillis(String UTCdate) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.OTATIMEFORMAT, Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        try {
            cal.setTime(sdf.parse(UTCdate));
            return cal.toInstant().toEpochMilli();
        } catch (ParseException e) {
            Log.e(MainActivity.CHANNEL_ID, "exception in OTAViewActivity.convertDateToMillis: ", e);
            return 0;
        }
    }

    public static String convertMillisToDate(long millis, String format) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
        cal.setTimeInMillis(millis);
        return sdf.format(cal.getTime());
    }

    public static long getLastOTATimeInMillis(Context context) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        String lastOTATime = sharedPref.getString(context.getResources().getString(R.string.last_ota_time), "0");
        if(lastOTATime.contains(":")) {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(new StoredData(context).getTimeFormatByCountry(), Locale.ENGLISH);
            try {
                cal.setTime(sdf.parse(lastOTATime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return cal.toInstant().toEpochMilli();
        } else {
            return Long.valueOf(lastOTATime);
        }
    }

    private Long currentOTATime = Long.valueOf(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otaview);

        Context context = getApplicationContext();

        OTAStatus ota = new StoredData(context).getOTAStatus();
        String unencodedHtml = "<html><body>";
        String dateFormat = new StoredData(context).getTimeFormatByCountry();
        if (ota != null && ota.getFuseResponse() != null) {
            String tmp;
            unencodedHtml += "<b>Alert status:</b> " + ((tmp = ota.getOtaAlertStatus()) != null ? tmp : "") + "<p>";
            for (OTAStatus.FuseResponse__1 fuse : ota.getFuseResponse().getFuseResponseList()) {
                unencodedHtml += "<ul>";
                unencodedHtml += "<li><b>CorrelationID:</b> " + ((tmp = fuse.getOemCorrelationId()) != null ? tmp : "");
                unencodedHtml += "<li><b>Created:</b> " + convertDate(fuse.getDeploymentCreationDate(), dateFormat);
                unencodedHtml += "<li><b>Expiration:</b> " + convertDate(fuse.getDeploymentExpirationTime(), dateFormat);
                unencodedHtml += "<li><b>Priority:</b> " + ((tmp = fuse.getCommunicationPriority()) != null ? tmp : "");
                unencodedHtml += "<li><b>Type:</b> " + ((tmp = fuse.getType()) != null ? tmp : "");
                unencodedHtml += "<li><b>Final Acton:</b> \"" + ((tmp = fuse.getDeploymentFinalConsumerAction()) != null ? tmp : "") + "\"";
                if (fuse.getLatestStatus() != null) {
                    currentOTATime = convertDateToMillis(fuse.getLatestStatus().getDateTimestamp());
                    unencodedHtml += "<li><b>Latest status: </b>" + ((tmp = fuse.getLatestStatus().getAggregateStatus()) != null ? tmp : "") + "<ul>";
                    unencodedHtml += "<li><b>Details:</b> " + ((tmp = fuse.getLatestStatus().getDetailedStatus()) != null ? tmp : "");
                    unencodedHtml += "<li><b>Time Stamp:</b> " + currentOTATime;
                    unencodedHtml += "</ul></li>";
                }
                unencodedHtml += "</ul><hr>";
            }
            String description = ota.getDescription();
            if (description != null) {
                unencodedHtml += "<b>Description:</b><p>" + description.replace("\n", "<br>") + "<hr>";
            }

        } else {
            unencodedHtml += "OTA Status is <i>unavailable</i>";
        }

        unencodedHtml += "</body></html>";

        String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                Base64.NO_PADDING);
        WebView mWebView = findViewById(R.id.ota_webview);
        int nightModeFlags = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES && WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
            WebSettingsCompat.setForceDark(mWebView.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
        }
        mWebView.loadData(encodedHtml, "text/html", "base64");

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        Long lastOTATime = getLastOTATimeInMillis(getApplicationContext());

        Button clear = findViewById(R.id.button);
        clear.setEnabled( currentOTATime > lastOTATime);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentOTATime > 0) {
                    SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    sharedPref.edit().putString(context.getResources().getString(R.string.last_ota_time), currentOTATime.toString()).apply();
                    MainActivity.updateWidget(context);
                    clear.setEnabled(false);
                }
            }
        });
    }
}
