package com.example.androidproject.adapter

import com.example.androidproject.model.Localisation

class LocalisationListener (val clickListener: (localisationId: Long) -> Unit) {
    fun onClick(localisation : Localisation) = clickListener(localisation.id)
}