import kotlin.collections.HashMap

data class Edge(val from: Int, val to: Int, val value: Int, val index: Int, val dict: HashMap<String, Int> = hashMapOf())

data class Node(val num: Int, val dict: HashMap<String, Int> = hashMapOf())