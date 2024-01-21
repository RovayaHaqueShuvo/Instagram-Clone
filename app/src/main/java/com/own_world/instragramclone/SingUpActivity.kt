package com.own_world.instragramclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.own_world.instragramclone.Modals.User
import com.own_world.instragramclone.Util.USER_NODE
import com.own_world.instragramclone.Util.USER_PROFILE_FOlDER
import com.own_world.instragramclone.Util.uploadImage
import com.own_world.instragramclone.databinding.ActivitySingUpBinding

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOlDER){
                if (it ==null){

                }else{
                    user.image = it
                    binding.profileImage.setImageURI(uri)
                }

            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingUpBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        user = User()

        binding.registerBtn.setOnClickListener {
            if (binding.name.editText?.text.toString()
                    .equals("") or binding.email.editText?.text.toString()
                    .equals("") or binding.password.editText?.text.toString().equals("")
            ) {
                Toast.makeText(this@SingUpActivity, "Fill all reqiure", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.editText?.text.toString(),
                    binding.password.editText?.text.toString()
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.userName = binding.name.editText?.text.toString()
                        user.email = binding.email.editText?.text.toString()
                        user.password = binding.password.editText?.text.toString()

                        Firebase.firestore.collection(USER_NODE)
                            .document(Firebase.auth.currentUser?.uid.toString()).set(user)
                            .addOnCompleteListener{
                                startActivity(Intent(this@SingUpActivity, HomeActivity::class.java))
                                finish()
                                Toast.makeText(this@SingUpActivity, "User Created", Toast.LENGTH_SHORT).show()
                            }

                    } else {
                        Toast.makeText(
                            this@SingUpActivity,
                            result.exception?.localizedMessage,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }

        binding.addImage.setOnClickListener {
            launcher.launch("image/*")
        }
    }
}