package com.example.dog_inder.ui.home

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.dog_inder.R
import com.example.dog_inder.databinding.HomeFragmentBinding
import com.example.dog_inder.ui.adapter.ListAdapter
import com.example.dog_inder.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: HomeFragmentBinding by fragmentAutoCleared()
    private var list: MutableList<String> = arrayOf("").toMutableList()

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

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()

    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        this.value = false

        addSource(emailLiveData) { email ->
            val password = passwordLiveData.value
            this.value = validateForm(email, password)
        }

        addSource(passwordLiveData) { password ->
            val email = emailLiveData.value
            this.value = validateForm(email, password)
        }
    }

    private fun validateForm(email: String?, password: String?) : Boolean {
        val isValidEmail = email != null && email.isNotBlank() && email.contains("@")
        val isValidPassword = password != null && password.isNotBlank() && password.length >= 6

        return isValidEmail && isValidPassword
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mEmailInput = view.findViewById<EditText>(R.id.fragment_home_email_input)
        val mPasswordInput = view.findViewById<EditText>(R.id.fragment_home_password_input)
        val mLoginBtn = view.findViewById<Button>(R.id.fragment_home_login_btn)

        mEmailInput.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }

        mPasswordInput.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        isValidLiveData.observe(viewLifecycleOwner) { isValid ->
            mLoginBtn.isEnabled = isValid
        }

        mLoginBtn.setOnClickListener{
//            permissionResultLauncher.launch(
//                    arrayOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    )
//            )

            homeViewModel.signIn(mEmailInput.text.toString().trim(), mPasswordInput.text.toString().trim()).observe(viewLifecycleOwner, Observer {
                it?.let {
                    it.uid
                }
            })
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val adapter = ListAdapter(list)
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        _binding.recyclerView.adapter = adapter
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}