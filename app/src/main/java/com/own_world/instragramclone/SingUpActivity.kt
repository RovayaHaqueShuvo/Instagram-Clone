package com.own_world.instragramclone

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.own_world.instragramclone.Modals.User
import com.own_world.instragramclone.Util.USER_NODE
import com.own_world.instragramclone.Util.USER_PROFILE_FOlDER
import com.own_world.instragramclone.Util.uploadImage
import com.own_world.instragramclone.databinding.ActivitySingUpBinding
import com.squareup.picasso.Picasso

class SingUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySingUpBinding
    private lateinit var user: User
    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadImage(uri, USER_PROFILE_FOlDER) {
                if (it == null) {
                } else {
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
        val text =
            "<font color= #FF000000> Already have an Account<font/> <font color= #1E88E5> Login?<font/>"
        binding.login.setText(Html.fromHtml(text))

        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {

                binding.loginBtn.text = "Update Profile"
                Firebase.firestore.collection(USER_NODE).document(Firebase.auth.currentUser!!.uid)
                    .get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profileImage)
                        }
                        binding.name.editText?.setText(user.userName)
                        binding.email.editText?.setText(user.email)
                        binding.password.editText?.setText(user.password)


                    }

            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        user = User()

        binding.loginBtn.setOnClickListener {

            if (intent.hasExtra("MODE")) {
                if (intent.getIntExtra("MODE", -1) == 1) {
                    Firebase.firestore.collection(USER_NODE)
                        .document(Firebase.auth.currentUser?.uid.toString()).set(user)
                        .addOnCompleteListener {
                            startActivity(Intent(this@SingUpActivity, HomeActivity::class.java))
                            finish()
                            Toast.makeText(this@SingUpActivity, "Profile Updated", Toast.LENGTH_SHORT)
                                .show()
                        }

                }

            } else if (binding.name.editText?.text.toString()
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
                            .addOnCompleteListener {
                                startActivity(Intent(this@SingUpActivity, HomeActivity::class.java))
                                finish()
                                Toast.makeText(
                                    this@SingUpActivity,
                                    "User Created",
                                    Toast.LENGTH_SHORT
                                ).show()
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

        binding.login.setOnClickListener {
            startActivity(Intent(this@SingUpActivity, LoginActivity::class.java))
            finish()
        }
    }

}