package com.example.newsapp.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentCatigoriesBinding
import com.example.newsapp.ui.home.news.NewsFragment

class Categories : Fragment() {

lateinit var viewBinding:FragmentCatigoriesBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        viewBinding=FragmentCatigoriesBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoriesClick()

    }

    private fun categoriesClick() {
        viewBinding.sportsImg.setOnClickListener{
            onCategoriesClickSport?.onCategoriesClick()
            sendDataToFragmentNews(R.id.sports_img)

        }

        viewBinding.healthImg.setOnClickListener{
            onCategoriesClickHealth?.onCategoriesClick()
            sendDataToFragmentNews(R.id.health_img)
        }

        viewBinding.entertainmentImg.setOnClickListener{
            onCategoriesClickEntertainment?.onCategoriesClick()
            sendDataToFragmentNews(R.id.entertainment_img)
        }

        viewBinding.technologyImg.setOnClickListener{
            onCategoriesClickTechnology?.onCategoriesClick()
            sendDataToFragmentNews(R.id.technology_img)
        }

        viewBinding.businessImg.setOnClickListener{
            onCategoriesClickBusiness?.onCategoriesClick()
            sendDataToFragmentNews(R.id.business_img)
        }

        viewBinding.scienceImg.setOnClickListener{
            onCategoriesClickScience?.onCategoriesClick()
            sendDataToFragmentNews(R.id.science_img)

        }
    }

    private fun pushFragment(fragment: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.frame_layout,fragment)?.commit()
    }

    private fun sendDataToFragmentNews(id: Int) {
        val bundle = Bundle()
        bundle.putInt("id",id)
        val fragment = NewsFragment()
        fragment.arguments = bundle
        pushFragment(fragment)
        }
    var onCategoriesClickEntertainment:OnCategoriesClickListener?=null
    var onCategoriesClickHealth:OnCategoriesClickListener?=null
    var onCategoriesClickBusiness:OnCategoriesClickListener?=null
    var onCategoriesClickScience:OnCategoriesClickListener?=null
    var onCategoriesClickTechnology:OnCategoriesClickListener?=null
    var onCategoriesClickSport:OnCategoriesClickListener?=null
    interface OnCategoriesClickListener{
        fun onCategoriesClick()

    }

}



//API KEY
//cc2c4bd37297421b806435497d4890b9