package com.example.itc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import api.ApiService
import com.example.itc.databinding.ActivityMainBinding
import com.example.itc.databinding.ActivityMapsBinding
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MapsActivity : FragmentActivity(),  OnMapReadyCallback {
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.map_fragment, it).commit()
            }

        mapFragment.getMapAsync(this)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
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

        // 현위치 버튼 클릭 리스너 설정
        naverMap.addOnLocationChangeListener { location ->
            Toast.makeText(this, "${location.latitude}, ${location.longitude}",
                Toast.LENGTH_SHORT).show()
            val latitude = location.latitude
            val longtitude = location.longitude

// Retrofit 빌더를 생성
            val retrofit = Retrofit.Builder()
                .baseUrl("BASE_URL") // 기본 URL 설정
                .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 컨버터 팩토리 설정
                .build()

// ApiService 인터페이스를 구현한 구현체 생성
            val apiService = retrofit.create(ApiService::class.java)

// mapUser 메서드 호출
            val call = apiService.mapUser(
                "00100101",
                "jiwon405@naver.com",
                "naver",
                latitude.toString(),
                longtitude.toString(),
                "인천시",
                "미추홀구",
                "용현동"
            )

            call.enqueue(object : retrofit2.Callback<ApiService.JoinResponse> {
                override fun onResponse(call: Call<ApiService.JoinResponse>, response: retrofit2.Response<ApiService.JoinResponse>) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        // 서버 응답을 처리합니다.
                    } else {
                        // 서버 응답이 실패한 경우
                    }
                }

                override fun onFailure(call: Call<ApiService.JoinResponse>, t: Throwable) {
                    // 네트워크 요청이 실패한 경우
                }
            })

        }

    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

}
