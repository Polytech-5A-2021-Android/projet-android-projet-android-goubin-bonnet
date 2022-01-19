package com.example.androidproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidproject.adapter.LocalisationAdapter
import com.example.androidproject.adapter.LocalisationListener
import com.example.androidproject.database.DatabaseLocalisation
import com.example.androidproject.database.MyDatabase
import com.example.androidproject.databinding.FragmentConnexionBinding
import com.example.androidproject.databinding.FragmentDashboardBinding
import com.example.androidproject.model.Localisation
import com.example.androidproject.viewModel.ConnexionViewModel
import com.example.androidproject.viewModel.ListLocalisationViewModel
import com.example.androidproject.viewModelFactory.ListLocalisationViewModelFactory
import com.google.android.gms.common.GooglePlayServicesUtil

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var viewModel: ListLocalisationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = DatabaseLocalisation.getInstance(application).localisationDao
        val viewModelFactory = ListLocalisationViewModelFactory(dataSource, application)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dashboard,
            container, false
        )
        viewModel = ViewModelProvider(this,viewModelFactory).get(ListLocalisationViewModel::class.java)
        binding.viewModel = viewModel

        binding.lifecycleOwner = this
        binding.apply {
            tvTitle.text = getString(R.string.title)
            btRafraichir.text = getString(R.string.rafraichir)
            btVoirAll.text = getString(R.string.voirAll)
            btFindWatch.text = getString(R.string.voirWatch)

        }

        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        })


        val adapter = LocalisationAdapter(LocalisationListener { localisationId ->
            this.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToMapFragment(localisationId)
            )
        })

        binding.list.adapter = adapter

        viewModel.localisations.observe(viewLifecycleOwner, Observer { it ->
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.btVoirAll.setOnClickListener {
            this.findNavController().navigate(
                DashboardFragmentDirections.actionDashboardFragmentToListLocalisationFragment()
            )
        }

        viewModel.idLocalisation.observe(viewLifecycleOwner, Observer {
                id ->
            id?.let {
                this.findNavController().navigate(
                    DashboardFragmentDirections.actionDashboardFragmentToMapFragment2(id)
                )
                viewModel.doneNavigating()
            }
        })


        return binding.root
    }

    companion object {
        @JvmStatic
        @BindingAdapter("localisationLat")
        fun TextView.setLong(item : Localisation) {
            text = item.longitude.toString()

        }

        @JvmStatic
        @BindingAdapter("localisationDate")
        fun TextView.setDate(item : Localisation) {
            text = item.date.toString()

        }

        @JvmStatic
        @BindingAdapter("localisationLong")
        fun TextView.setLat(item : Localisation) {
            text = item.latitude.toString()

        }

        @BindingAdapter("setImage")
        @JvmStatic
        fun ImageView.setUserImage(item: Localisation) {
            setImageResource(
                R.mipmap.ic_map
            )
        }
    }
}