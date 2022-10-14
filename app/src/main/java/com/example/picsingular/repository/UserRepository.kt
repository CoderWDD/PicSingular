package com.example.picsingular.repository

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import com.example.picsingular.App
import com.example.picsingular.bean.User
import com.example.picsingular.bean.dto.UserDTO
import com.example.picsingular.bean.dto.UserUpdateDTO
import com.example.picsingular.common.utils.retrofit.ApiCallHandler
import com.example.picsingular.service.network.UserNetworkService
import com.example.picsingular.service.network.impl.UserServiceImpl
import com.example.picsingular.service.storage.sharedpreference.DataStoreManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.MultipartBody
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userService: UserNetworkService, private val dataStoreManager: DataStoreManager) {

    fun login(username: String, password: String) = flow {
        val userDTO = UserDTO(username = username, password = password)
        val loginRes = ApiCallHandler.apiCall { userService.loginUser(userDTO) }
        emit(loginRes)
    }.flowOn(Dispatchers.IO)

    fun register(username: String, password: String) = flow {
        val userDTO = UserDTO(username = username, password = password)
        val registerRes = ApiCallHandler.apiCall { userService.registerUser(userDTO = userDTO) }
        emit(registerRes)
    }.flowOn(Dispatchers.IO)

    // logout
    fun logout() = flow {
        val logoutRes = ApiCallHandler.apiCall { userService.logoutUser() }
        emit(logoutRes)
    }.flowOn(Dispatchers.IO)
    fun getUserInfoById(userId: Long) = flow{
        val userInfo = ApiCallHandler.apiCall { userService.getUserInfoById(userId = userId) }
        emit(userInfo)
    }.flowOn(Dispatchers.IO)

    fun getUserInfo() = flow {
        val userInfo = ApiCallHandler.apiCall { userService.getUserInfo() }
        emit(userInfo)
    }.flowOn(Dispatchers.IO)

    fun updateUserInfo(userUpdateDTO: UserUpdateDTO) = flow {
        val user = ApiCallHandler.apiCall { userService.updateUser(userUpdateDTO) }
        emit(user)
    }.flowOn(Dispatchers.IO)

    suspend fun saveUserToLocalStore(user: User?){
        dataStoreManager.saveUser(user = user!!)
    }

    // remove token from local store
    suspend fun removeTokenFromLocalStore(){
        dataStoreManager.saveToken("")
        dataStoreManager.saveUser(null)
    }

    // get user from local store
    fun getUserFromLocalStore(): Flow<User> = dataStoreManager.getUser()

    // save user token to local store
    suspend fun saveUserTokenToLocalStore(token: String){
        dataStoreManager.saveToken(token)
    }

    // get user token from local store
    fun getTokenFromLocalStore(): Flow<String> = dataStoreManager.getToken()

    // upload avatar
    fun uploadUserAvatar(multipartFile: MultipartBody) = flow{
        val res = ApiCallHandler.apiCall { userService.uploadAvatar(multipartFile) }
        emit(res)
    }.flowOn(Dispatchers.IO)
}