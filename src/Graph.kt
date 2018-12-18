class Graph {
    val listOfEdges = arrayListOf<Edge>()
    val listOfNodes = arrayListOf<Node>()

    fun getAdjacentNodesAndEdges(node: Int): List<Pair<Edge, Int>> {
        val listToReturn = ArrayList<Pair<Edge, Int>>()
        for (edge in listOfEdges) {
            if (edge.from == node) {
                listToReturn.add(Pair(edge, edge.to))
            }

        }
        return listToReturn
    }

    fun createEdge(from: Int, to: Int, value: Int = 0) {
        listOfEdges.add(Edge(from, to, value))
    }
}