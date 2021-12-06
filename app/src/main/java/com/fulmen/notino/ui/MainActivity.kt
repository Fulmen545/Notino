package com.fulmen.notino.ui

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fulmen.notino.R
import com.fulmen.notino.other.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {
    private val notinoViewModel: NotinoViewModel by viewModels()
    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        val recycler: RecyclerView =  findViewById(R.id.rvProducts)
        val progress: ProgressBar =  findViewById(R.id.progress)
        adapter = ProductAdapter(this)
        recycler.layoutManager = GridLayoutManager(this, 2)
        recycler.adapter = adapter



        notinoViewModel.res.observe(this,  {
            when(it.status){
                Status.SUCCESS -> {
                    progress.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                    it.data.let {res->
                        if (res?.vpProductByIds != null){
                            res.vpProductByIds.let { it1 -> adapter.submitList(it1) }
                        }else{
                            Toast.makeText(this, "No products available", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                Status.LOADING -> {
                    progress.visibility = View.VISIBLE
                    recycler.visibility = View.GONE
                }
                Status.ERROR -> {
                    progress.visibility = View.GONE
                    recycler.visibility = View.VISIBLE
                    Toast.makeText(this, "No products available", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }


}