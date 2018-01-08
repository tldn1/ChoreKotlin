package com.example.tldn1.chorekotlin.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.tldn1.chorekotlin.R
import com.example.tldn1.chorekotlin.adapter.ChoreListAdapter
import com.example.tldn1.chorekotlin.data.DatabaseHandler
import com.example.tldn1.chorekotlin.model.ChoreModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.saving_chore.view.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    var db: DatabaseHandler? = null
    private var adapter: ChoreListAdapter? = null
    private var choreList: ArrayList<ChoreModel>? = null
    private var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        db = DatabaseHandler(this)


        choreList = db!!.readAllChores()
        layoutManager = LinearLayoutManager(this)
        adapter = ChoreListAdapter(choreList!!, this, this)

        var divider: DividerItemDecoration = DividerItemDecoration(this, LinearLayoutManager.VERTICAL)

        recyclerView.addItemDecoration(divider)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.add) {
            var builder: AlertDialog.Builder = AlertDialog.Builder(this)

            // get the layout inflater

            var v = layoutInflater.inflate(R.layout.saving_chore, null)

            builder.setView(v)
                    .setPositiveButton("Save", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->
                        var dbHandler = DatabaseHandler(this)
                        var chore = ChoreModel()
                        if (!TextUtils.isEmpty(v.enterChoreId.text) && !TextUtils.isEmpty(v.assignToId.text) && !TextUtils.isEmpty(v.assignedById.text)) {

                            chore.choreName = "${v.enterChoreId.text}"
                            chore.choreAssignedBy = "${v.assignToId.text}"
                            chore.choreAssignedTo = "${v.assignedById.text}"
                            dbHandler.createChore(chore)


                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            Toast.makeText(this, "You need to fill all fields", Toast.LENGTH_SHORT).show()
                        }

                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener() { dialogInterface: DialogInterface, i: Int ->

                    }).show()
        }


        return super.onOptionsItemSelected(item)
    }


}
