package com.redditmessaging.views.main_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.redditmessaging.databinding.PartDefaultLoadStateBinding


/**
 * Action to be executed when Try Again button is pressed
 */
typealias TryAgainAction = () -> Unit

/**
 * This adapter is used for rendering the load state (ProgressBar, error message and Try Again button)
 * in the list's header and footer.
 */
/**
 * This adapter is used for rendering the load state (ProgressBar, error message and Try Again button)
 * in the list's header and footer.
 */
class DefaultLoadStateAdapter(
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PartDefaultLoadStateBinding.inflate(inflater, parent, false)
        return Holder(binding, null)
    }

    /**
     * The same layout is used for:
     * - footer
     * - main indicator
     */
    class Holder(
        private val binding: PartDefaultLoadStateBinding,
        private val swipeRefreshLayout: SwipeRefreshLayout?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) = with(binding) {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                progressBar.isVisible = false
            } else {
                progressBar.isVisible = loadState is LoadState.Loading
            }
        }
    }

}