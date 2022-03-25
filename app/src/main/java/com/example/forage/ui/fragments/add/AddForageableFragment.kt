package com.example.forage.ui.fragments.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.forage.R
import com.example.forage.databinding.FragmentAddForageableBinding
import com.example.forage.model.Forageable
import com.example.forage.ui.viewmodel.ForageableViewModel

class AddForageableFragment : Fragment() {

    private val navArgs: AddForageableFragmentArgs by navArgs()
    private lateinit var forageable: Forageable
    private lateinit var viewModel: ForageableViewModel

    private var _binding: FragmentAddForageableBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[ForageableViewModel::class.java]
        _binding = FragmentAddForageableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navArgs.forageable
        if (id > 0) {
            viewModel.getForageable(id).observe(viewLifecycleOwner) {
                forageable = it
                bindForageable(forageable)
                binding.deleteBtn.visibility = View.VISIBLE
                binding.deleteBtn.setOnClickListener { deleteForageable(forageable) }
            }
        } else {
            binding.saveBtn.setOnClickListener { addForageable() }
            addForageable()
        }
    }

    private fun deleteForageable(forageable: Forageable) {
        viewModel.deleteForageable(forageable)
        findNavController().navigate(R.id.action_addForageableFragment_to_forageListFragment)
    }

    private fun addForageable() {
        if (isValidEntry()) {
            viewModel.addForageable(
                binding.nameInput.text.toString(),
                binding.locationAddressInput.text.toString(),
                binding.inSeasonCheckbox.isChecked,
                binding.notesInput.text.toString()
            )
            findNavController().navigate(R.id.action_addForageableFragment_to_forageListFragment)
        }
    }

    private fun bindForageable(forageable: Forageable) {
        binding.apply {
            nameInput.setText(forageable.name, TextView.BufferType.SPANNABLE)
            locationAddressInput.setText(forageable.address, TextView.BufferType.SPANNABLE)
            inSeasonCheckbox.isChecked = forageable.inSeason
            notesInput.setText(forageable.notes, TextView.BufferType.SPANNABLE)
            saveBtn.setOnClickListener { updateForageable() }
        }
    }

    private fun updateForageable() {
        if (isValidEntry()) {
            viewModel.updateForageable(
                id = navArgs.forageable,
                name = binding.nameInput.text.toString(),
                address = binding.locationAddressInput.text.toString(),
                inSeason = binding.inSeasonCheckbox.isChecked,
                notes = binding.notesInput.text.toString()
            )
            findNavController().navigate(R.id.action_addForageableFragment_to_forageListFragment)
        }
    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.nameInput.text.toString(), binding.locationAddressInput.text.toString()
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}