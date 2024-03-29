package com.kuantum.artbook.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kuantum.artbook.R
import com.kuantum.artbook.adapter.ArtAdapter
import com.kuantum.artbook.databinding.FragmentArtsBinding
import com.kuantum.artbook.viewmodel.ArtsViewModel
import javax.inject.Inject

class ArtsFragment @Inject constructor(
    private val adapter: ArtAdapter
) : Fragment(R.layout.fragment_arts) {

    private var fragmentBinding: FragmentArtsBinding? = null
    lateinit var viewModel: ArtsViewModel


    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val layoutPosition = viewHolder.layoutPosition
            val selectedArt = adapter.artList[layoutPosition]

            viewModel.deleteArt(selectedArt)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtsViewModel::class.java]


        val binding = FragmentArtsBinding.bind(view)
        fragmentBinding = binding

        subscribeToObservers()

        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding.recyclerview)

        binding.fab.setOnClickListener {
            findNavController().navigate(ArtsFragmentDirections.actionArtsFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            adapter.artList = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()

        fragmentBinding = null
    }
}