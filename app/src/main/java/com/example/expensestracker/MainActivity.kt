package com.example.expensestracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.Repositories.Entitiy.Item
import com.example.expensestracker.Repositories.RoomRepo
import com.example.expensestracker.ViewModels.ItemViewModel
import com.example.skyjobtest.Activities.ItemsAdapter

class MainActivity : AppCompatActivity() {
private lateinit var iteViewModel : ItemViewModel
    private lateinit var roomRepo: RoomRepo
    private lateinit var addItemBtn : Button
    private lateinit var deleteBtn : Button
    private lateinit var todaysItems : Button

    private lateinit var itemssRecyclerView: RecyclerView
    lateinit var rewardsAdapter: ItemsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        roomRepo = RoomRepo(applicationContext)
        addItemBtn = findViewById(R.id.addItem)
        deleteBtn = findViewById(R.id.deleteItem)
        todaysItems = findViewById(R.id.todaysItems)
        iteViewModel = ItemViewModel(roomRepo)


        itemssRecyclerView = findViewById(R.id.ItemList)

        linearLayoutManager = LinearLayoutManager(this)

        itemssRecyclerView.setLayoutManager(linearLayoutManager)
        itemssRecyclerView.setHasFixedSize(true)
        rewardsAdapter = ItemsAdapter( this)
        itemssRecyclerView.adapter = rewardsAdapter
        iteViewModel.currentItemsList.observe(this, Observer {

            rewardsAdapter.setrewardsList(it)
              Log.d("NewData",it.toString())
        })
        var c = 1.0
        addItemBtn.setOnClickListener(View.OnClickListener {
            iteViewModel.setNewItem(Item("Test",c))
            c++
        })
        deleteBtn.setOnClickListener(View.OnClickListener {
            iteViewModel.deleteAll()
        })
        todaysItems.setOnClickListener(View.OnClickListener {
            iteViewModel.requestTodaysItems()
        })
        iteViewModel.requestAll()
    }

    override fun onResume() {
        super.onResume()
        iteViewModel.requestAll()
    }
}
