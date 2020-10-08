package com.example.movie.ui.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movie.R
import com.example.movie.ui.main.model.Movie
import com.example.movie.ui.main.viewmodel.MainActivityViewModel
import com.example.movie.utils.EndlessRecyclerScrollListener
import com.example.movie.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() ,SearchView.OnQueryTextListener{

    lateinit var recyclerViewAdapter: ContentListAdapter
    lateinit var progressBar: ProgressBar
    lateinit var view: View
    private val viewModel by lazy {
        ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }


    private lateinit var listener: EndlessRecyclerScrollListener

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title= "Film List"
        progressBar = findViewById(R.id.progress_bar)
        view = findViewById(R.id.view)

        search_recycler_view.layoutManager =
            GridLayoutManager(this, 2)


        listener = EndlessRecyclerScrollListener(
            search_recycler_view.layoutManager as GridLayoutManager,
            loadMore!!
        )
        search_recycler_view.addOnScrollListener(listener)

        viewModel.publishObject.subscribe {
            search_text.visibility = View.GONE
            search_recycler_view.visibility = View.VISIBLE
            if (response.size == 0) {
                if (it.size > 0) {
                    setAdapter(response)
                    recyclerViewAdapter.notifyDataSetChanged()
                    response.clear()
                    response.addAll(it)
                } else {
                    search_text.visibility = View.VISIBLE
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, "No records found. try again", Toast.LENGTH_LONG).show()
                }

            } else {
                hideLoadingItem()
                response.addAll(it)
                recyclerViewAdapter?.notifyDataSetChanged()
            }

        }

    }

    val loadMore: (Int) -> Unit = { current_page ->
        showLoadingItem()
        viewModel.getMovieList( queryGlobal,current_page)
    }

    private fun showLoadingItem() {
        val adapter = search_recycler_view.adapter
        if (adapter is ContentListAdapter) {
            adapter.showLoadingItem()
        }
    }

    private fun hideLoadingItem() {
        val adapter = search_recycler_view.adapter
        if (adapter is ContentListAdapter) {
            adapter.hideLoading()
        }
    }

    private fun resetListener() {
        listener.reset()
    }


    private var response: ArrayList<Movie?> = ArrayList()

    private var queryGlobal = ""
    override fun onQueryTextSubmit(query: String?): Boolean {
        progressBar.visibility = View.VISIBLE
        view.visibility = View.VISIBLE



        if (!query.isNullOrBlank() && query.length > 1) {
            query?.let {
                queryGlobal = it
                response.clear()

                resetListener()
                if (NetworkUtils.isNetworkConnected(this)) {
                    search_text.visibility = View.GONE
                    viewModel.getMovieList(it, 1)
                } else {
                    progressBar.visibility = View.GONE
                    view.visibility = View.GONE
                    Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show()
                }

            }


        } else
        {
            search_text.visibility = View.VISIBLE
            search_recycler_view.visibility = View.GONE
            progressBar.visibility = View.GONE


        }

        return false
    }

    private fun setAdapter(response: ArrayList<Movie?>) {
        progressBar.visibility = View.GONE
        recyclerViewAdapter =
            ContentListAdapter(response)
        search_recycler_view.adapter = recyclerViewAdapter
        recyclerViewAdapter.notifyDataSetChanged()
    }


    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }




    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        val search =
            menu?.findItem(R.id.action_search)?.actionView as SearchView
        search.setOnQueryTextListener(this)
        return true
    }




}
