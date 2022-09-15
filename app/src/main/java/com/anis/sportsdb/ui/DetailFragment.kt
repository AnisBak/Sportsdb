package com.anis.sportsdb.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.anis.sportsdb.databinding.FragmentDetailsBinding

private const val TAG = "DetailFragment"
class DetailFragment : Fragment(){

    private val args: DetailFragmentArgs by navArgs()
    private var _binding: FragmentDetailsBinding? = null
    private val binding
        get() = checkNotNull(_binding){
            "Cannot access binding because it is null. Is the view visible?"
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(layoutInflater,container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ${args.data[0]}")
        binding.apply {
            picture.load(args.data[0])
            namePlayerDetail.text = args.data[1]
            description.text = args.data[2]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}