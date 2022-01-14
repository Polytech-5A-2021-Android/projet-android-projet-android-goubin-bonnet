package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidproject.database.MyDatabase
import com.example.androidproject.databinding.FragmentConnexionBinding
import com.example.androidproject.viewModel.ConnexionViewModel
import com.example.androidproject.viewModelFactory.ConnexionViewModelFactory


class ConnexionFragment : Fragment() {
    private lateinit var binding: FragmentConnexionBinding
    private lateinit var viewModel: ConnexionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = ConnexionViewModelFactory(dataSource, application)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_connexion,
            container, false
        )
        viewModel = ViewModelProvider(this,viewModelFactory).get(ConnexionViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        binding.apply {
            tvBienvenue.text = getString(R.string.bienvenue)
            tiUsername.hint = getString(R.string.username)
            tiPassword.hint = getString(R.string.password)
            btLogin.text = getString(R.string.login)
            btCreateAccount.text = getString(R.string.create_account)
        }


        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btCreateAccount.setOnClickListener {
            this.findNavController().navigate(
                ConnexionFragmentDirections.actionConnexionFragmentToCreateAccountFragment2()
            )
        }

        viewModel.navigateToPersonalDataFragment.observe(viewLifecycleOwner, Observer {
                user ->
            user?.let {
                this.findNavController().navigate(
                    ConnexionFragmentDirections
                        .actionConnexionFragmentToDashboardFragment(user))
                viewModel.doneNavigating()
            }
        })

        return binding.root
    }
}