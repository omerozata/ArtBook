package com.kuantum.artbook.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.kuantum.artbook.R
import com.kuantum.artbook.databinding.FragmentArtDetailsBinding
import com.kuantum.artbook.util.Status
import com.kuantum.artbook.viewmodel.ArtDetailsViewModel
import com.kuantum.artbook.viewmodel.SharedViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding: FragmentArtDetailsBinding? = null
    lateinit var viewModel: ArtDetailsViewModel
    lateinit var sharedViewModel: SharedViewModel

    private var selectedUrl = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]
        viewModel = ViewModelProvider(requireActivity())[ArtDetailsViewModel::class.java]

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.imageview.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        binding.btnSave.setOnClickListener {
            viewModel.makeArt(
                binding.editArtname.text.toString(),
                binding.editArtistname.text.toString(),
                binding.editYear.text.toString(),
                selectedUrl
            )
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }

    private fun subscribeToObservers() {
        sharedViewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {url ->
            fragmentBinding?.let {
                glide.load(url).into(it.imageview)
                selectedUrl = url
            }
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), it.message ?: "Success", Toast.LENGTH_LONG).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {

                }
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }
}