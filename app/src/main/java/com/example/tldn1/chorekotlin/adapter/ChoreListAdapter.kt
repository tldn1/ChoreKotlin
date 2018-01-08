package com.example.tldn1.chorekotlin.adapter

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.tldn1.chorekotlin.R
import com.example.tldn1.chorekotlin.data.DatabaseHandler
import com.example.tldn1.chorekotlin.model.ChoreModel
import kotlinx.android.synthetic.main.saving_chore.view.*

/**
 * Created by tldn1 on 1/7/2018.
 */
class ChoreListAdapter(private val list: ArrayList<ChoreModel>, private val context: Context, private val activity: Activity) : RecyclerView.Adapter<ChoreListAdapter.MyViewHolder>() {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder?, position: Int) {

        holder!!.bindItem(list[position])


        var db = DatabaseHandler(context)
        holder.imgDelete!!.setOnClickListener {
            db.deleteChore(list[position].id!!)
            list.removeAt(position)
            notifyDataSetChanged()

            Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show()


        }
        holder.imgEdit!!.setOnClickListener {

            var builder: AlertDialog.Builder = AlertDialog.Builder(context)

            // get the layout inflater

            var v = activity.layoutInflater.inflate(R.layout.saving_chore, null)
            v.enterChoreId.setText(list[position].choreName)
            v.assignToId.setText(list[position].choreAssignedTo)
            v.assignedById.setText(list[position].choreAssignedBy)
            builder.setView(v)
                    .setPositiveButton("Save", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->

                        if (!TextUtils.isEmpty(v.enterChoreId.text) && !TextUtils.isEmpty(v.assignToId.text) && !TextUtils.isEmpty(v.assignedById.text)) {
                            var choreName = "${v.enterChoreId.text}"
                            var choreAssignedBy = "${v.assignToId.text}"
                            var choreAssignedTo = "${v.assignedById.text}"

                            db.editChore(list[position].id!!, choreName, choreAssignedTo, choreAssignedBy)
                            list[position].choreName = choreName
                            list[position].choreAssignedTo = choreAssignedTo
                            list[position].choreAssignedBy = choreAssignedBy

                            notifyItemChanged(position, list[position])

                            Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
                        }else{
                            Toast.makeText(context, "You need to fill all fields", Toast.LENGTH_SHORT).show()

                        }

                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->

                    }).show()


        }


    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.chore_list_item, parent, false)

        val holder = MyViewHolder(view)

        return holder
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgEdit: ImageView? = null
        var imgDelete: ImageView? = null
        fun bindItem(model: ChoreModel) {

            var choreName: TextView = itemView.findViewById(R.id.choreName)
            var assBy: TextView = itemView.findViewById(R.id.assBy)
            var assTo: TextView = itemView.findViewById(R.id.assTo)
            imgEdit = itemView.findViewById(R.id.edit)
            imgDelete = itemView.findViewById(R.id.delete)

            choreName.text = "Chore Name: ${model.choreName}"
            assBy.text = "Assigned By: ${ model.choreAssignedBy}"
            assTo.text = "Assigned To: ${model.choreAssignedTo}"
        }
    }
}