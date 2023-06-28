package com.nationalconclave.bseb.ui.main.login

import androidx.lifecycle.ViewModel
import com.nationalconclave.bseb.utils.NetworkService
import javax.inject.Inject

class LoginViewModel : ViewModel() {

    @Inject
    lateinit var service: NetworkService

}