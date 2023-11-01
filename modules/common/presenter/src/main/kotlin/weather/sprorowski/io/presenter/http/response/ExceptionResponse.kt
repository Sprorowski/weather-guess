package weather.sprorowski.io.presenter.http.response

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonInclude.Include
import io.ktor.http.*

@JsonInclude(Include.NON_NULL)
abstract class ExceptionResponse(
    @JsonIgnore val statusCode: HttpStatusCode,
    val message: String,
    val details: Map<String, String>? = null,
)
