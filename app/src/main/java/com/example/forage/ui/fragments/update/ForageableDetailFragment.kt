package com.example.forage.ui.fragments.update

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.forage.R
import com.example.forage.databinding.FragmentForageableDetailBinding
import com.example.forage.model.Forageable
import com.example.forage.ui.viewmodel.ForageableViewModel

class ForageableDetailFragment : Fragment() {
    private lateinit var viewModel: ForageableViewModel
    private val navArgs: ForageableDetailFragmentArgs by navArgs()
    private lateinit var forageable: Forageable

    private var _binding: FragmentForageableDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[ForageableViewModel::class.java]
        _binding = FragmentForageableDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navArgs.forageable
        viewModel.getForageable(id).observe(viewLifecycleOwner) {
            forageable = it
            bindForageable()
        }
    }

    private fun bindForageable() {
        binding.apply {
            name.text = forageable.name
            location.text = forageable.address
            notes.text = forageable.notes
            if (forageable.inSeason) season.text = getString(R.string.in_season) else season.text =
                getString(R.string.out_of_season)

            editForageableFab.setOnClickListener {
                val action =
                    ForageableDetailFragmentDirections.actionForageableDetailFragmentToAddForageableFragment(
                        forageable.id
                    )
                findNavController().navigate(action)
            }
            location.setOnClickListener { launchMap() }
        }
    }

    private fun launchMap() {
        val address = forageable.address.let {
            it.replace(", ", ",")
            it.replace(". ", " ")
            it.replace(" ", "+")
        }
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}