package com.example.itc

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import api.ApiService
import api.RetrofitInstance
import com.example.itc.databinding.ActivityMapsBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : FragmentActivity(), OnMapReadyCallback {

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    private lateinit var centerMarker: Marker
    private var isFirstLocationUpdate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }


        mapFragment.getMapAsync(this)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        centerMarker = Marker()
        centerMarker.position = LatLng(37.5665, 126.9780)

// 위치를 가져오는 비동기적인 작업이 완료되면 실행될 콜백
        mapFragment.getMapAsync(this)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)

        binding.placeButton.setOnClickListener {
            val markerPosition = centerMarker.position
            sendApiRequest(markerPosition.latitude, markerPosition.longitude)
        }


    }

        override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray
        ) {
            if (locationSource.onRequestPermissionsResult(
                    requestCode, permissions,
                    grantResults
                )
            ) {
                if (!locationSource.isActivated) { // 권한 거부됨
                    naverMap.locationTrackingMode = LocationTrackingMode.None
                }
                return
            }
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }

        override fun onMapReady(naverMap: NaverMap) {
            this.naverMap = naverMap
            naverMap.locationSource = locationSource
            naverMap.locationTrackingMode = LocationTrackingMode.Follow
            naverMap.uiSettings.isLocationButtonEnabled = true

            // 더미 마커 추가
            centerMarker.map = naverMap

            // 현위치 버튼 클릭 리스너 설정
            naverMap.addOnLocationChangeListener { location ->
                Toast.makeText(
                    this, "${location.latitude}, ${location.longitude}",
                    Toast.LENGTH_SHORT
                ).show()

                // 더미 마커 위치 업데이트
                if (isFirstLocationUpdate) {
                    // 처음 위치 업데이트
                    centerMarker.position = LatLng(location.latitude, location.longitude)
                    isFirstLocationUpdate = false
                }
            }

            // 지도의 카메라 이동 이벤트 리스너 등록
            naverMap.addOnCameraChangeListener { _, _ ->
                // 더미 마커 위치 업데이트
                centerMarker.position = naverMap.cameraPosition.target
            }

            // 스크롤 제스처가 비활성화된 경우에만 더미 마커 위치 업데이트
            if (!naverMap.uiSettings.isScrollGesturesEnabled) {
                naverMap.addOnCameraIdleListener {
                    // 더미 마커를 현재 중앙 위치로 이동
                    centerMarker.position = naverMap.cameraPosition.target
                }
            }
        }

         private fun sendApiRequest(latitude: Double, longitude: Double) {
            // Retrofit을 사용하여 서버에 API 요청 보내기
            // (이 부분은 이미 작성된 코드를 사용)
            // RetrofitInstance를 사용하여 ApiService 인터페이스의 구현체 생성
            val apiService = RetrofitInstance.apiService

            // mapUser 메서드 호출
            val call = apiService.mapUser(
                "00100101",
                "jiwon405@naver.com",
                "naver",
                latitude.toString(),
                longitude.toString(),
                "인천시",
                "미추홀구",
                "용현동"
            )

            call.enqueue(object : retrofit2.Callback<ApiService.JoinResponse> {
                override fun onResponse(
                    call: Call<ApiService.JoinResponse>,
                    response: retrofit2.Response<ApiService.JoinResponse>
                ) {
                    val statusCode = response.code() // 응답 코드를 가져옵니다.

                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        // 서버 응답을 처리합니다.
                    } else {
                        // 서버 응답이 실패한 경우
                        Log.d("log", "Fail with status code: $statusCode")
                    }
                }

                override fun onFailure(call: Call<ApiService.JoinResponse>, t: Throwable) {
                    // 네트워크 요청이 실패한 경우
                    Log.d("log", "Fail: ${t.message}")
                }
            })
        }

        companion object {
            private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
        }
}