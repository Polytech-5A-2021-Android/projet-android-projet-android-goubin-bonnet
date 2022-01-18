package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidproject.adapter.LocalisationAdapter
import com.example.androidproject.adapter.LocalisationListener
import com.example.androidproject.database.DatabaseLocalisation
import com.example.androidproject.databinding.FragmentListLocalisationBinding
import com.example.androidproject.viewModel.ListLocalisationViewModel
import com.example.androidproject.viewModelFactory.ListLocalisationViewModelFactory

class ListLocalisationFragment : Fragment() {
    private lateinit var binding: FragmentListLocalisationBinding
    private lateinit var viewModel: ListLocalisationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = DatabaseLocalisation.getInstance(application).localisationDao
        val viewModelFactory = ListLocalisationViewModelFactory(dataSource, application,1)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_localisation,
            container, false
        )
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(ListLocalisationViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        val adapter = LocalisationAdapter(LocalisationListener { localisationId ->
            this.findNavController().navigate(
                ListLocalisationFragmentDirections.actionListLocalisationFragmentToMapFragment(localisationId)
            )
        })
        binding.list.adapter = adapter

        viewModel.localisations.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                adapter.submitList(it)
            }
        })


        return binding.root

    }
}