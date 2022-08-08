package com.android.nortontestapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.android.nortontestapplication.R
import com.android.nortontestapplication.databinding.FragmentLoginBinding
import com.android.nortontestapplication.model.LoginRequest
import com.android.nortontestapplication.utils.SessionManager
import com.android.nortontestapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login,container,false)
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        binding.lifecycleOwner = this
        binding.buttonLogin.setOnClickListener { it ->
            viewModel.getToken(LoginRequest(binding.editTextUserName.text.toString(),binding.editTextTextPassword.text.toString()))
            viewModel.token.observe(requireActivity(), Observer {
                SessionManager(requireActivity()).saveAuthToken(it.token)
            })
            viewModel.message.observe(requireActivity(), Observer {
                it.getContentIfNotHandled()?.let {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                }
            })
            it.findNavController().navigate(R.id.action_loginFragment_to_serversListFragment)
        }

        return binding.root
    }

}