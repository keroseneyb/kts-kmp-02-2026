package com.kerosene.features.auth.data.repository

import com.kerosene.features.auth.domain.repository.LoginRepository

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(username: String, password: String): Result<String> = runCatching {
        if (username == USERNAME && password == PASSWORD) {
            TOKEN
        } else {
            throw Exception(ERROR)
        }
    }

    companion object {
        const val USERNAME = "admin"
        const val PASSWORD = "admin"
        const val TOKEN = "token"
        const val ERROR = "Incorrect username or password"
    }
}
