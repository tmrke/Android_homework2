package ru.ageev.android_homework2.ui.profile_screen.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.ageev.android_homework2.data.model.Profile
import ru.ageev.android_homework2.domain.auth_use_case.DeleteTokenUseCase
import ru.ageev.android_homework2.domain.profile_use_case.GetProfileUseCase
import ru.ageev.android_homework2.domain.GetUsernameUseCase
import ru.ageev.android_homework2.domain.profile_use_case.SubscribeUseCase
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getUsernameUseCase: GetUsernameUseCase,
    private val deleteTokenUseCase: DeleteTokenUseCase,
    private val subscribeUseCase: SubscribeUseCase,
) : ViewModel() {

    private val _profileLiveData = MutableLiveData<Profile>()
    val profileLiveData: LiveData<Profile> = _profileLiveData

    fun getProfile(profileId: String) {
        viewModelScope.launch {
            getProfileUseCase.execute(profileId).also { profile ->
                _profileLiveData.postValue(profile)
            }
        }
    }

    fun deleteToken() {
        deleteTokenUseCase.execute()
    }

    fun getUsername(): String {
        return getUsernameUseCase.execute()
    }


    fun subscribe(profileId: String) {
        viewModelScope.launch {
            subscribeUseCase.execute(profileId)
        }
    }
}