package com.hos_dvk.easyphone

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.role.RoleManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioAttributes
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.provider.Telephony
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.hos_dvk.easyphone.module_activity.ContactActivity
import com.hos_dvk.easyphone.module_activity.DialerActivity
import com.hos_dvk.easyphone.module_activity.PhotoChoiceActivity
import com.hos_dvk.easyphone.module_activity.SmsListActivity





//--------------------MainActivity-------------------------
class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createNotificationChannel()

        //------------------Permissions------------------------------
        var appDefaultSMS = false
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            if (Telephony.Sms.getDefaultSmsPackage(this) != null) {
                appDefaultSMS = (Telephony.Sms.getDefaultSmsPackage(this) == packageName)
            }
        } else {
            val roleManager = getSystemService(RoleManager::class.java)
            if (roleManager.isRoleAvailable(RoleManager.ROLE_SMS)) {
                appDefaultSMS = roleManager.isRoleHeld(RoleManager.ROLE_SMS)
            }
        }
        val requiredReadContactPermission = Manifest.permission.READ_CONTACTS
        val requiredWriteContactPermission = Manifest.permission.WRITE_CONTACTS
        val requiredReadSmsPermission = Manifest.permission.READ_SMS
        val requiredReceiveSmsPermission = Manifest.permission.RECEIVE_SMS
        val requiredSendSmsPermission = Manifest.permission.SEND_SMS
        val requiredCallPermission = Manifest.permission.CALL_PHONE
        val requestReadExternalStoragePermission = Manifest.permission.READ_EXTERNAL_STORAGE
        val requestWriteExternalStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        val requestCameraPermission = Manifest.permission.CAMERA
        val checkValReadContact = checkSelfPermission(requiredReadContactPermission)
        val checkValWriteContact = checkSelfPermission(requiredWriteContactPermission)
        val checkValReadSms = checkSelfPermission(requiredReadSmsPermission)
        val checkValReceiveSms = checkSelfPermission(requiredReceiveSmsPermission)
        val checkValSendSms = checkSelfPermission(requiredSendSmsPermission)
        val checkValCall = checkSelfPermission(requiredCallPermission)
        val checkValReadExternalStorage = checkSelfPermission(requestReadExternalStoragePermission)
        val checkValWriteExternalStorage =
            checkSelfPermission(requestWriteExternalStoragePermission)
        val checkValCamera = checkSelfPermission(requestCameraPermission)
        if (checkValReadContact == PackageManager.PERMISSION_GRANTED &&
            checkValWriteContact == PackageManager.PERMISSION_GRANTED &&
            checkValReadSms == PackageManager.PERMISSION_GRANTED &&
            checkValReceiveSms == PackageManager.PERMISSION_GRANTED &&
            checkValSendSms == PackageManager.PERMISSION_GRANTED &&
            checkValCall == PackageManager.PERMISSION_GRANTED &&
            checkValReadExternalStorage == PackageManager.PERMISSION_GRANTED &&
            checkValWriteExternalStorage == PackageManager.PERMISSION_GRANTED &&
            checkValCamera == PackageManager.PERMISSION_GRANTED && appDefaultSMS
        ) {
            Toast.makeText(
                this,
                getString(R.string.accept_permission),
                Toast.LENGTH_LONG
            ).show()
        } else {
            val permissionAll = 1
            val permissions: Array<String> = arrayOf(
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_SMS,
                Manifest.permission.SEND_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.CAMERA
            )

            if (!hasPermissions(this, *permissions)) {
                ActivityCompat.requestPermissions(this, permissions, permissionAll)
            } else {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
                    startActivityForResult(intent, 45)
                } else {
                    val roleManager = getSystemService(RoleManager::class.java)
                    val roleRequestIntent =
                        roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
                    startActivityForResult(roleRequestIntent, 45)
                }
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val attributes: AudioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();

            val name: CharSequence = "newSms"
            val description = "Notification de Nouveau(x) Message(s)"
            val importance = NotificationManager.IMPORTANCE_HIGH
            channel = NotificationChannel("SMS", name, importance)
            channel?.description = "Notification de Nouveau(x) Message(s)"
            channel?.setSound(Settings.System.DEFAULT_NOTIFICATION_URI, attributes)
            channel?.enableVibration(true)
            val notificationManager = getSystemService(
                Context.NOTIFICATION_SERVICE
            ) as NotificationManager
            notificationManager.createNotificationChannel(channel!!)
        }
    }

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    //----------------basic function-------------------------

    fun dialer(@Suppress("UNUSED_PARAMETER") view: View) {
        Toast.makeText(this, getString(R.string.module_dialer), Toast.LENGTH_LONG).show()
        val intent = Intent(this, DialerActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun contact(@Suppress("UNUSED_PARAMETER") view: View) {
        Toast.makeText(this, getString(R.string.module_contacts), Toast.LENGTH_LONG).show()
        val requiredPermission = Manifest.permission.READ_CONTACTS
        val checkVal = checkSelfPermission(requiredPermission)
        if (checkVal == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, ContactActivity::class.java)
            startActivity(intent)
        } else if (!firstContacts) {
            Toast.makeText(
                this,
                getString(R.string.refuse_contact),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun sms(@Suppress("UNUSED_PARAMETER") view: View) {
        Toast.makeText(this, getString(R.string.module_sms), Toast.LENGTH_LONG).show()
        val requiredPermissionReceive = Manifest.permission.RECEIVE_SMS
        val requiredPermissionSend = Manifest.permission.SEND_SMS
        val requiredPermissionRead = Manifest.permission.READ_SMS
        val checkValReceived = checkSelfPermission(requiredPermissionReceive)
        val checkValSend = checkSelfPermission(requiredPermissionSend)
        val checkValRead = checkSelfPermission(requiredPermissionRead)
        if (checkValRead == PackageManager.PERMISSION_GRANTED && checkValReceived == PackageManager.PERMISSION_GRANTED && checkValSend == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(this, SmsListActivity::class.java)
            startActivity(intent)
        } else if (!firstContacts) {
            Toast.makeText(
                this,
                getString(R.string.refuse_contact),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun photo(@Suppress("UNUSED_PARAMETER") view: View) {
        Toast.makeText(this, getString(R.string.module_photo), Toast.LENGTH_LONG).show()
        val intent = Intent(this, PhotoChoiceActivity::class.java)
        startActivity(intent)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun goToOthersLaunchers(@Suppress("UNUSED_PARAMETER") view: View) {
        packageManager.clearPackagePreferredActivities(packageName)
        val i = Intent(Intent.ACTION_MAIN)
        i.addCategory(Intent.CATEGORY_HOME)
        startActivity(i)
    }

    fun systemSettings(@Suppress("UNUSED_PARAMETER") view: View) {
        val mySystemSettings = Intent(Settings.ACTION_SETTINGS)
        startActivity(mySystemSettings)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            45 -> {
                Toast.makeText(
                    this,
                    getString(R.string.accept_permission),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    val intent = Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT)
                    intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, packageName)
                    startActivityForResult(intent, 45)
                } else {
                    val roleManager = getSystemService(RoleManager::class.java)
                    val roleRequestIntent =
                        roleManager.createRequestRoleIntent(RoleManager.ROLE_SMS)
                    startActivityForResult(roleRequestIntent, 45)
                }
            }
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}



