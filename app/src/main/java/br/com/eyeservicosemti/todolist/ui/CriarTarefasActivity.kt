package br.com.eyeservicosemti.todolist.ui

import android.app.Activity
import android.content.Intent
import android.hardware.camera2.CaptureRequest
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Key
import br.com.eyeservicosemti.todolist.MainActivity
import br.com.eyeservicosemti.todolist.databinding.ActivityAddTarefaBinding
import br.com.eyeservicosemti.todolist.datasource.TarefasDataSource
import br.com.eyeservicosemti.todolist.extensions.format
import br.com.eyeservicosemti.todolist.extensions.text
import br.com.eyeservicosemti.todolist.model.Tarefas
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*

class CriarTarefasActivity: AppCompatActivity() {
    private lateinit var binding:ActivityAddTarefaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTarefaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra(TAREFAS_ID)){
            val tarefasId = intent.getIntExtra(TAREFAS_ID, 0)
            TarefasDataSource.findById(tarefasId)?.let {
              binding.tilTitulo.text    = it.titulo
              binding.tilDescricao.text = it.descricao
              binding.tilData.text      = it.data
              binding.tilHora.text      = it.hora
            }
        }
        insertListeners()


    }

    private fun insertListeners() {
        binding.editData?.setOnClickListener{
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timezone = TimeZone.getDefault()
                val offset = timezone.getOffset(Date().time) * -1

                binding.tilData.text = Date(it + offset).format()

            }
            datePicker.show(supportFragmentManager,"DATEPICKER_TAG")

        }

      binding.tilHora.editText?.setOnClickListener {
          val timePicker = MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).build()
          timePicker.addOnPositiveButtonClickListener{
              val hora = if (timePicker.hour in 0..9) "0${timePicker.hour}" else timePicker.hour
              val minuto = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
              binding.tilHora.text = "$hora : $minuto"
          }
          timePicker.show(supportFragmentManager,"TIMEPICKER_TAG")
      }

        binding.btnCriar.setOnClickListener{
            val tarefa = Tarefas(
                titulo    = binding.tilTitulo.text,
                descricao = binding.tilDescricao.text,
                data      = binding.tilData.text,
                hora      = binding.tilHora.text,
                id        = intent.getIntExtra(TAREFAS_ID,0)
            )
            TarefasDataSource.inserirTarefa(tarefa)
            setResult(Activity.RESULT_OK, Intent.makeRestartActivityTask(callingActivity))
            finish()
        }

        binding.btnCancelar.setOnClickListener{
            setResult(Activity.RESULT_OK, Intent.makeRestartActivityTask(callingActivity))
            finish()
        }


    }

    companion object{
        const val TAREFAS_ID = "tarefas_id"
    }

}