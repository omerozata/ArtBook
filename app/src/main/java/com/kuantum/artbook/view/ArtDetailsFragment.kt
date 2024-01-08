package com.kuantum.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.kuantum.artbook.R
import com.kuantum.artbook.databinding.FragmentArtDetailsBinding

class ArtDetailsFragment : Fragment(R.layout.fragment_art_details) {

    private var fragmentBinding : FragmentArtDetailsBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentArtDetailsBinding.bind(view)
        fragmentBinding = binding
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }
}