package com.example.walley.ui

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.walley.data.db.PhotosRepository
import com.example.walley.data.db.WalleyDatabase
import com.example.walley.data.network.FetchPhotosRepository
import com.example.walley.databinding.FragmentPhotosBinding

class PhotosFragment : Fragment() {

    private lateinit var binding : FragmentPhotosBinding
    private lateinit var viewModel: PhotosViewModel

    private val photosAdapter : PhotosAdapter by lazy {
        PhotosAdapter(layoutInflater, onItemClick = { bitmap ->
            getAppInternalPath()
            setWallpaper(bitmap)
        })
    }

    private val walleyDB by lazy {
        WalleyDatabase.initDatabase(requireContext())
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

        val photosRepository = PhotosRepository(walleyDB.getPhotosDao())
        val fetchPhotosRepository = FetchPhotosRepository(getVolley(), photosRepository)

        viewModel = ViewModelProvider(this, PhotosViewModel.Factory(fetchPhotosRepository, photosRepository)).get(PhotosViewModel::class.java)

//        viewModel.photoFetchStatus.observe(viewLifecycleOwner) { fetchStatus ->
//            if(fetchStatus != null){
//                when(fetchStatus){
//                    PhotosFetchStatus.FETCHING -> {
//                        binding.progressBar.visibility = View.VISIBLE
//                        binding.photosList.visibility = View.GONE
//                        binding.errorLabel.visibility = View.GONE
//                    }
//                    PhotosFetchStatus.FETCHED -> {
//                        binding.progressBar.visibility = View.GONE
//                        binding.photosList.visibility = View.VISIBLE
//                        binding.errorLabel.visibility = View.GONE
//                    }
//                    PhotosFetchStatus.FAILURE -> {
//                        binding.progressBar.visibility = View.GONE
//                        binding.photosList.visibility = View.GONE
//                        binding.errorLabel.visibility = View.VISIBLE
//                    }
//                }
//            }
//        }

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

    private fun setWallpaper(bitmap : Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(requireContext())
        wallpaperManager.setBitmap(bitmap)
        Toast.makeText(requireContext(), "Wallpaper set :P", Toast.LENGTH_SHORT).show()
    }

    private fun getVolley() : RequestQueue {
        return Volley.newRequestQueue(requireContext())
    }

//    private fun getAppInternalPath() : String {
//        val dirs = requireContext().getExternalFilesDirs(null)
//        val path = dirs[0].absolutePath
//        File("$path/huha.txt").createNewFile()
//        Toast.makeText(requireContext(), path, Toast.LENGTH_SHORT).show()
//        return path
//    }
}