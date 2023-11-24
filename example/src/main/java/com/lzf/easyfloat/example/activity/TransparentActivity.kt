package com.lzf.easyfloat.example.activity

import android.content.ClipboardManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.example.databinding.ActivityTransparentBinding

class TransparentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransparentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransparentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setBackgroundDrawableResource(android.R.color.transparent);
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            val clipboard: ClipboardManager =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = clipboard.primaryClip
            if (clipData != null && clipData.itemCount > 0) {
                val item = clipData.getItemAt(0)
                val text = item.text
                if (text != null) {
                    val clipboardText = text.toString()
                    binding.tvClipboard.text = clipboardText
                }
            }
        }
    }
}