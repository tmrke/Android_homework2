package ru.ageev.android_homework2.presentation.auth_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.ageev.android_homework2.data.model.Profile
import ru.ageev.android_homework2.data.remote.model.response.CheckUsernameEnumResponse
import ru.ageev.android_homework2.domain.CheckUsernameUseCase
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val checkUsernameUseCase: CheckUsernameUseCase
) : ViewModel() {

    private val _checkUsernameLiveData = MutableLiveData<CheckUsernameEnumResponse>()
    val checkUsernameLiveData: LiveData<CheckUsernameEnumResponse> = _checkUsernameLiveData
    suspend fun checkUsername(username: String) {
        viewModelScope.launch {
            checkUsernameUseCase.execute(username).let { response ->
                _checkUsernameLiveData.postValue(response)
            }
        }
    }
}