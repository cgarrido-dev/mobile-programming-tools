package com.example.claudio_garrido_20240522_continuidad_ing_informatica

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.InputFilter
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var repository: AttendanceRepository
    private var selectedRecord: AttendanceRecord? = null
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        repository = AttendanceRepository(this)

        val editTextRut: EditText = findViewById(R.id.editTextRut)
        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextLastname: EditText = findViewById(R.id.editTextLastname)
        val buttonCheckIn: Button = findViewById(R.id.buttonCheckIn)
        val buttonCheckOut: Button = findViewById(R.id.buttonCheckOut)
        val textViewMessage: TextView = findViewById(R.id.textViewMessage)
        val buttonViewRecords: Button = findViewById(R.id.buttonViewRecords)
        val listViewRecords: ListView = findViewById(R.id.listViewRecords)
        val buttonShareRecord: Button = findViewById(R.id.buttonShareRecord)

        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

        editTextRut.filters = arrayOf(InputFilter { source, start, end, dest, dstart, dend ->
            val currentText = dest.toString()
            for (i in start until end) {
                val char = source[i]
                if (!char.isDigit() && (char.toUpperCase() != 'K' || currentText.contains('K'))) {
                    return@InputFilter ""
                }
            }
            if (currentText.length + (end - start) > 9) {
                return@InputFilter ""
            }
            null
        })

        val letterFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!source[i].isLetter()) {
                    return@InputFilter ""
                }
            }
            null
        }

        editTextName.filters = arrayOf(letterFilter)
        editTextLastname.filters = arrayOf(letterFilter)

        buttonCheckIn.setOnClickListener {
            hideKeyboard()
            val rut = editTextRut.text.toString()
            val name = editTextName.text.toString()
            val lastname = editTextLastname.text.toString()

            if (rut.isBlank() || name.isBlank() || lastname.isBlank()) {
                textViewMessage.text = "Todos los campos son obligatorios"
            } else if (!validateRUT(rut)) {
                textViewMessage.text = "RUT inválido"
            } else {
                val record = AttendanceRecord(
                    rut = rut,
                    name = name,
                    lastname = lastname,
                    dateTime = dateTimeFormat.format(Date()),
                    type = "Entrada"
                )
                repository.insertAttendanceRecord(record)
                showMessageAndClearFields("Entrada registrada correctamente", editTextRut, editTextName, editTextLastname, textViewMessage)
            }
        }

        buttonCheckOut.setOnClickListener {
            hideKeyboard()
            val rut = editTextRut.text.toString()
            val name = editTextName.text.toString()
            val lastname = editTextLastname.text.toString()

            if (rut.isBlank() || name.isBlank() || lastname.isBlank()) {
                textViewMessage.text = "Todos los campos son obligatorios"
            } else if (!validateRUT(rut)) {
                textViewMessage.text = "RUT inválido"
            } else {
                val record = AttendanceRecord(
                    rut = rut,
                    name = name,
                    lastname = lastname,
                    dateTime = dateTimeFormat.format(Date()),
                    type = "Salida"
                )
                repository.insertAttendanceRecord(record)
                showMessageAndClearFields("Salida registrada correctamente", editTextRut, editTextName, editTextLastname, textViewMessage)
            }
        }

        buttonViewRecords.setOnClickListener {
            val records = repository.getAllAttendanceRecords()
            val recordStrings = records.map {
                "${it.rut} - ${it.dateTime} - ${it.type}"
            }
            adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, recordStrings)
            listViewRecords.adapter = adapter
            listViewRecords.choiceMode = ListView.CHOICE_MODE_SINGLE

            listViewRecords.setOnItemClickListener { _, _, position, _ ->
                selectedRecord = records[position]
            }
        }

        buttonShareRecord.setOnClickListener {
            if (selectedRecord != null) {
                shareRecord(selectedRecord!!)
                selectedRecord = null
                listViewRecords.clearChoices()
                adapter.notifyDataSetChanged()
                showMessageAndClearFields("Registro compartido", editTextRut, editTextName, editTextLastname, textViewMessage, 5000)
            } else {
                textViewMessage.text = "Es necesario seleccionar un registro para compartirlo"
            }
        }
    }

    private fun showMessageAndClearFields(message: String, editTextRut: EditText, editTextName: EditText, editTextLastname: EditText, textViewMessage: TextView, duration: Long = 5000) {
        textViewMessage.text = message
        editTextRut.text.clear()
        editTextName.text.clear()
        editTextLastname.text.clear()

        Handler(Looper.getMainLooper()).postDelayed({
            textViewMessage.text = ""
        }, duration)
    }

    private fun shareRecord(record: AttendanceRecord) {
        val message = """
            RUT: ${record.rut}
            Fecha y Hora: ${record.dateTime}
            Tipo: ${record.type}
        """.trimIndent()

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        val chooser = Intent.createChooser(shareIntent, "Compartir registro a través de")
        if (shareIntent.resolveActivity(packageManager) != null) {
            startActivity(chooser)
        } else {
            Toast.makeText(this, "No hay aplicaciones disponibles para compartir", Toast.LENGTH_SHORT).show()
        }
    }

    private fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun validateRUT(rut: String): Boolean {
        if (!rut.matches(Regex("[0-9]{1,2}(.?[0-9]{3}){2}-?[0-9kK]"))) {
            return false
        }

        val rutf = rut.toLowerCase().replace(Regex("[-.]"), "")
        val rutl = rutf.takeLast(1)
        var sum = 0
        var i = 0
        for (x in rutf.dropLast(1).reversed()) {
            sum += (x.toString().toInt() * ((i % 6) + 2))
            i += 1
        }

        val div = 11 - (sum % 11)
        return (if (div == 11) 0 else div) == (if (rutl == "k") 10 else rutl.toInt())
    }
}