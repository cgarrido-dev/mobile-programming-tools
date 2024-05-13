package com.example.claudio_garrido_20240507_continuidad_ing_informatica

import android.os.Bundle
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.widget.ListView
import android.widget.AdapterView

class MainActivity : AppCompatActivity() {
    private lateinit var toyListView: ListView
    private lateinit var toyAdapter: ToyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toyListView = findViewById(R.id.toyListView)
        toyAdapter = ToyAdapter(this, ToyData.toyList)
        toyListView.adapter = toyAdapter
        toyListView.setOnItemClickListener { _, _, position, _ ->
            val selectedToy = toyAdapter.getItem(position)
            val intent = Intent(this, ToyDetailActivity::class.java).apply {
                putExtra("TOY_ID", selectedToy.id)
            }
            startActivity(intent)
        }

        registerForContextMenu(toyListView)
    }

    override fun onCreateContextMenu(menu: android.view.ContextMenu?, v: android.view.View?, menuInfo: android.view.ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu?.setHeaderTitle("Acciones")
        menu?.add(0, v?.id ?: 0, 0, "Eliminar")
    }

    override fun onContextItemSelected(item: android.view.MenuItem): Boolean {
        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
        return when (item.title) {
            "Eliminar" -> {
                toyAdapter.removeItem(info.position)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}