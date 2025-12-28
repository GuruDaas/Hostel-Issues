 import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import com.hostel.Issues.models.request.RegisterRequest
import com.hostel.Issues.models.response.RegisterResponse
import com.hostel.Issues.models.request.LoginRequest
import com.hostel.Issues.models.response.LoginResponse

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>
}
