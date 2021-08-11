package com.ticketswap.assessment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ticketswap.assessment.base.BaseActivity
import com.ticketswap.assessment.databinding.ActivitySearchBinding
import com.ticketswap.assessment.util.LoadArtists
import com.ticketswap.assessment.util.ViewModelCommand
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity<SearchViewModel>() {

    override val viewModel by viewModel<SearchViewModel>()
    private lateinit var binding: ActivitySearchBinding

    lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.isRefreshing.observe(this, {
            binding.swiperefreshLayout.isRefreshing = it == true
        })

        binding.swiperefreshLayout.setOnRefreshListener {
            viewModel.searchButtonClicked(binding.searchInput.text.toString())
        }

        binding.buttonSearch.setOnClickListener {
            viewModel.searchButtonClicked(binding.searchInput.text.toString())
        }

        setupRecycler()
    }

    override fun handleViewModelCommand(command: ViewModelCommand) = when (command) {
        is LoadArtists -> {
            adapter.items = command.artists
            adapter.notifyDataSetChanged()
            true
        }
        else -> super.handleViewModelCommand(command)
    }

    private fun setupRecycler(){
        adapter = RecyclerAdapter()
        binding.recycler.adapter = adapter
        binding.recycler.layoutManager = LinearLayoutManager(this)
    }

    companion object {
        fun start(activity: Activity) {
            val intent = Intent(activity, SearchActivity::class.java)
            activity.startActivity(intent)
        }
    }
}
