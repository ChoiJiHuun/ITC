package api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @Headers("Content-Type: application/json")
    @POST("/api/v1/user/login")
    fun LoginUser(
        @Field("userEmail") userEmail: String,
        @Field("loginType") loginType: String,
        @Field("accessToken") accessToken: String,
    ): Call<LoginApi.LoginResponse>

    data class LoginResponse (
        @SerializedName("status")
        val status: Int,

        @SerializedName("success")
        val success: Boolean,

        @SerializedName("message")
        val message: String?,

       )
}