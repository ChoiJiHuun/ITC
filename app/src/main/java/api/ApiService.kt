// ApiService.kt
package api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/api/v1/wastes-info")
    fun mapUser(
        @Field("wasteCode") wasteCode: String,
        @Field("userEmail") userEmail: String,
        @Field("loginType") loginType: String,
        @Field("locationLatitude") locationLatitude: String,
        @Field("locationLongitude") locationLongitude: String,
        @Field("region1depthName") region1depthName: String,
        @Field("region2depthName") region2depthName: String,
        @Field("region3depthName") region3depthName: String
    ): Call<JoinResponse>

    class JoinData {


    }
    data class JoinResponse(
        @SerializedName("status")
        val status: Int,

        @SerializedName("success")
        val success: Boolean,

        @SerializedName("message")
        val message: String?,

        @SerializedName("data")
        val data: Data? // 이 부분이 추가되었습니다.
    ) {
        data class Data(
            @SerializedName("userId")
            val userId: Int
        )
    }


}
