package tw.teng.practice.contact.ui

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import tw.teng.practice.contact.R
import tw.teng.practice.contact.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}