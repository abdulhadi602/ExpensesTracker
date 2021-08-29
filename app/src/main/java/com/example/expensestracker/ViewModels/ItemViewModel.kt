package com.example.expensestracker.ViewModels

import androidx.lifecycle.*
import com.example.expensestracker.Repositories.Entitiy.Item
import com.example.expensestracker.Repositories.Interface.Repo
import java.util.*

class ItemViewModel(repo : Repo)  : ViewModel() {
    private var repository : Repo
    init {
        repository = repo
    }
    private var state : MutableLiveData<ItemState> = MutableLiveData()

    private lateinit var newItem : Item
    val currentItemsList : LiveData<List<Item>> = Transformations.switchMap(state){
         when(state.value){
             ItemState.ADD_ITEM -> repository.addItem(newItem)
             ItemState.SHOW_TODAYS_LIST -> repository.getTodaysItems()
             ItemState.SHOW_ALL ->  repository.getAllItems()
             else -> repository.deleteAllItems()

         }
    }
    fun setNewItem(item : Item){
        newItem = item
        state.value = ItemState.ADD_ITEM
    }
    fun requestTodaysItems(){
        state.value = ItemState.SHOW_TODAYS_LIST
    }
    fun requestAll(){
        state.value = ItemState.SHOW_ALL
    }
    fun deleteAll(){
        state.value = ItemState.DELETE_ALL
    }
}
enum class ItemState{
    ADD_ITEM,
    SHOW_TODAYS_LIST,
    SHOW_ALL,
    DELETE_ALL
}