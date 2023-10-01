package com.redditmessaging.views.main_fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.redditmessaging.databinding.FragmentMainBinding
import com.redditmessaging.di.ConnectKoinModules.mainScreenScope
import com.redditmessaging.model.datasource.AppState
import com.redditmessaging.simpleScan
import com.redditmessaging.views.MainViewModel
import com.redditmessaging.views.base_for_cinema.BaseFragment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class MainFragment : BaseFragment<AppState, FragmentMainBinding>(FragmentMainBinding::inflate) {

    lateinit var model: MainViewModel

    private lateinit var mainLoadStateHolder: DefaultLoadStateAdapter.Holder


    private val adapter: MessageAdapter by lazy { MessageAdapter() }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMessagesList()
    }

    private fun setupMessagesList() {

        // in case of loading errors this callback is called when you tap the 'Try Again' button

        val footerAdapter = DefaultLoadStateAdapter()

        // combined adapter which shows both the list of users + footer indicator when loading pages

        val adapterWithLoadState = adapter
            .withLoadStateFooter(footer = footerAdapter)
        binding.apply {
            messagesRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = adapterWithLoadState
                (itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations = true
            }
            mainLoadStateHolder = DefaultLoadStateAdapter.Holder(
                loadStateView,
                swipeRefreshLayout
            )
        }
        observeMessages()
        observeLoadState()
        handleScrollingToTopWhenSearching()
        handleListVisibility()
    }

    private fun observeMessages() {
        val viewModel: MainViewModel by lazy { mainScreenScope.get() }
        model = viewModel
        startViewModelFlow()
    }

    private fun startViewModelFlow(){
        lifecycleScope.launch {
            model.messagesFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }
    private fun observeLoadState() {
        // you can also use adapter.addLoadStateListener
        lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200).collectLatest { state ->
                // main indicator in the center of the screen
                mainLoadStateHolder.bind(state.refresh)
            }
        }
    }

    private fun handleScrollingToTopWhenSearching() = lifecycleScope.launch {
        // list should be scrolled to the 1st item (index = 0) if data has been reloaded:
        // (prev state = Loading, current state = NotLoading)
        getRefreshLoadStateFlow()
            .simpleScan(count = 2)
            .collectLatest { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    binding.messagesRecyclerView.scrollToPosition(0)
                }
            }
    }

    private fun handleListVisibility() = lifecycleScope.launch {
        // list should be hidden if an error is displayed OR if items are being loaded after the error:
        // (current state = Error) OR (prev state = Error)
        //   OR
        // (before prev state = Error, prev state = NotLoading, current state = Loading)
        getRefreshLoadStateFlow()
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.messagesRecyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(): Flow<LoadState> {
        return adapter.loadStateFlow.map { it.refresh }
    }
}




