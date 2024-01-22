package com.own_world.instragramclone.Post

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.own_world.instragramclone.R
import com.own_world.instragramclone.Util.POST_FOLDER
import com.own_world.instragramclone.Util.USER_PROFILE_FOlDER
import com.own_world.instragramclone.Util.uploadImage
import com.own_world.instragramclone.databinding.ActivityPostBinding

class PostActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPostBinding
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, POST_FOLDER) {
                if (it!=null) {
                    binding.selectImage.setImageURI(uri)
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPostBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            finish()
        }

        binding.selectImage.setOnClickListener {
            launcher.launch("image/*")
        }

    }
}