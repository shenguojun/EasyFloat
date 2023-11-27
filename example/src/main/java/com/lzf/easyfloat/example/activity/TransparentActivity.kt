package com.lzf.easyfloat.example.activity

import android.app.Activity
import android.content.ClipboardManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lzf.easyfloat.example.databinding.ActivityTransparentBinding

class TransparentActivity : AppCompatActivity() {

    companion object {
        private const val CLIP_TEXT = "CLIP_TEXT"
        private var lastClipTime: String? = null
        fun startTransparentActivity(activity: Activity, clipText: String? = null) {
            val intent = Intent(activity, TransparentActivity::class.java)
            clipText?.let {
                intent.putExtra(CLIP_TEXT, it)
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            activity.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityTransparentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransparentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.setBackgroundDrawableResource(android.R.color.transparent)
        val clipText = intent.getStringExtra(CLIP_TEXT)
        if (!clipText.isNullOrEmpty()) {
            binding.tvClipboard.text = clipText
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        if (hasFocus) {
            val clipboard: ClipboardManager =
                getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = clipboard.primaryClip
            val timestamp = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                clipData?.description?.timestamp?.toString()
            } else {
                clipData?.description?.toString()
            }
            if (clipData != null && clipData.itemCount > 0 && timestamp != null && timestamp != lastClipTime) {
                lastClipTime = timestamp
                val item = clipData.getItemAt(0)
                val text = item.text
                if (text != null) {
                    val clipboardText = text.toString()
                    binding.tvClipboard.text = clipboardText
                }
            }
            if (binding.tvClipboard.text.isNullOrEmpty()) {
                finish()
            }
        }
    }
}