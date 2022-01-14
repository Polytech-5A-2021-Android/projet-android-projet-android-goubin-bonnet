package com.example.androidproject

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.androidproject.database.MyDatabase
import com.example.androidproject.databinding.FragmentCreateAccountBinding
import com.example.androidproject.viewModel.ConnexionViewModel
import com.example.androidproject.viewModelFactory.ConnexionViewModelFactory
import java.time.LocalDate
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import java.time.temporal.ChronoUnit
import java.util.*

class CreateAccountFragment : Fragment() {
    private lateinit var binding: FragmentCreateAccountBinding
    private lateinit var viewModel: ConnexionViewModel
    private lateinit var spinner: Spinner

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(this.activity).application
        val dataSource = MyDatabase.getInstance(application).userDao
        val viewModelFactory = ConnexionViewModelFactory(dataSource, application)
        val calendar = Calendar.getInstance()
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_create_account,
            container, false
        )

        binding.apply {
            tvCreateAccount.text = getString(R.string.create_account)
            tiUsername.hint = getString(R.string.username)
            tiLastname.hint = getString(R.string.lastname)
            tiPassword.hint = getString(R.string.password)
            tiAdresse.hint = getString(R.string.adresse)
            tiVille.hint = getString(R.string.ville)
            tiEmail.hint = getString(R.string.email)
            btValider.text = getString(R.string.validate)
        }
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this,viewModelFactory).get(ConnexionViewModel::class.java)
        binding.viewModel = viewModel

        spinner = binding.pays
        spinner.adapter = this.context?.let { ArrayAdapter<String>(it, android.R.layout.simple_dropdown_item_1line, viewModel.pays) }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                viewModel.onPays(position)
            }
        }

        val datePicker = binding.datePicker
        datePicker.maxDate = calendar.timeInMillis
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) {
                view, year, month, day ->
            val month = month + 1
            val age  = ChronoUnit.YEARS.between(LocalDate.of(year, month, day), LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH)))
            binding.tvAge.setText("Age : " + age.toString())
            viewModel.onBirthday(day.toString() + "/" + month.toString() + "/" + year.toString())
            viewModel.onAge(age)
        }


        viewModel.message.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this.context, it, Toast.LENGTH_LONG).show()
            }
        })

        binding.btValider.setOnClickListener {
            this.findNavController().navigate(
                CreateAccountFragmentDirections.actionCreateAccountFragment2ToConnexionFragment()
            )
        }

        viewModel.navigateToConnexionActivity.observe(viewLifecycleOwner, Observer {
                user ->
            user?.let {
                this.findNavController().navigate(
                    CreateAccountFragmentDirections.actionCreateAccountFragment2ToConnexionFragment())
                viewModel.doneValidateNavigating()
            }
        })

        return binding.root

    }

}