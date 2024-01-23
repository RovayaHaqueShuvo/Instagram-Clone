package com.own_world.instragramclone.Util

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

fun uploadImage(uri: Uri, folderName: String, callback:(String?)->Unit) {
    var imageUrl = ""
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                callback(imageUrl)
            }
        }
}


fun uploadVideo(uri: Uri, folderName: String,progressDialog: ProgressDialog, callback:(String?)->Unit) {
    var imageUrl = ""
    progressDialog.setMessage("Uploading Video...")
    progressDialog.show()


    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                progressDialog.dismiss()
                callback(imageUrl)
            }
        }
        .addOnProgressListener {

            var uploader = 100.0 * it.bytesTransferred / it.totalByteCount
            progressDialog.setMessage("Uploading Video ${uploader.toInt()}%")

        }
}

