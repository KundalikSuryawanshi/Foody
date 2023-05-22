package com.kundalik.foody

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.kundalik.foody.databinding.ActivityCreateRecipeBinding
import com.kundalik.foody.models.Recipe
import com.kundalik.foody.utils.GenerateKeys
import java.time.LocalDate

class CreateRecipeActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCreateRecipeBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var progressBar: ProgressBar
    private lateinit var recipeImage: String

    val TAG = "CREATE RECIPE"
    val PIC_IMAGE_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityCreateRecipeBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        progressBar = mBinding.progressBar

        mBinding.btnCreateRecipe.setOnClickListener {
            createFoodRecipe()
        }

        val image = mBinding.ivCreateImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(intent, PIC_IMAGE_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PIC_IMAGE_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            mBinding.ivCreateImage.setImageURI(imageUri)
             //Perform the image upload to Firebase Storage
            uploadImageToFirebaseStorage(imageUri)
        }
    }

    private fun uploadImageToFirebaseStorage(imageUri: Uri?) {
        val storageReference = FirebaseStorage.getInstance().reference.child("images")
        val random = GenerateKeys().generateRandomPassword(10)
        val imageRef = storageReference.child("${random}+image.jpg")
        val uploadTask = imageUri?.let { imageRef.putFile(it) }

        uploadTask?.addOnSuccessListener { taskSnapshot ->
            // Image upload successful
            // Retrieve the download URL
            imageRef.downloadUrl.addOnSuccessListener { uri ->
                if (uri != null)
                {
                    recipeImage = uri.toString()
                } else {
                    recipeImage = getString(R.string.imageLink)
                }

            }
        }
            ?.addOnFailureListener { exception ->
                Toast.makeText(this@CreateRecipeActivity, "unable to save image ${exception}", Toast.LENGTH_SHORT).show()
                // Handle image upload failure
            }
    }

    private fun createFoodRecipe() {
        progressBar.visibility = View.VISIBLE
        val name = mBinding.etRecipeName.text.toString()
        val title = mBinding.etRecipeTitle.text.toString()
        val timeToReady = mBinding.etTimeToReady.selectedItem.toString()
        val ingredients = mBinding.etRecipeIngredients.text.toString()
        val process = mBinding.etRecipeProcess.text.toString()
        val category = mBinding.etRecipeCategory.selectedItem.toString()
        val date = LocalDate.now().toString()
        val postUser = auth.currentUser?.uid.toString()
        var image = recipeImage
        if (image != null) {
            val image = recipeImage.toString()
        } else {
            image = getString(R.string.imageLink)
        }


        if (name.isNotEmpty() && title.isNotEmpty() && ingredients.isNotEmpty() && process.isNotEmpty() && date.isNotEmpty()) {

            val recipe = Recipe(
                recipeId = "",
                image,
                name,
                title,
                timeToReady,
                ingredients,
                process,
                category,
                date,
                postUser
            )
            saveRecipeToRealTimeDataBase(recipe)

        } else {
            Toast.makeText(this@CreateRecipeActivity, "Enter All the Filed", Toast.LENGTH_SHORT).show()
        }
    }

    private fun saveRecipeToRealTimeDataBase(recipe: Recipe) {
        val key = database.reference.child("recipe").push().key

        database.reference.child("recipe").child(key!!).setValue(recipe)
            .addOnSuccessListener {
                progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@CreateRecipeActivity, "Successfully Saved", Toast.LENGTH_SHORT).show()
                mBinding.etRecipeName.text = null
                mBinding.etRecipeTitle.text = null
                mBinding.etTimeToReady.selectedItem.toString()
                mBinding.etRecipeIngredients.text = null
                mBinding.etRecipeProcess.text = null
                mBinding.etRecipeCategory.selectedItem.toString()
                finish()
            }
            .addOnFailureListener {
                progressBar.visibility = View.INVISIBLE
                Log.d(TAG+"SAVE RECIPE", it.message.toString())
                Toast.makeText(this@CreateRecipeActivity, "Unable to save!", Toast.LENGTH_SHORT).show()
            }
    }
}

