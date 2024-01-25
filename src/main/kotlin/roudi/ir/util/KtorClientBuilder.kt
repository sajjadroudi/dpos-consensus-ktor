package roudi.ir.util

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*

object KtorClientBuilder {

    fun build(): HttpClient {
        return HttpClient(CIO) {

            defaultRequest {
                contentType(ContentType.Application.Json)
            }

            install(ContentNegotiation) {
                json()
            }
        }
    }

}