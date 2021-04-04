package tw.teng.practice.contact.ui.base

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

open class BaseActivity : AppCompatActivity() {
    lateinit var _viewModel: BaseViewModel

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
        (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).run {
            registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    _viewModel.onNetworkAvailable()
                }
            })
        }
    }
}