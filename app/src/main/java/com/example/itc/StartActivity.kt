package com.example.itc

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.itc.databinding.ActivityStartBinding
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.*
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient


class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)



        KakaoSdk.init(this, "ddbb22dc00845a1d661212c964be0a81")

        binding.KakaoButton.setOnClickListener {
            KaKaoLogin()
        }
    }
         private fun KaKaoLogin() {

             UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                 if (error != null) {
                     Log.e(TAG, "토큰 정보 보기 실패", error)
                 } else if (tokenInfo != null) {
                     Log.i(
                         TAG, "토큰 정보 보기 성공" +
                                 "\n회원번호: ${tokenInfo.id}" +
                                 "\n만료시간: ${tokenInfo.expiresIn} 초"
                     )
                     val intent = Intent(this,MainActivity::class.java)
                     startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                 }
             }

            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }

// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                        val intent = Intent(this,MainActivity::class.java)
                        startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
            }

            UserApiClient.instance.loginWithKakaoAccount(
                this,
                prompts = listOf(Prompt.SELECT_ACCOUNT)
            ) { token, error ->
                if (error != null) {
                    Log.e(TAG, "로그인 실패", error)
                } else if (token != null) {
                    Log.i(TAG, "로그인 성공 ${token.accessToken}")
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                }
            }


            val properties = mapOf("CUSTOM_PROPERTY_KEY" to "CUSTOM_PROPERTY_VALUE")

            UserApiClient.instance.updateProfile(properties) { error ->
                if (error != null) {
                    Log.e(TAG, "사용자 정보 저장 실패", error)
                } else {
                    Log.i(TAG, "사용자 정보 저장 성공")
                }
            }

             UserApiClient.instance.me { user, error ->
                 if (error != null) {
                     Log.e(TAG, "사용자 정보 요청 실패", error)
                 } else if (user != null) {
                     Log.i(
                         TAG, "사용자 정보 요청 성공" +
                                 "\n회원번호: ${user.id}" +
                                 "\n이메일: ${user.kakaoAccount?.email}" +
                                 "\n닉네임: ${user.kakaoAccount?.profile?.nickname}"
                     )
                     var login_type = "kakao"
                     var email = user.kakaoAccount?.email
                     var nickname = user.kakaoAccount?.profile?.nickname

                 }
             }
             
        }
    }