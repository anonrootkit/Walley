package com.example.walley.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.walley.R
import com.example.walley.data.FetchPhotosRepository
import com.example.walley.databinding.FragmentPhotosBinding

class PhotosFragment : Fragment() {

    private lateinit var binding : FragmentPhotosBinding
    private lateinit var viewModel: PhotosViewModel

    private val photosAdapter : PhotosAdapter by lazy {
        PhotosAdapter(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPhotosBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fetchPhotosRepository = FetchPhotosRepository(requireContext())

        viewModel = ViewModelProvider(this, PhotosViewModel.Factory(fetchPhotosRepository)).get(PhotosViewModel::class.java)

        viewModel.photoFetchStatus.observe(viewLifecycleOwner) { fetchStatus ->
            if(fetchStatus != null){
                when(fetchStatus){
                    PhotosFetchStatus.FETCHING -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.photosList.visibility = View.GONE
                        binding.errorLabel.visibility = View.GONE
                    }
                    PhotosFetchStatus.FETCHED -> {
                        binding.progressBar.visibility = View.GONE
                        binding.photosList.visibility = View.VISIBLE
                        binding.errorLabel.visibility = View.GONE
                    }
                    PhotosFetchStatus.FAILURE -> {
                        binding.progressBar.visibility = View.GONE
                        binding.photosList.visibility = View.GONE
                        binding.errorLabel.visibility = View.VISIBLE
                    }
                }
            }
        }

        viewModel.photoList.observe(viewLifecycleOwner) { list ->
            if (!list.isNullOrEmpty()){
                // Set the list to the adapter
                photosAdapter.submitList(list)
            }
        }

        binding.photosList.apply{
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
            adapter = photosAdapter
        }

    }
}