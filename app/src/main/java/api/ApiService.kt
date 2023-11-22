// ApiService.kt
package api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST


interface ApiService {
    @FormUrlEncoded
    @POST("/api/v1/wastes-info")
    fun mapUser(
        @Field("user_email") waste_id: String,
        @Field("login_type") user_email: String,
        @Field("user_email") login_type: String,
        @Field("login_type") location_latitude: String,
        @Field("user_email") location_longtitude: String,
        @Field("region_1depth_name") region_1depth_name: String,
        @Field("region_2depth_namel") region_2depth_name: String,
        @Field("region_3depth_name") region_3depth_name: String
    ): Call<JoinResponse>

    class JoinData {


    }
    class JoinResponse {
        @SerializedName("status")
        val status = 0

        @SerializedName("success")
        private val success = false

        @SerializedName("message")
        val message: String? = null

        inner class Data {
            @SerializedName("userId")
            private val userId = 0
        }
    }

}
