package com.example.itc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.itc.databinding.ActivityAnsBinding
import com.example.itc.databinding.ActivityPayBinding

class PayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding  =  ActivityPayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.DoButton.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)

            startActivity(intent)
        }
    }
}