package br.com.eyeservicosemti.todolist.model

data class Tarefas(
    val titulo   : String,
    val descricao:String,
    val data     :String,
    val hora     :String,
    val id       : Int = 0
){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Tarefas

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}
