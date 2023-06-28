package com.nationalconclave.bseb.utils


import com.nationalconclave.bseb.App
import com.nationalconclave.bseb.ui.main.home.HomeActivity
import com.nationalconclave.bseb.ui.main.home.HomeViewModel
import com.nationalconclave.bseb.ui.main.login.LoginActivity
import com.nationalconclave.bseb.ui.main.login.LoginViewModel
import com.nationalconclave.bseb.ui.main.otp.OtpActivity
import com.nationalconclave.bseb.ui.main.otp.OtpViewModel
import com.nationalconclave.bseb.ui.main.persondetails.PersonDetailsActivity
import com.nationalconclave.bseb.ui.main.registration.RegistrationActivity
import com.nationalconclave.bseb.ui.main.registration.RegistrationViewModel
import com.nationalconclave.bseb.ui.main.sightseeingregistration.SightSeeingRegActivity
import com.nationalconclave.bseb.ui.main.sightseeingregistration.SightSeeingViewModel
import com.nationalconclave.bseb.ui.main.splash.SplashActivity
import com.nationalconclave.bseb.ui.main.view.MainActivity
import com.nationalconclave.bseb.ui.main.viewmodel.MainActivityViewModel
import com.nationalconclave.bseb.ui.main.webview.WebViewActivity
import com.nationalconclave.bseb.ui.movieDetail.view.MovieDetailActivity
import com.nationalconclave.bseb.ui.movieDetail.viewmodel.MovieDetailViewmodel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {
    fun inject(application: App)

    fun inject(activity: MainActivity)
    fun inject(activity: MovieDetailActivity)

    fun inject(activity: LoginActivity)
    fun inject(activity: RegistrationActivity)
    fun inject(activity: SplashActivity)
    fun inject(activity: HomeActivity)
    fun inject(activity: OtpActivity)
    fun inject(activity: WebViewActivity)
    fun inject(activity: PersonDetailsActivity)
    fun inject(activity: SightSeeingRegActivity)

    fun inject(viewModel: LoginViewModel)
    fun inject(viewModel: RegistrationViewModel)
    fun inject(viewModel: OtpViewModel)
    fun inject(viewModel: HomeViewModel)
    fun inject(viewModel: SightSeeingViewModel)

    fun inject(viewModel: MainActivityViewModel)
    fun inject(viewModel: MovieDetailViewmodel)

    fun networkService(): NetworkService

}