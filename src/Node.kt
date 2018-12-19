import kotlin.collections.HashMap

data class Edge(val from: Int, val to: Int, var value: Int, val index: Int, val dict: HashMap<String, Int> = hashMapOf()) {
    fun addValue() {
        value++
    }
}

data class Node(val num: Int, val dict: HashMap<String, Int> = hashMapOf())