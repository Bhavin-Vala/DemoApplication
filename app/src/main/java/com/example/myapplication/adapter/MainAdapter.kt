package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.UserEntity
import com.example.myapplication.app.AppController
import kotlinx.android.synthetic.main.row_main.view.*

class MainAdapter(
    private val onClickDelete: (userEntity: UserEntity, position: Int) -> Unit,
    private val onClickEdit: (userEntity: UserEntity) -> Unit
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    lateinit var list: ArrayList<UserEntity>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_main, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val list = list[position]
        holder.title.text = list.title
        holder.title.textSize = AppController.sharedPrefs?.textSize?.toFloat()!!
        holder.description.text = list.description
        holder.description.textSize = AppController.sharedPrefs?.textSize?.toFloat()!!
        holder.dateAndTime.text = list.dateAndTime
        holder.dateAndTime.textSize = AppController.sharedPrefs?.textSize?.toFloat()!!
        holder.delete.setOnClickListener {
            onClickDelete(list, position)
        }

        holder.edit.setOnClickListener {
            onClickEdit(list)
        }
    }

    override fun getItemCount(): Int {
        return if (this::list.isInitialized) {
            list.size
        } else {
            0
        }
    }

    fun addData(list: ArrayList<UserEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun removeAt(position: Int) {
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, list.size)
    }

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.tvTitle
        val description = itemView.tvDescription
        val dateAndTime = itemView.tvDateAndTime
        val delete = itemView.ivDelete
        val edit = itemView.ivEdit
    }
}