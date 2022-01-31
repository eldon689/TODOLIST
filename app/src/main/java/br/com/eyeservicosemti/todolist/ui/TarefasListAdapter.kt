package br.com.eyeservicosemti.todolist.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.eyeservicosemti.todolist.R
import br.com.eyeservicosemti.todolist.databinding.ItemTarefaBinding
import br.com.eyeservicosemti.todolist.model.Tarefas

class TarefasListAdapter : ListAdapter<Tarefas,TarefasListAdapter.TarefasViewHolder>(DiffCallback()){
    var listenerEditar : (Tarefas) -> Unit = {}
    var listenerApagar : (Tarefas) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarefasViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTarefaBinding.inflate(inflater, parent, false)
        return TarefasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TarefasViewHolder, position: Int) {
      holder.bind(getItem(position))
    }

   inner class TarefasViewHolder(
        private val binding: ItemTarefaBinding): RecyclerView.ViewHolder(binding.root){
           fun bind(item:Tarefas) {
               binding.tvItemtitulo.text = item.titulo
               binding.tvItemdatahora.text = "${item.data} ${item.hora}"
               binding.imgMais.setOnClickListener{
                   showPopUp(item)
               }




           }

        private fun showPopUp(item: Tarefas) {
            val img_mais = binding.imgMais
            val popupMenu = PopupMenu(img_mais.context,img_mais)
            popupMenu.menuInflater.inflate(R.menu.popup_menu,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.editar -> listenerEditar(item)
                    R.id.apagar -> listenerApagar(item)

                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()

        }



    }


}// fim TarefasListAdapter



class DiffCallback: DiffUtil.ItemCallback<Tarefas>(){
    override fun areItemsTheSame(oldItem: Tarefas, newItem: Tarefas) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Tarefas, newItem: Tarefas) = oldItem == newItem

}
