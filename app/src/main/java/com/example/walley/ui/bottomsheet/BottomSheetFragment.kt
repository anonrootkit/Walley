package com.example.walley.ui.bottomsheet

import android.app.WallpaperManager
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.walley.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream


class BottomSheetFragment(
    private val bitmap: Bitmap,
    private val photographerName: String,
    private val position: Int
) : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentBottomSheetBinding
    private lateinit var viewModel: BottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this, BottomSheetViewModel.Factory()).get(BottomSheetViewModel::class.java)

        binding.setWallpaper.setOnClickListener {
            val imagePath = getImageFromStorage()
            openWallpaperIntent(imagePath)
            Toast.makeText(requireContext(), imagePath, Toast.LENGTH_SHORT).show()
            dismiss()
        }

        binding.saveImage.setOnClickListener {
            saveBitmapInStorage()
            dismiss()
        }

        binding.shareWallpaper.setOnClickListener {
            shareWallpaper()
            dismiss()
        }
    }

    private fun saveBitmapInStorage(): String {
        val dirs = requireContext().getExternalFilesDirs(null)
        val imagePath = dirs[0].absolutePath+"/${photographerName}+$position.jpg"
        val file = File(imagePath)

        if (!file.exists()){
            file.createNewFile()
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Toast.makeText(requireContext(), "Wallpaper has been saved", Toast.LENGTH_SHORT).show()
            outputStream.close()
        }
        return file.absolutePath
    }

    private fun getImageFromStorage(): String {
        val path = saveBitmapInStorage()
        return path
    }

    private fun openWallpaperIntent(imagePath : String) {
        val myWallpaperManager = WallpaperManager.getInstance(requireContext())
        val intent = myWallpaperManager.getCropAndSetWallpaperIntent(getContentTypeImagePath(imagePath))
        startActivity(intent)
    }

    private fun getContentTypeImagePath(path: String): Uri {
        return FileProvider.getUriForFile(requireContext(), "com.example.walley.fileprovider", File(path))
    }

    private fun shareWallpaper() {
        val path = saveBitmapInStorage()
        val uri = getContentTypeImagePath(path)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        requireContext().startActivity(intent)
    }
}