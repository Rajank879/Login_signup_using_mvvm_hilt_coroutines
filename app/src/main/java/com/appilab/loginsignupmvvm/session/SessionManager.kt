package com.appilab.loginsignupmvvm.session

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appilab.loginsignupmvvm.data.local.AuthTokenDao
import com.appilab.loginsignupmvvm.domain.models.AuthToken
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class SessionManager @Inject constructor(
    private val authTokenDao: AuthTokenDao
) {

    private val _cachedToken = MutableLiveData<AuthToken?>()

    val cachedToken : LiveData<AuthToken?>
    get() = _cachedToken

    fun login(newValue:AuthToken){
        Timber.d("login: setting new value")
        setValue(newValue)
    }

    fun logout(){
        Timber.d("logout: nullyfying the token")
        CoroutineScope(Dispatchers.IO).launch {
            var errorMessage:String?= null
            try{
                _cachedToken.value!!.pk?.let{
                    authTokenDao.nullifyToken(it)
                }?:throw CancellationException("Token error . Logging out user. ")
            }catch (e:CancellationException)
            {
                Timber.e("logout : ${e.message}")
                errorMessage = e.message
            }catch (e:Exception){
                Timber.e("logout : ${e.message}")
                errorMessage = e.message
            }finally {
                errorMessage?.let{
                    Timber.e("logout : ${it}")
                }
                Timber.d("logout: finally")
                setValue(null)
            }
        }
    }

    private fun setValue(newValue: AuthToken?){
        CoroutineScope(Dispatchers.Main).launch {
            if (_cachedToken.value!=null)
            {
                _cachedToken.value = newValue
            }
        }
    }
}