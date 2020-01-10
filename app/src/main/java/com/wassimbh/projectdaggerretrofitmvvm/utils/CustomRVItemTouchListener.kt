package com.wassimbh.projectdaggerretrofitmvvm.utils
import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class CustomRVItemTouchListener(context:Context, recyclerView: RecyclerView, clickListener:RecyclerViewItemClickListener):RecyclerView.OnItemTouchListener {
    //GestureDetector to intercept touch events
    internal var gestureDetector:GestureDetector
    private val clickListener:RecyclerViewItemClickListener
    init{
        this.clickListener = clickListener
        gestureDetector = GestureDetector(context, object:GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e:MotionEvent):Boolean {
                return true
            }
            override fun onLongPress(e:MotionEvent) {
                //find the long pressed view
                val child = recyclerView.findChildViewUnder(e.getX(), e.getY())
                if (child != null && clickListener != null)
                {
                    clickListener.onLongClick(child, recyclerView.getChildLayoutPosition(child))
                }
            }
        })
    }
    override fun onInterceptTouchEvent(recyclerView:RecyclerView, e:MotionEvent):Boolean {
        val child = recyclerView.findChildViewUnder(e.getX(), e.getY())
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e))
        {
            clickListener.onClick(child, recyclerView.getChildLayoutPosition(child))
        }
        return false
    }
    override fun onTouchEvent(rv:RecyclerView, e:MotionEvent) {
    }
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept:Boolean) {
    }
}