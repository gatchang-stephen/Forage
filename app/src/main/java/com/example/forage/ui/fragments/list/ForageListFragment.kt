package com.example.forage.ui.fragments.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.forage.R
import com.example.forage.databinding.FragmentForageListBinding
import com.example.forage.ui.adapter.ForageableListAdapter
import com.example.forage.ui.viewmodel.ForageableViewModel

class ForageListFragment : Fragment() {

    private var _binding: FragmentForageListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ForageableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        viewModel = ViewModelProvider(this)[ForageableViewModel::class.java]
        _binding = FragmentForageListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = ForageableListAdapter { forageable ->
            val action =
                ForageListFragmentDirections.actionForageListFragmentToForageableDetailFragment(
                    forageable.id
                )
            findNavController().navigate(action)
        }
        viewModel.getForageables.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.apply {
            recyclerView.adapter = adapter
            addForageableFab.setOnClickListener {
                findNavController().navigate(R.id.action_forageListFragment_to_addForageableFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}