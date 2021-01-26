package com.example.hotpapperapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Adapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.hotpapperapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val MY_PERMISSION_REQUEST_FINE_LOCATION = 1
    private lateinit var binding: ActivityMapsBinding

    private lateinit var customAdapter: RestaurantMapRecycerViewAdapter

    private val viewModel: MapViewModel by viewModels {
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
//        layoutManager = LinearLayoutManager(
//            this,
//            LinearLayoutManager.VERTICAL,
//            false
//        )


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
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    when(state){
                        0 -> Timber.i("Timber スクロールを待機")
                        1 -> Timber.i("Timber スクロールが開始した")
                        2 -> Timber.i("Timber 表示する画面が決定された")
                    }

                    // スクロール状態が切り替わると呼ばれる
                    //
                    // SCROLL_STATE_IDLE     = 0; => スクロールを待機
                    // SCROLL_STATE_DRAGGING = 1; => スクロールが開始した
                    // SCROLL_STATE_SETTLING = 2; => 表示する画面が決定された

                }
                // スクロールイベントが発生している最中リアルタイムに呼ばれる
                //
                // position             => 現在の position が渡される
                //                         スクロールが完了して直前と違う position になっているとその値が反映される
                // positionOffset       => スクロール開始前からの相対的な offset 値
                // positionOffsetPixels => スクロール開始前からの相対的な offset 値（ピクセル）
                //
                override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                    Timber.i("Timber onPageScrolled")
                }
                // スクロールが終了すると選択されたページの position を引数にして呼ばれる
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Timber.i("Timber  onPageSelected")
                }
            })
        }

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        binding.apply {
            viewModel = viewModel
            customAdapter.refresh(itemListSnapshot.toMutableList())
            lifecycleOwner = this@MapsActivity
        }


        viewModel.apply {
            loadNext()
            itemList.observe(
                this@MapsActivity,
                Observer { customAdapter.refresh(it.toMutableList()) })
        }


    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        checkPermission()
        mMap.setOnMarkerClickListener { marker ->
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 17F))
            return@setOnMarkerClickListener true
        }
    }

//    private val loadNextScrollListener = object : RecyclerView.OnScrollListener() {
//
//        //スクロールの検知
//        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//            super.onScrolled(recyclerView, dx, dy)
//            Timber.i("Timber onScrolled")
//
//            val visibleItemCount = recyclerView.childCount
//            val totalItemCount = customLayoutManager.itemCount
//            val firstVisibleItemPosition = customLayoutManager.findFirstVisibleItemPosition()
//
//            if (totalItemCount - visibleItemCount <= firstVisibleItemPosition) {
//                viewModel.loadNext()
//            }
//        }
//    }


    private fun myLocation() {
        Timber.i("Timber")

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val position = LatLng(34.702485, 135.495951)

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
}

class ScrollListener(val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    private var previousTotal = 0
    private var loading = true
    private var visibleThreshold = 2
    private var firstVisibleItem = 0
    private var visibleItemCount = 0
    private var totalItemCount = 0

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy > 0) {
            visibleItemCount = recyclerView.childCount;
            totalItemCount = layoutManager.itemCount;
            firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

            if (loading) {
                if (totalItemCount > previousTotal) {
                    loading = false;
                    previousTotal = totalItemCount;
                }
            }
            if (!loading && (totalItemCount - visibleItemCount)
                <= (firstVisibleItem + visibleThreshold /*最後の行から２つ上*/)) {
                // End has been reached
                Timber.i("Timber Scroll")
                loading = true
            }
        }
    }

}
