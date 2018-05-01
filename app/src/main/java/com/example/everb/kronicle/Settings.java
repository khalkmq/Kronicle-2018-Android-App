package com.example.everb.kronicle;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import android.view.MenuItem;
import android.widget.Toast;

public class Settings extends AppCompatPreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
    }

    public static class MainPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_main);

            Preference myPref = findPreference(getString(R.string.key_send_feedback));
            myPref.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                public boolean onPreferenceClick(Preference preference) {
                    sendFeedback(getActivity());
                    return true;
                }
            });

            Preference notificationSwitch = findPreference(getString(R.string.notifications_new_message));
            notificationSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object boolChoice) {
                    notifications(getActivity(), boolChoice);
                    return true;
                }
            });

        }
    }

    public static void notifications(Context context, Object boolChoice) {

        boolean choice = (boolean) boolChoice;

        if (choice) {
            NotificationManager notification=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = new Notification.Builder
                    (context).setContentTitle("NOTIFICATION").setContentText("THIS WORKS IDK").
                    setContentTitle("KRONICLE").setSmallIcon(R.drawable.k_simple).build();

            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notification.notify(0, notify);
        }

        else {
            Toast.makeText(context, "You will stop receiving notifications", Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void sendFeedback(Context context) {
        String body = null;
        try {
            body = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            body = "\nPlease don't remove the following information\n Device OS: Android \n Device OS version: " +
                    Build.VERSION.RELEASE + "\n App Version: " + body + "\n Device Brand: " + Build.BRAND +
                    "\n Device Model: " + Build.MODEL + "\n Device Manufacturer: " + Build.MANUFACTURER;
        }

        catch (PackageManager.NameNotFoundException e) {
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kronicle@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "feedback for Kronicle");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(intent, context.getString(R.string.choose_email_client)));
    }
}
