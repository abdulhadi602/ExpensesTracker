package com.example.expensestracker

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.expensestracker.Models.NAV_STATE
import com.example.expensestracker.Repositories.Entitiy.Item
import com.example.expensestracker.Repositories.RoomRepo
import com.example.expensestracker.Static.StaticData
import com.example.expensestracker.Utils.AlertState
import com.example.expensestracker.Utils.DateUtils
import com.example.expensestracker.Utils.ItemAlert
import com.example.expensestracker.ViewModels.ItemViewModel
import com.example.skyjobtest.Activities.ItemsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {
private lateinit var itemViewModel : ItemViewModel
    private lateinit var roomRepo: RoomRepo
    private lateinit var addItemBtn : FloatingActionButton
    private lateinit var deleteItems :FloatingActionButton
    private lateinit var itemssRecyclerView: RecyclerView
    private lateinit var expenseNavBar : BottomNavigationView
    lateinit var rewardsAdapter: ItemsAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var totalAmmount : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.ExtendedLookup_toolbar)
        setSupportActionBar(toolbar)
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        totalAmmount = toolbar.findViewById(R.id.TotalMoney)
        roomRepo = RoomRepo(applicationContext)
        addItemBtn = findViewById(R.id.addItem)
        expenseNavBar = findViewById(R.id.expenseNavBar)
        deleteItems = findViewById(R.id.deleteItems)
        itemViewModel = ItemViewModel(roomRepo)


        itemssRecyclerView = findViewById(R.id.ItemList)

        linearLayoutManager = LinearLayoutManager(this)

        itemssRecyclerView.setLayoutManager(linearLayoutManager)
        itemssRecyclerView.setHasFixedSize(true)
        rewardsAdapter = ItemsAdapter(this,ItemHandler)
        itemssRecyclerView.adapter = rewardsAdapter
        itemViewModel.currentItemsList.observe(this, Observer {
            rewardsAdapter.setrewardsList(it)
            totalAmmount.setText(it.getTotal().toString())
            Log.d("NewData", it.toString())
        })
        deleteItems.setOnClickListener {
            itemViewModel.deleteAll()
        }
        addItemBtn.setOnClickListener {
         ItemAlert(this, ItemHandler,AlertState.ADD,null).show()
        }
        expenseNavBar.setOnNavigationItemSelectedListener(navListener)

        expenseNavBar.selectedItemId = R.id.CurrentItems
        itemViewModel.requestTodaysItems()
    }

    override fun onResume() {
        super.onResume()
        when(StaticData.navState){
            NAV_STATE.CURRENT_ITEMS -> itemViewModel.requestTodaysItems()
            NAV_STATE.HISTORICAL_ITEMS -> itemViewModel.requestAll()
        }

    }
    fun  List<Item>.getTotal() : Double{
        var total = 0.0
        for (item in this){
            total += item.itemPrice
        }
        return total
    }
    val ItemHandler = object:  Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val item : Item = msg.obj as Item
            when(msg.what){
                1 ->  itemViewModel.setNewItem(item)
                -1 -> itemViewModel.deleteItem(item)
                0 -> itemViewModel.updateItem(item)
            }

        }
    }
    var navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.CurrentItems -> {
                    itemViewModel.requestTodaysItems()
                    StaticData.navState = NAV_STATE.CURRENT_ITEMS
                }
                R.id.HistoricalItems -> {
                    itemViewModel.requestAll()
                    StaticData.navState = NAV_STATE.HISTORICAL_ITEMS
                }

            }

            true
        }
}

