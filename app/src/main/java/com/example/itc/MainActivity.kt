package com.example.itc

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.example.itc.databinding.ActivityMainBinding
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =  ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.ManageButton.setOnClickListener{
            val intent2 = Intent(this,AnsActivity::class.java)
            startActivity(intent2)
        }

        binding.AIButton.setOnClickListener{
            val intent = Intent(this,PayActivity::class.java)
            startActivity(intent)
        }

    }
}