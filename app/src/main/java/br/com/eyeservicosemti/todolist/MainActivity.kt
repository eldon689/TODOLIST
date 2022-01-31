package br.com.eyeservicosemti.todolist

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import br.com.eyeservicosemti.todolist.databinding.ActivityMainBinding
import br.com.eyeservicosemti.todolist.databinding.ItemTarefaBinding
import br.com.eyeservicosemti.todolist.datasource.TarefasDataSource
import br.com.eyeservicosemti.todolist.ui.CriarTarefasActivity
import br.com.eyeservicosemti.todolist.ui.TarefasListAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy { TarefasListAdapter() }

    private val pegarConteudo  = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        resultado: ActivityResult ->
        if (resultado.resultCode == Activity.RESULT_OK) {
            val intent = resultado.data
            adapter.submitList(TarefasDataSource.getList())
            updateList()

            adapter.listenerEditar = {
                val intent = Intent(this, CriarTarefasActivity::class.java)
                intent.putExtra(CriarTarefasActivity.TAREFAS_ID,it.id)
                atualizarTarefas(intent)
            }
            adapter.listenerApagar = {
                TarefasDataSource.apagarTarefa(it)
                updateList()
            }

        }


    }

    private fun atualizarTarefas(intente: Intent){
        pegarConteudo.launch(intente)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.rvTarefas.adapter = adapter
        setContentView(binding.root)

        binding.fabAdd.setOnClickListener{
            updateList()
            insertListeners()
        }

    } /// fim create

    private fun insertListeners() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding.rvTarefas.adapter = adapter
        pegarConteudo.launch(Intent(this, CriarTarefasActivity::class.java))
    }


    private fun updateList(){
        val list = TarefasDataSource.getList()
           adapter.submitList(list)
           //binding.includeEmptyState.emptyState.visibility = if (list.isEmpty()) View.VISIBLE
           //else View.GONE
          if (list.isEmpty()){
              binding.includeEmptyState.emptyState.visibility = View.VISIBLE
          } else{
              binding.includeEmptyState.emptyState.visibility = View.GONE
          }



    }


    companion object{
        private const val CRIAR_NOVA_TAREFA = 1
    }

}