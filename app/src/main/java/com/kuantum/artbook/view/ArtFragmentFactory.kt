package com.kuantum.artbook.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.kuantum.artbook.adapter.ArtAdapter
import com.kuantum.artbook.adapter.ImageApiAdapter
import com.kuantum.artbook.adapter.LanguageSpinnerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val artAdapter: ArtAdapter,
    private val imageApiAdapter : ImageApiAdapter,
) : FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ArtsFragment::class.java.name -> ArtsFragment(artAdapter)
            ArtDetailsFragment::class.java.name -> ArtDetailsFragment(glide)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageApiAdapter)

            else -> super.instantiate(classLoader, className)
        }
    }

}