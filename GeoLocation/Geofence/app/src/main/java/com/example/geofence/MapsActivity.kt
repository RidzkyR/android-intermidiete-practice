package com.example.geofence

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import android.Manifest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.geofence.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.CircleOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val centerLat = 37.4274745
    private val centerLng = -122.169719
    private val geofenceRadius = 400.0

    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) Toast.makeText(this@MapsActivity, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this@MapsActivity, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        if (Build.VERSION.SDK_INT >= 33) requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.uiSettings.isZoomControlsEnabled = true

        val stanfold = LatLng(centerLat, centerLng)
        mMap.addMarker(MarkerOptions().position(stanfold).title("Stanfold University"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stanfold, 15f))

        mMap.addCircle(
            CircleOptions()
                .center(stanfold)
                .radius(geofenceRadius)
                .fillColor(0x22FF0000)
                .strokeColor(Color.RED)
                .strokeWidth(3f)
        )

        getLocation()
    }

    private fun getLocation(){

    }

    private val requestBackgroundLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                getMyLocation()
            }
        }

    private val runningQOrLater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q
    @TargetApi(Build.VERSION_CODES.Q)
    private val requestLocationPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                if (runningQOrLater) {
                    requestBackgroundLocationPermissionLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                } else {
                    getMyLocation()
                }
            }
        }
    private fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
    @TargetApi(Build.VERSION_CODES.Q)
    private fun checkForegroundAndBackgroundLocationPermission(): Boolean {
        val foregroundLocationApproved = checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val backgroundPermissionApproved =
            if (runningQOrLater) {
                checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            } else {
                true
            }
        return foregroundLocationApproved && backgroundPermissionApproved
    }
    @SuppressLint("MissingPermission")
    private fun getMyLocation() {
        if (checkForegroundAndBackgroundLocationPermission()) {
            mMap.isMyLocationEnabled = true
        } else {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }
}