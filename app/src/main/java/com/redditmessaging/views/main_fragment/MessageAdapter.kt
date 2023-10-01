package com.redditmessaging.views.main_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.redditmessaging.databinding.ItemMessageBinding
import com.redditmessaging.databinding.MessageCardItemBinding
import com.redditmessaging.model.request.DataX


/**
 * Adapter for rendering users list in a RecyclerView.
 */
class MessageAdapter : PagingDataAdapter<DataX, MessageAdapter.Holder>(
    FilmsDiffCallback()
) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val film = getItem(position) ?: return
        with(holder.binding) {
            film?.let { result ->
                firstCard.apply {
                    fillWithViewCard(this, result)
                }
            }
        }
    }

    private fun fillWithViewCard(filmCardItemBinding: MessageCardItemBinding, data: DataX) {
        filmCardItemBinding.apply {
            title.text=data.title
            rating.text = data.upvoteRatio?.toInt().toString()
            messageCount.text = data.numCrossposts.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemMessageBinding.inflate(inflater, parent, false)
        return Holder(binding)
    }


    class Holder(
        val binding: ItemMessageBinding
    ) : RecyclerView.ViewHolder(binding.root)

}


class FilmsDiffCallback : DiffUtil.ItemCallback<DataX>() {
    private val gson by lazy { Gson() }
    override fun areItemsTheSame(
        oldItem: DataX,
        newItem: DataX
    ): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(
        oldItem: DataX,
        newItem: DataX
    ): Boolean {
        return gson.toJson(oldItem) == gson.toJson(newItem)
    }
}
