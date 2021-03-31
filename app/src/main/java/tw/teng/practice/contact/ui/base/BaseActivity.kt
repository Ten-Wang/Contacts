package tw.teng.practice.contact.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

open class BaseActivity : AppCompatActivity() {

    lateinit var _viewModel: BaseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _viewModel = ViewModelProvider(this).get(BaseViewModel::class.java)
    }
}