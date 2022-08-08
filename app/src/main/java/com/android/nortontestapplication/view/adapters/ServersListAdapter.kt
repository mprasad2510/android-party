package com.android.nortontestapplication.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.nortontestapplication.R
import com.android.nortontestapplication.databinding.FragmentServersListItemBinding
import com.android.nortontestapplication.model.ServersListItem

class ServersListAdapter() : RecyclerView.Adapter<MyViewHolder>() {

    private var serversListItem : List<ServersListItem>? = null

    fun setListData(serversListItem : List<ServersListItem>?) {
        this.serversListItem = serversListItem
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: FragmentServersListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_servers_list_item,
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(serversListItem!![position])
    }

    override fun getItemCount(): Int {
        if (serversListItem == null) return 0
        return serversListItem?.size!!
    }
}

class MyViewHolder(private val binding : FragmentServersListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(serversListItem: ServersListItem) {
            binding.textServer.text = serversListItem.name
            binding.textDistance.text = serversListItem.distance.toString().plus("km")
    }
}