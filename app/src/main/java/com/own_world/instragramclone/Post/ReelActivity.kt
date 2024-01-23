package com.own_world.instragramclone.Post

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.own_world.instragramclone.HomeActivity
import com.own_world.instragramclone.Modals.Reel
import com.own_world.instragramclone.R
import com.own_world.instragramclone.Util.POST_FOLDER
import com.own_world.instragramclone.Util.REEL
import com.own_world.instragramclone.Util.REEL_FOLDER
import com.own_world.instragramclone.Util.uploadImage
import com.own_world.instragramclone.Util.uploadVideo
import com.own_world.instragramclone.databinding.ActivityReelBinding

class ReelActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReelBinding

    var videoUrl:String? = null
    lateinit var progressDialog:ProgressDialog
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadVideo(uri, REEL_FOLDER,progressDialog) {
                    url->
                if (url!=null) {
                    videoUrl=url
                }

            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReelBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        progressDialog = ProgressDialog(this)
        setSupportActionBar(binding.materialToolbar)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        binding.materialToolbar.setNavigationOnClickListener {
            startActivity(Intent(this@ReelActivity, HomeActivity::class.java))
            finish()
        }
        binding.cancelButton.setOnClickListener {
            startActivity(Intent(this@ReelActivity, HomeActivity::class.java))
            finish()
        }
        binding.selectReel.setOnClickListener {
            launcher.launch("video/*")
        }

        binding.postBtn.setOnClickListener {
            val reel: Reel = Reel(videoUrl!!,binding.captionEdtxt.editText?.text.toString())

            Firebase.firestore.collection(REEL).document().set(reel).addOnSuccessListener {
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ REEL).document().set(reel).addOnSuccessListener {
                    startActivity(Intent(this@ReelActivity, HomeActivity::class.java))
                    finish()
                }

            }
        }
    }


}