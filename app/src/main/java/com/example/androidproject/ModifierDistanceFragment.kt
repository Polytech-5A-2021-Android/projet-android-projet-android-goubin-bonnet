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
import com.example.androidproject.adapter.LocalisationAdapter
import com.example.androidproject.adapter.LocalisationListener
import com.example.androidproject.database.DatabaseDistance
import com.example.androidproject.database.DatabaseLocalisation
import com.example.androidproject.databinding.FragmentListLocalisationBinding
import com.example.androidproject.databinding.FragmentModifierDistanceBinding
import com.example.androidproject.viewModel.ListLocalisationViewModel
import com.example.androidproject.viewModel.ModifierDistanceViewModel
import com.example.androidproject.viewModelFactory.ListLocalisationViewModelFactory
import com.example.androidproject.viewModelFactory.ModifierDistanceViewModelFactory
import com.google.android.material.slider.Slider

private lateinit var binding: FragmentModifierDistanceBinding
private lateinit var viewModel: ModifierDistanceViewModel
private lateinit var slider: Slider

class ModifierDistanceFragment : Fragment() {
    // TODO: Rename and change types of parameters
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = DatabaseDistance.getInstance(application).distanceDao
        val viewModelFactory = ModifierDistanceViewModelFactory(dataSource, application)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_modifier_distance,
            container, false
        )

        viewModel = ViewModelProvider(this, viewModelFactory).get(ModifierDistanceViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        var texte = viewModel.distance.value?.distance
        binding.apply {
            tvOldValue.text = getString(R.string.oldValue) + " " + texte
            btModifierDistanceLocalisation.text = getString(R.string.validate)
        }
        slider = binding.slider

        binding.btModifierDistanceLocalisation.setOnClickListener {
            viewModel.onValidate(slider.value)
        }

        viewModel.done.observe(viewLifecycleOwner, Observer {
                id ->
            id?.let {
                var texte = viewModel.distance.value?.distance
                binding.tvOldValue.text = getString(R.string.oldValue) + " " + texte
                viewModel.doneNavigating()
            }
        })


        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        })


        return binding.root

    }
}