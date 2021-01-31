package com.example.hotpapperapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.hotpapperapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import timber.log.Timber


class MapsFragment : Fragment(R.layout.activity_maps)  {

    private lateinit var mMap: GoogleMap
    private val MY_PERMISSION_REQUEST_FINE_LOCATION = 1
    private lateinit var binding: ActivityMapsBinding

    private val viewModel: MainViewModel by activityViewModels {
        ViewModelFactory(
            lifecycleScope
        )
    }


    private lateinit var customAdapter: RestaurantMapRecycerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        MapsActivity.startMapsActivity(requireContext())

        Timber.i("Test")
        val itemListSnapshot = viewModel.itemList.value ?: listOf<Shop>().also {
            viewModel.itemList.value = it
        }

        binding = DataBindingUtil.bind(view) ?: return
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
            registerOnPageChangeCallback(object  : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    Timber.d("onPageSelected position:$position")
                }
            })

            Timber.i("Timber")
        }


        //SupportMapFragmentを設定
        initMap()



        binding.apply {
            viewModel = viewModel
            customAdapter.refresh(itemListSnapshot.toMutableList())
            lifecycleOwner = requireActivity()
        }


        viewModel.apply {
            loadNext()
            itemList.observe(
                requireActivity(),
                Observer { customAdapter.refresh(it.toMutableList()) })
        }


    }

    private fun initMap() {

        requireActivity().supportFragmentManager.beginTransaction()
            //layoutファイルを設定し、マップを立ち上げる
            .add(R.id.map, SupportMapFragment.newInstance().apply {
                //Mapの準備、完成次第、パーミッションを選択させる
                getMapAsync {
                    mMap = it
                    checkPermission()
                    mMap.setOnMarkerClickListener { marker ->
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.position, 17F))
                        return@setOnMarkerClickListener true
                    }
                    mMap.setPadding(0, 0, 0, 400)
                }
            })
            .commit()
    }



    //位置情報取得の許可を得る
    private fun checkPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //許可を得たら、マップの描写を変更、現在地をカメラに設定させる
            myLocation()
        } else {
            requestLocationPermissions()
        }
    }

    //現在地の設定、現在地取得ボタンなどUIを変更させる
    private fun myLocation() {
        Timber.i("Timber")

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val position = LatLng(34.702485, 135.495951)

//            val resources: Resources = this.resources
//            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_pin_drop_24)

            mMap.apply {
                isMyLocationEnabled = true
                uiSettings.apply {
                    isMyLocationButtonEnabled = true
                    isZoomControlsEnabled = true
                }
                addMarker(
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
                        .zIndex(2.0f))
                animateCamera(CameraUpdateFactory.newLatLngZoom(position, 17F))
            }
        }
    }




    //パーミッション処理の内部
    private fun requestLocationPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_REQUEST_FINE_LOCATION
            )
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                MY_PERMISSION_REQUEST_FINE_LOCATION
            )
        }
    }

    //パーミッションを設定したあとに現在地情報を引き渡す
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
        val toast = Toast.makeText(requireActivity(), msg, Toast.LENGTH_LONG)
        toast.show()
    }

    companion object {


        fun newInstance(): MapsFragment {
            return MapsFragment()
        }
    }
}


