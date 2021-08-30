package com.example.expensestracker.Utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.example.expensestracker.R
import com.example.expensestracker.Repositories.Entitiy.Item
import java.util.*


class ItemAlert(context: Context, handler: Handler, alertState: AlertState, item: Item?) :
    Dialog(context) {
    private lateinit var stateButton: Button
    private lateinit var itemnametxt: EditText
    private lateinit var itempricetxt: EditText
    private lateinit var Heading: TextView
    private lateinit var InputFieldsLayout: LinearLayout
    private var alertState: AlertState? = null
    private var item: Item? = null

    init {
        Objects.requireNonNull(window)?.setBackgroundDrawableResource(android.R.color.transparent)
        this.alertState = alertState
        this.item = item
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.additem)
        itemnametxt = findViewById(R.id.itemnametxt)
        itempricetxt = findViewById(R.id.itempricetxt)
        stateButton = findViewById(R.id.statebtn)
        Heading = findViewById(R.id.Heading)
        InputFieldsLayout = findViewById(R.id.InputFieldsLayout)
        stateButton.setOnClickListener(stateListener)
        when (alertState) {
            AlertState.ADD -> stateButton.setText(R.string.add)
            AlertState.DELETE -> {
                stateButton.setText(R.string.delete)
                Heading.setText(R.string.Are_you_sure_you_want_to_delete_this_item)
                InputFieldsLayout.visibility = View.GONE
            }
            AlertState.UPDATE -> {
                Heading.setText(R.string.Updating_item)
                stateButton.setText(R.string.update)
                itemnametxt.setText(item?.itemName)
                itempricetxt.setText(item?.itemPrice.toString())
            }
        }
    }


    private val stateListener = View.OnClickListener {
        if(alertState != AlertState.DELETE){
            if (TextUtils.isEmpty(itemnametxt.text)) {
                itemnametxt.setError(R.string.Please_enter_item_name.toString())
                return@OnClickListener
            }
            if (TextUtils.isEmpty(itempricetxt.text)) {
                itempricetxt.setError(R.string.Please_enter_price.toString())
                return@OnClickListener
            }
        }

        val msg = Message()


        var tempItem: Item
        when (alertState) {
            AlertState.ADD -> {
                msg.what = 1
                tempItem =
                    Item(itemnametxt.text.toString(), itempricetxt.text.toString().toDouble())
                msg.obj = tempItem
            }
            AlertState.DELETE -> {
                msg.what = -1
                msg.obj = this.item
            }
            AlertState.UPDATE -> {
                msg.what = 0
                this.item?.itemPrice = itempricetxt.text.toString().toDouble()
                this.item?.itemName = itemnametxt.text.toString()
                msg.obj = this.item
            }
        }

        handler.sendMessage(msg)
        dismiss()
    }


    companion object {
        private fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            //Find the currently focused view, so we can grab the correct window token from it.
            var view = activity.currentFocus
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = View(activity)
            }
            assert(imm != null)
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

enum class AlertState {
    ADD,
    UPDATE,
    DELETE
}