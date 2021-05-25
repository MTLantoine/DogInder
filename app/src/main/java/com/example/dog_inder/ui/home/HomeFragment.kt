package com.example.dog_inder.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.example.dog_inder.R
import androidx.activity.result.contract.ActivityResultContracts
import android.Manifest
import android.widget.EditText
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()

    private val permissionResultLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
        if (!map.values.contains(false)) {
            // DO ACTION
            getDocumentResultLauncher.launch("image/jpeg | image/png | image/png")
        }
    }

    private val getDocumentResultLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            Log.d("MyURI", uri.path!!)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.fragment_home_login_btn).setOnClickListener{
            permissionResultLauncher.launch(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            )

            val mEmailInput = view.findViewById<EditText>(R.id.fragment_home_email_input)
            val mPasswordInput = view.findViewById<EditText>(R.id.fragment_home_password_input)

            homeViewModel.signUp(mEmailInput.toString(), mPasswordInput.toString()).observe(viewLifecycleOwner, Observer {
                it?.let {
                    it.uid
                }
            })
        }
    }
}