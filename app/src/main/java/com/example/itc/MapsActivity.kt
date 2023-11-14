 package com.example.itc

 import android.os.Bundle
 import androidx.appcompat.app.AppCompatActivity
 import com.google.android.gms.maps.CameraUpdateFactory
 import com.google.android.gms.maps.GoogleMap
 import com.google.android.gms.maps.OnMapReadyCallback
 import com.google.android.gms.maps.SupportMapFragment
 import com.google.android.gms.maps.model.LatLng
 import com.google.android.gms.maps.model.MarkerOptions


 class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

     private lateinit var mMap: GoogleMap

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_maps)

         val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
         mapFragment.getMapAsync(this)
     }

     override fun onMapReady(googleMap: GoogleMap) {
         mMap = googleMap

         val SEOUL = LatLng(37.56, 126.97)

         val markerOptions = MarkerOptions()         // 마커 생성
         markerOptions.position(SEOUL)
         markerOptions.title("서울")                  // 마커 제목
         markerOptions.snippet("한국의 수도")         // 마커 설명
         mMap.addMarker(markerOptions)

         mMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL)) // 초기 위치
         mMap.animateCamera(CameraUpdateFactory.zoomTo(15F))    // 줌의 정도
         googleMap.mapType = GoogleMap.MAP_TYPE_HYBRID          // 지도 유형 설정
     }
 }