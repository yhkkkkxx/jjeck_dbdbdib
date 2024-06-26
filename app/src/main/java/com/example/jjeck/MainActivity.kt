package com.example.jjeck

import android.Manifest
import android.R.attr.data
import android.R.attr.text
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.android.volley.Request
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import kotlinx.android.synthetic.main.activity_main.card_view
import org.json.JSONArray
import org.json.JSONObject
import java.util.Locale


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val pERMISSION_ID = 42
    lateinit var mFusedLocationClient: FusedLocationProviderClient
    lateinit var language: String
    lateinit var mMap: GoogleMap

    var textView: TextView? = null
    var button: Button? = null

    // Current location is set to seoul, this will be of no use
    var currentLocation: LatLng = LatLng(37.0, 126.0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        language = intent.getStringExtra("lang").toString()
        setAppLocale(this, language) //언어변경


        setContentView(R.layout.activity_main)

        val btn_res = findViewById<Button>(R.id.button_res)
        btn_res.setOnClickListener {//여기에 식당 보여주면 될듯


        }
        val btn_accom = findViewById<Button>(R.id.button_acc)
        btn_accom.setOnClickListener {//여기에 숙박

        }
        // Fetching API_KEY which we wrapped
        val ai: ApplicationInfo = applicationContext.packageManager
            .getApplicationInfo(applicationContext.packageName, PackageManager.GET_META_DATA)
        val value = ai.metaData["com.google.android.geo.API_KEY"]
        val apiKey = value.toString()

        // Initializing the Places API with the help of our API_KEY
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, apiKey)
        }

        // Initializing Map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id} , ${place.latLng}")

                currentLocation = LatLng(place.latLng.latitude, place.latLng.longitude)

                mMap.clear()
                mMap.addMarker(MarkerOptions().position(currentLocation).title(place.name)
                    .snippet(
                            "PHONE NUMBER : ${place.phoneNumber}" +
                            "Lating : ${place.latLng}"
                            ))


                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))


            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
        // Adding functionality to the button
        val btn = findViewById<ImageButton>(R.id.currentLoc)
        btn.setOnClickListener {
            getLastLocation()
        }
    }

    fun sendRequest() {
        val url = "http://10.0.2.2/accom_info_${language}.php"

        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response: String ->
                val accom_Arr = JSONArray(response)

                for(i in 0 until 50) {
                    if(language.toString() == "ko") {
                        val accom_name = accom_Arr.getJSONObject(i).getString("accom_name")
                        val accom_addr = accom_Arr.getJSONObject(i).getString("accom_addr")
                        val accom_parkinglot =
                            accom_Arr.getJSONObject(i).getString(("accom_parkinglot"))
                        val accom_wifi = accom_Arr.getJSONObject((i)).getString("accom_wifi")

                        //35.8147, 127.1526
                        if (!(geoCoding(accom_addr).latitude.toDouble() in 35.8127..35.8167 && geoCoding(
                                accom_addr
                            ).longitude.toDouble() in 127.1506..127.1546)
                        )
                            continue

                        val markerOption = MarkerOptions()
                        markerOption.position(
                            LatLng(
                                geoCoding(accom_addr).latitude,
                                geoCoding(accom_addr).longitude
                            )
                        )
                        val marker: Marker = mMap.addMarker((markerOption))
                        marker.tag =
                            accom_name + "/" +
                                    accom_addr + "/" +
                                    accom_parkinglot + "/" +
                                    accom_wifi
                    }
                    else {
                        val accom_name = accom_Arr.getJSONObject(i).getString("accom_name")
                        val accom_addr = accom_Arr.getJSONObject(i).getString("accom_addr")
                        val accom_kor_addr = accom_Arr.getJSONObject(i).getString(("accom_kor_addr"))
                        val accom_parkinglot = accom_Arr.getJSONObject(i).getString(("accom_parkinglot"))
                        val accom_wifi = accom_Arr.getJSONObject((i)).getString("accom_wifi")

                        //35.8147, 127.1526
                        if (!(geoCoding(accom_kor_addr).latitude.toDouble() in 35.8127..35.8167 && geoCoding(accom_kor_addr).longitude.toDouble() in 127.1506..127.1546)
                        )
                            continue

                        val markerOption = MarkerOptions()
                        markerOption.position(
                            LatLng(
                                geoCoding(accom_kor_addr).latitude,
                                geoCoding(accom_kor_addr).longitude
                            )
                        ).title(accom_name.toString()).alpha(0.5f)
                        val marker: Marker = mMap.addMarker((markerOption))
                        marker.tag =
                            accom_name + "/" +
                                    accom_addr + "/" +
                                    accom_parkinglot + "/" +
                                    accom_wifi
                    }
                }
            }
        ) { error: VolleyError -> textView!!.text = "error: ${error.message}" }

        queue.add(stringRequest)
    }

    fun geoCoding(address: String): Location {
        return try {
            Geocoder(applicationContext, Locale.KOREA).getFromLocationName(address, 1)?.let {
                Location("").apply {
                    latitude = it[0].latitude
                    longitude = it[0].longitude
                }
            }?: Location("").apply {
                latitude = 0.0
                longitude = 0.0
            }
        }catch (e:Exception) {
            e.printStackTrace()
            geoCoding(address)
        }
    }

    // Services such as getLastLocation()
    // will only run once map is ready
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        card_view.visibility = View.GONE
        val jeonju = LatLng(35.8147, 127.1526)
        val position = CameraPosition.Builder().target(jeonju).zoom(16f).build()
        mMap.addMarker(MarkerOptions().position(jeonju))
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(position))

        sendRequest()

        googleMap!!.setOnMarkerClickListener(object : GoogleMap.OnMarkerClickListener {
            override fun onMarkerClick(marker: Marker) : Boolean {
                if(marker.title == null) {
                    var accom_Name = findViewById<TextView>(R.id.accom_name)
                    var accom_Addr = findViewById<TextView>(R.id.accom_addr)
                    var accom_Parkinglot = findViewById<TextView>(R.id.accom_parkinglot)
                    var accom_Wifi = findViewById<TextView>(R.id.accom_wifi)

                    var tags = marker.tag.toString().split("/")

                    accom_Name.text = tags[0]
                    accom_Addr.text = tags[1]
                    accom_Parkinglot.text = tags[2]
                    accom_Wifi.text = tags[3]

                    card_view.visibility = View.VISIBLE
                }

                return false
            }
        })

        googleMap!!.setOnMapClickListener(object : GoogleMap.OnMapClickListener {
            override fun onMapClick(latLng: LatLng) {
                card_view.visibility = View.GONE
            }
        })
    }

    // Get current location
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {

                mFusedLocationClient.lastLocation.addOnCompleteListener(this) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        requestNewLocationData()
                    } else {
                        currentLocation = LatLng(location.latitude, location.longitude)
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(currentLocation))
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16F))
                    }
                }
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermissions()
        }
    }

    // Get current location, if shifted
    // from previous location
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    // If current location could not be located, use last location
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation: Location? = locationResult.lastLocation
            if (mLastLocation != null) {
                currentLocation = LatLng(mLastLocation.latitude, mLastLocation.longitude)
            }
        }
    }

    // function to check if GPS is on
    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    // Check if location permissions are
    // granted to the application
    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    // Request permissions if not granted before
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            pERMISSION_ID
        )
    }

    // What must happen when permission is granted
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == pERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                getLastLocation()
            }
        }
    }
    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.configuration.setLocale(locale)
    }
}