package com.lawlett.mafia.ui.auth

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.lawlett.mafia.R
import com.lawlett.mafia.core.BaseFragment
import com.lawlett.mafia.core.gone
import com.lawlett.mafia.core.showToast
import com.lawlett.mafia.core.visible
import com.lawlett.mafia.databinding.AuthFragmentBinding
import org.koin.android.ext.android.inject

const val AUTH_LAYOUT: Int = 0x00000001
const val ENTER_NAME_LAYOUT: Int = 0x00000002
const val RC_SIGN_IN: Int = 0x00000003

class AuthFragment : BaseFragment<AuthViewModel, AuthFragmentBinding>(R.layout.auth_fragment) {

    private var currentLayout: Int = AUTH_LAYOUT
    private lateinit var mGoogleSignInOption: GoogleSignInOptions
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun getViewModule(): AuthViewModel = inject<AuthViewModel>().value

    override fun getViewBinding(): AuthFragmentBinding? {
        return rootView?.let { rootView -> AuthFragmentBinding.bind(rootView) }
    }

    override fun setUpView() {
        updateViewCurrentInformation()
        btnNextSetListener()
        initGoogleSignIn()
    }

    private fun initGoogleSignIn() {
        mGoogleSignInOption = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), mGoogleSignInOption)
    }

    private fun btnNextSetListener() {
        binding?.singInButton?.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun setUpViewModelObs() {}

    private fun setContentLayout(layoutCode: Int) {
        when (layoutCode) {
            AUTH_LAYOUT -> showAuth()
            ENTER_NAME_LAYOUT -> showEnterName()
        }
    }

    private fun showEnterName() {
        binding?.authContainer?.gone()
        binding?.enterNameContainer?.visible()
        currentLayout = ENTER_NAME_LAYOUT
    }

    private fun showAuth() {
        binding?.authContainer?.visible()
        binding?.enterNameContainer?.gone()
        currentLayout = AUTH_LAYOUT
    }

    private fun updateViewCurrentInformation() {
        setContentLayout(currentLayout)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) handleResult(GoogleSignIn.getSignedInAccountFromIntent(data))
        else requireContext().showToast("Problem in execution order :(")
    }

    private fun handleResult(task: Task<GoogleSignInAccount>) {
        try {
            firebaseAuthWithGoogle(task.getResult(ApiException::class.java)?.idToken)
        } catch (e: ApiException) {
            requireContext().showToast(e.toString())
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    view?.let {
                        Snackbar.make(it, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                    }
                }
                updateUI(auth.currentUser)
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            setContentLayout(ENTER_NAME_LAYOUT)
        }
    }

}
