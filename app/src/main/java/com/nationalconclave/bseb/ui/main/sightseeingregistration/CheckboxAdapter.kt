package com.nationalconclave.bseb.ui.main.sightseeingregistration

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import com.nationalconclave.bseb.R

class CheckboxAdapter(context: Context, items: List<String>) :
    ArrayAdapter<String>(context, 0, items) {

    private val checkedPositions = MutableList(items.size) { false }
    private val list = ArrayList<String>()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context)
                .inflate(R.layout.list_item_checkbox, parent, false)
        }

        val checkBox = itemView!!.findViewById<RadioButton>(R.id.checkBox)
        val textView = itemView.findViewById<TextView>(R.id.textView)
        val llCheckBox = itemView.findViewById<LinearLayout>(R.id.ll_checkbox)

        checkBox.isChecked = checkedPositions[position]
        llCheckBox.setOnClickListener {
            checkBox.isChecked = checkBox.isChecked.not()
        }
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked){
                list.clear()
                list.add(getItem(position).toString())
            }
            else{
                list.remove(getItem(position).toString())
            }
            checkedPositions[position] = isChecked

        }
        textView.text = getItem(position)

        return itemView
    }

    fun getCheckedPositions(): List<Boolean> {
        return checkedPositions
    }

    fun getCheckedList() : ArrayList<String>{
        return list
    }
}