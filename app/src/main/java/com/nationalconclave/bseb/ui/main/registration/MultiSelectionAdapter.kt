package com.nationalconclave.bseb.ui.main.registration

import android.R
import android.content.Context
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView


class MultiSelectionAdapter : ArrayAdapter<String> {


    private var selectedItems: BooleanArray = booleanArrayOf()



    constructor(context: Context, resource: Int, data: List<String?>) : super(context, resource, data) {
        selectedItems = BooleanArray(data.size)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = super.getView(position, convertView, parent!!)
        val textView: TextView = view.findViewById(R.id.text1)
        if (selectedItems[position]) {
            textView.setTextColor(Color.BLUE) // Apply a different color to selected items
        } else {
            textView.setTextColor(Color.BLACK) // Reset the color for unselected items
        }
        return view
    }


    override fun isEnabled(position: Int): Boolean {
        return true // Enable all items for selection
    }

    fun getSelectedItems(): BooleanArray {
        return selectedItems
    }

    fun setSelectedItem(position: Int, isSelected: Boolean) {
        selectedItems[position] = isSelected
        notifyDataSetChanged()
    }

}