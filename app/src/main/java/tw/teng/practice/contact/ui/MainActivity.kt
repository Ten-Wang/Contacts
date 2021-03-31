package tw.teng.practice.contact.ui

import android.os.Bundle
import tw.teng.practice.contact.R
import tw.teng.practice.contact.ui.base.BaseActivity

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}