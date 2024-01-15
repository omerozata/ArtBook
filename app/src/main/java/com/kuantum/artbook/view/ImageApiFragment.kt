package com.kuantum.artbook.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.kuantum.artbook.R
import com.kuantum.artbook.adapter.ImageApiAdapter
import com.kuantum.artbook.databinding.FragmentImageApiBinding
import com.kuantum.artbook.util.Status
import com.kuantum.artbook.util.Util.DEFAULT_SEARCH_LANGUAGE
import com.kuantum.artbook.viewmodel.ArtViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class ImageApiFragment @Inject constructor(
    private val adapter: ImageApiAdapter
) : Fragment(R.layout.fragment_image_api) {

    private var fragmentBinding: FragmentImageApiBinding? = null
    lateinit var viewModel: ArtViewModel

    private lateinit var selectedLanguage: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val languageKeyList = arrayListOf<String>("Türkçe", "English", "Français")
        val languageValueList = arrayListOf<String>("tr", "en", "fr")

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        selectedLanguage = viewModel.getSearchLanguage()

        println(selectedLanguage)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

        val spinnerAdapter = ArrayAdapter<String>(
            requireContext(), android.R.layout.simple_spinner_dropdown_item, languageKeyList
        )

        val position = languageValueList.indexOf(selectedLanguage)
        println("position$position")

        binding.spinner.apply {
            adapter = spinnerAdapter
            setSelection(position, false)
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?, view: View?, position: Int, id: Long
                ) {
                    viewModel.setSelectedLanguage(languageValueList[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }
        }


        var job: Job? = null
        binding.editSearch.addTextChangedListener {
            job?.cancel()

            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        println(selectedLanguage)
                        viewModel.searchImage(it.toString(), selectedLanguage)
                    }
                }
            }
        }

        subscribeToObservers()

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = GridLayoutManager(requireContext(), 3)

        adapter.onItemClick = {
            viewModel.setSelectedImage(it)
            findNavController().popBackStack()
        }


        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun subscribeToObservers() {

        viewModel.imageList.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult ->
                        imageResult.previewURL
                    }
                    adapter.imageList = urls ?: listOf()
                    fragmentBinding?.progressbar?.visibility = View.GONE
                }

                Status.LOADING -> {
                    fragmentBinding?.progressbar?.visibility = View.VISIBLE
                }

                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG)
                        .show()
                    fragmentBinding?.progressbar?.visibility = View.GONE
                }
            }

        })

        viewModel.selectedSearchLanguage.observe(viewLifecycleOwner, Observer {
            selectedLanguage = it
            viewModel.saveSearchLanguage(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }
}