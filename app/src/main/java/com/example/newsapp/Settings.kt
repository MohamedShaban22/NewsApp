package com.example.newsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.example.newsapp.databinding.FragmentSettingsBinding

import java.util.Locale

class Settings : Fragment() {
   lateinit var viewBinding:FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.languageSpinner.onItemSelectedListener =object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position==0){}
                else if(position==1){
                    val local=LocaleListCompat.create(Locale("en"))
                    AppCompatDelegate.setApplicationLocales(local)
                }
                else if(position==2){
                    val local =LocaleListCompat.create(Locale("ar"))
                    AppCompatDelegate.setApplicationLocales(local)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }
}