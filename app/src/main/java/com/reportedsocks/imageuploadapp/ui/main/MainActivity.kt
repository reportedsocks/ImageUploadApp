package com.reportedsocks.imageuploadapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.reportedsocks.imageuploadapp.R
import com.reportedsocks.imageuploadapp.ui.savedlinks.SavedLinksFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_activity.view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //set init toolbar parameters
        val toolbar = toolbar
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        toolbar.title = "ImageUploadApp"
        toolbar.toolbar_nav_button.setOnClickListener(View.OnClickListener {
            val fragmentTransaction = this.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, SavedLinksFragment())
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        })

        if (savedInstanceState == null) {
            if(checkForStoragePermission()){
                transitionFragment()
            } else {
                requestStoragePermission()
            }
        }

    }

    // will receive user's response on permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == 123) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                transitionFragment()
            } else {
                Toast.makeText(this,
                    "Now you have to configure the permission in settings ot restart the app",
                    Toast.LENGTH_LONG).show()
            }
        }
    }

    // request permission, Api was checked in prev method already
    @SuppressLint("NewApi")
    private fun requestStoragePermission() {

        if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // providing very convincing rationale in snackbar...
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 123)
        }
    }

    //check if permission is available
    private fun checkForStoragePermission(): Boolean {
        // no need to ask for this permission on old Api
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission( Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        }
        return true
    }
    private fun transitionFragment(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitNow()
    }

}
