package com.example.dog_inder.ui.fragments.home

import android.Manifest
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.example.dog_inder.databinding.HomeFragmentBinding
import com.example.dog_inder.ui.activities.DashboardActivity
import com.example.dog_inder.utils.databinding.fragmentAutoCleared
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {
    private var cancellationSignal: CancellationSignal? = null;

    private val authtificationCallbacks: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    navigateDashboard()
                }
            }
    private var signin: Boolean = true

    private val permissionResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (!map.values.contains(false)) {
                getResultLauncher.launch(
                    arrayOf(
                        Manifest.permission.INTERNET,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                )
            }
        }

    private val getResultLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {}

    private val homeViewModel: HomeViewModel by viewModel()
    private var _binding: HomeFragmentBinding by fragmentAutoCleared()

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

    private fun validateForm(email: String?, password: String?): Boolean {
        val isValidEmail: Boolean
        val isValidPassword: Boolean
        if (signin) {
            isValidEmail = email != null && email.isNotBlank()
            isValidPassword = password != null && password.isNotBlank()
        } else {
            isValidEmail = email != null && email.isNotBlank() && email.contains("@")
            isValidPassword = password != null && password.isNotBlank() && password.length >= 6
        }

        return isValidEmail && isValidPassword
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        askForPermissions()
        checkBiometricSupport()

        _binding.fragmentHomeAuthBtn.setOnClickListener {
            val biometricPrompt =
                BiometricPrompt.Builder(activity)
                    .setTitle("Title of prompt")
                    .setSubtitle("Authentification is required")
                    .setDescription("This app can use fingerprint protection to keep your data secure")
                    .setNegativeButton("Cancel", ContextCompat.getMainExecutor(activity), DialogInterface.OnClickListener{ dialog, which ->
                        Toast.makeText(
                            activity,
                            "Authentification canceled",
                            Toast.LENGTH_LONG
                        ).show()
                    }).build()

            biometricPrompt.authenticate(getCancellationSignal(), ContextCompat.getMainExecutor(activity), authtificationCallbacks)
        }

        _binding.fragmentHomeChangeBtn.isEnabled = true
        _binding.fragmentHomeChangeBtn.setOnClickListener {
            if (signin) {
                _binding.fragmentHomeChangeBtn.text = "D??j?? un compte ? Se connecter"
                _binding.fragmentHomeLoginBtn.text = "Cr??er un compte"
            } else {
                _binding.fragmentHomeChangeBtn.text = "Pas encore de compte ? cr??ez en un !"
                _binding.fragmentHomeLoginBtn.text = "Se connecter"
            }

            signin = !signin
        }

        _binding.fragmentHomeEmailInput.doOnTextChanged { text, _, _, _ ->
            emailLiveData.value = text?.toString()
        }

        _binding.fragmentHomePasswordInput.doOnTextChanged { text, _, _, _ ->
            passwordLiveData.value = text?.toString()
        }

        isValidLiveData.observe(viewLifecycleOwner) { isValid ->
            _binding.fragmentHomeLoginBtn.isEnabled = isValid
        }

        _binding.fragmentHomeLoginBtn.setOnClickListener {

            if (signin) {
                homeViewModel.signIn(
                    _binding.fragmentHomeEmailInput.text.toString().trim(),
                    _binding.fragmentHomePasswordInput.text.toString().trim()
                ).observe(viewLifecycleOwner, Observer {
                    it?.let {
                        it.uid
                        navigateDashboard()
                    }
                })
            } else {
                homeViewModel.signUp(
                    _binding.fragmentHomeEmailInput.text.toString().trim(),
                    _binding.fragmentHomePasswordInput.text.toString().trim()
                ).observe(viewLifecycleOwner, Observer {
                    it?.let {
                        it.uid
                        navigateDashboard()
                    }
                })
            }
        }
    }


    fun navigateDashboard() {
        val dashboardActivity = Intent(activity, DashboardActivity::class.java)
        startActivity(dashboardActivity)
    }

    private fun getCancellationSignal(): CancellationSignal {
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(
                activity,
                "Authentification was canceled by the user",
                Toast.LENGTH_LONG
            ).show()
        }

        return cancellationSignal as CancellationSignal;
    }

    fun checkBiometricSupport(): Boolean {
        val keyguardManager =
            activity?.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager

        if (!keyguardManager.isKeyguardSecure) {
            Toast.makeText(
                activity,
                "Figerprint authentification is not enabled",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.USE_BIOMETRIC
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                activity,
                "Figerprint authentification is not enabled",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        return if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
            true
        } else true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return _binding.root
    }

    fun askForPermissions() {
        permissionResultLauncher.launch(
            arrayOf(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.CAMERA
            )
        )
    }
}