package com.kuantum.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kuantum.artbook.R
import com.kuantum.artbook.databinding.FragmentArtsBinding

class ArtsFragment : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding : FragmentArtsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }
}