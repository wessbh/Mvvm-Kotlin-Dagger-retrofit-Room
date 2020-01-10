package com.wassimbh.projectdaggerretrofitmvvm.utils.listeners

import android.view.View

interface RecyclerViewItemClickListener {
    fun onClick(view: View, position: Int)
    fun onLongClick(view:View, position: Int)
}