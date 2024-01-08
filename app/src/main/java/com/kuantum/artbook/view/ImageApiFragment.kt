package com.kuantum.artbook.view

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kuantum.artbook.R
import com.kuantum.artbook.databinding.FragmentImageApiBinding

class ImageApiFragment : Fragment(R.layout.fragment_image_api) {

    private var fragmentBinding : FragmentImageApiBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentImageApiBinding.bind(view)
        fragmentBinding = binding

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