package tw.teng.practice.contact.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import tw.teng.practice.contact.R
import tw.teng.practice.contact.databinding.ActivityMainBinding
import tw.teng.practice.contact.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
    }
}