package com.kuantum.artbook.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kuantum.artbook.R
import com.kuantum.artbook.databinding.FragmentArtDetailsBinding

class ArtDetailsFragment : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding : FragmentArtDetailsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding

        binding.imageview.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callBack)
    }



    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }
}