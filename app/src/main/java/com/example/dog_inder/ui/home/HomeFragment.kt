package com.example.dog_inder.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.dog_inder.databinding.HomeFragmentBinding
import com.example.dog_inder.ui.activities.DashboardActivity
import com.example.dog_inder.utils.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: HomeFragmentBinding by fragmentAutoCleared()

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

        _binding.fragmentHomeEmailInput.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }

        _binding.fragmentHomePasswordInput.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        isValidLiveData.observe(viewLifecycleOwner) { isValid ->
            _binding.fragmentHomeLoginBtn.isEnabled = isValid
        }

        _binding.fragmentHomeLoginBtn.setOnClickListener{
//            permissionResultLauncher.launch(
//                    arrayOf(
//                            Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE
//                    )
//            )

            homeViewModel.signIn(_binding.fragmentHomeEmailInput.text.toString().trim(), _binding.fragmentHomePasswordInput.text.toString().trim()).observe(viewLifecycleOwner, Observer {
                it?.let {
                    it.uid
                    navigateDashboard()
                }
            })
        }
    }


    fun navigateDashboard() {
        val dashboardActivity = Intent(activity, DashboardActivity::class.java)
        startActivity(dashboardActivity)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }
}