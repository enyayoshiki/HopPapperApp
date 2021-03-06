package com.example.hotpapperapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.hotpapperapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val MY_PERMISSION_REQUEST_FINE_LOCATION = 1
    private lateinit var binding: ActivityMapsBinding

    private lateinit var customAdapter: RestaurantMapRecycerViewAdapter

    private val viewModel: MainViewModel by viewModels {
        ViewModelFactory(
            lifecycleScope
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.i("Test")
        val itemListSnapshot = viewModel.itemList.value ?: listOf<Shop>().also {
            viewModel.itemList.value = it
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps)
        customAdapter = RestaurantMapRecycerViewAdapter()


        binding.restaurantViewPagerMap.apply {

            //左右を少しはみ出して表示させる Item_RecyclerViewの左右にはmarginを設定する
            offscreenPageLimit = 1
            val recyclerView = getChildAt(0) as RecyclerView
            recyclerView.apply {
                setPadding(50, 0, 50, 0)
                clipToPadding = false
            }
            adapter = customAdapter

            Timber.i("Timber")
        }


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        binding.apply {
            viewModel = viewModel
            customAdapter.refresh(itemListSnapshot.toMutableList())
            lifecycleOwner = this@MapsActivity
        }




    }

    override fun onStart() {
        super.onStart()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermission()
        mMap.setOnMarkerClickListener { marker ->
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 17F))
            return@setOnMarkerClickListener true
        }
    }

    private fun myLocation() {
        Timber.i("Timber")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val position = LatLng(34.702485, 135.495951)

//            val resources: Resources = this.resources
//            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_pin_drop_24)

            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.addMarker(
                MarkerOptions()
                    .title("大阪駅")
                    .snippet("JR西日本 大阪環状線の駅")
                    .position(position)
                    .alpha(1.0f)
                    .rotation(0.0f)
                    .draggable(false)
                    .flat(false)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_baseline_pin_drop_24))
                    .zIndex(2.0f)
            )
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17F))
        }
    }


    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            myLocation()
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_REQUEST_FINE_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_REQUEST_FINE_LOCATION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSION_REQUEST_FINE_LOCATION -> {
                if (permissions.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    myLocation()
                }
            }
            else -> {
                showToast("現在位置は表示できません")
            }
        }
    }

    private fun showToast(msg: String) {
        val toast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        toast.show()
    }

    companion object {
        fun startMapsActivity(context: Context) {
            val intent = Intent(context, MapsActivity::class.java)
            context.startActivity(intent)
        }
    }
}


