package br.com.eyeservicosemti.todolist.datasource

import android.app.Activity
import br.com.eyeservicosemti.todolist.model.Tarefas

object TarefasDataSource {
    private val list = arrayListOf<Tarefas>()
    fun getList() = list.toList()

    fun inserirTarefa(tarefa:Tarefas){
        if (tarefa.id == 0){
            list.add(tarefa.copy(id = list.size+1))
        } else{
            list.remove(tarefa)
            list.add(tarefa)
        }


    }

    fun findById(tarefasId: Int) = list.find {it.id == tarefasId}
    fun apagarTarefa(tarefa: Tarefas) {
        list.remove(tarefa)
    }

}
