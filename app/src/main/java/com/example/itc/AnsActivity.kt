package com.example.itc


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.itc.databinding.ActivityAnsBinding // 뷰 바인딩 import 추가

class AnsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnsBinding // 뷰 바인딩 객체 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 뷰 바인딩을 통해 XML 레이아웃의 뷰 참조
        val spinner1 = binding.spinner1
        val spinner2 = binding.spinner2
        val textView3 = binding.textView3

        // 대분류 정보
        val categories = resources.getStringArray(R.array.categories)

        // 대분류 스피너 설정
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner1.adapter = categoryAdapter

        //x

        // 소분류 스피너 설정 메서드
        fun setSubcategories(subcategoryArrayResId: Int) {
            val subcategoriesWithPrices = resources.getStringArray(subcategoryArrayResId)
            val subcategoryNames = subcategoriesWithPrices.map { it.split(",")[0] }.toTypedArray()
            val subcategoryAdapter =
                ArrayAdapter(this, android.R.layout.simple_spinner_item, subcategoryNames)
            subcategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinner2.adapter = subcategoryAdapter

            // 소분류 스피너 선택 이벤트 처리
            binding.spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // 선택한 소분류에 따른 가격 정보 표시
                    val selectedSubcategoryString = subcategoriesWithPrices[position]
                    val parts = selectedSubcategoryString.split(",")
                    val subcategoryPrice = parts[1].toDouble()

                    // 가격을 textView3에 표시
                    binding.textView3.text = "가격: $subcategoryPrice 원"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 선택이 해제될 때 수행할 작업 (옵션)
                }
            }
        }
        //
        // 대분류 스피너 선택 이벤트 처리
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                // 선택한 대분류에 따라 소분류와 가격 정보를 설정
                when (position) {
                    0 -> setSubcategories(R.array.furniture_subcategories)
                    1 -> setSubcategories(R.array.appliances_subcategories)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 선택이 해제될 때 수행할 작업 (옵션)
            }
        }

        binding.MapButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            startActivity(intent)
        }

    }
}

