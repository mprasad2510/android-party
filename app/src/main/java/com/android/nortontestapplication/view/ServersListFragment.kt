package com.android.nortontestapplication.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.nortontestapplication.R
import com.android.nortontestapplication.databinding.FragmentServersListBinding
import com.android.nortontestapplication.view.adapters.ServersListAdapter
import com.android.nortontestapplication.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServersListFragment : Fragment() {

    private lateinit var binding: FragmentServersListBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter1: ServersListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_servers_list, container, false)
        initRecyclerView()
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]
        viewModel.getAllServersList()
        binding.lifecycleOwner = this
        viewModel.serversList.observe(requireActivity() , Observer {
            adapter1.setListData(it)
            adapter1.notifyDataSetChanged()
            it.forEach { item->
                viewModel.insertServers(item)
            }
        })
        viewModel.message.observe(requireActivity(), Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        })
        return binding.root
    }

    private fun initRecyclerView() {
        binding.recyclerServersList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val decoration = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            adapter1 = ServersListAdapter()
            adapter = adapter1
        }
    }

}