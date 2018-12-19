import processing.core.PApplet
import processing.core.PVector
import java.lang.Exception

lateinit var p: PApplet

var lastNodeClicked = -1

val graph = Graph()

val directed = true

val backgroundColor = 128

val listClickableValues = arrayListOf<ListenerForEdgeValue>()


class App : PApplet() {
    var indexOfNodes = 0
    var indexOfEdges = 0
    val listOfDrawableNodes = arrayListOf<DrawableNode>()
    val listOfDrawableEdges = arrayListOf<DrawableEdge>()

    override fun settings() {
        size(800, 600)
        p = this
    }

    override fun setup() {
        frameRate(20f)
        rectMode(CENTER)
        noLoop()
//        redraw()
    }

    override fun mousePressed() {

        listClickableValues.forEach { if (it.clicked())  { if (key == 'd') it.sub() else it.add(); redraw(); return} }

        if (keyPressed && key == 'c') {
            listOfDrawableEdges[0].color = Triple(random(255f).toInt(), random(255f).toInt(), random(255f).toInt())
        }

        if (keyPressed && key == ' ') {
            println(keyPressed)
            for (it in listOfDrawableNodes) {
                val clicked = it.detectIfClicked()
                println("clicked ${it.node}")

                if (!clicked) continue

                if (lastNodeClicked == -1) lastNodeClicked = it.node
                else {
                    // Add value later
                    addEdge(lastNodeClicked, it.node, 1)
                    lastNodeClicked = -1
                }
            }
        } else {
            addNode()
        }


        redraw()

    }

    private fun addNode() {
        // Carpeala
        try {
            if (
                listOfDrawableNodes.any { it ->
                    it.detectIfArea(
                        PVector(p.mouseX.toFloat(), p.mouseY.toFloat())
                    )
                }
            ) return
        } catch (e: Exception) {
        }

        graph.listOfNodes.add(Node(indexOfNodes))
        listOfDrawableNodes.add(
            DrawableNode(
                indexOfNodes,
                Triple(255, 255, 255),
                PVector(p.mouseX.toFloat(), p.mouseY.toFloat())
            )
        )
        indexOfNodes++
    }

    private fun addEdge(from: Int, to: Int, value: Int = 0, color: Triple<Int, Int, Int> = Triple(0, 0, 0)) {

        val fromN = listOfDrawableNodes.first { it.node == from }
        val toN = listOfDrawableNodes.first { it.node == to }

        listOfDrawableEdges.add(
            DrawableEdge(
                indexOfEdges,
                fromN.position,
                toN.position,
                color
            )
        )
        graph.createEdge(from, to, value, indexOfEdges)

//        graph.listOfEdges.first { it.index == indexOfEdges }.dict["poc"] = random(5f).toInt()

        indexOfEdges++
    }

    fun addEdgeListener(temp: ListenerForEdgeValue) {
        listClickableValues.add(temp)
    }

    var imageNr = 0
    override fun draw() {
        background(backgroundColor)

        for ((index, drawable) in listOfDrawableEdges.withIndex()) {
            val hashMapOfEdge = graph.listOfEdges.first { drawable.num == it.index }.dict

            val howManyEdgesInTheSamePlaceSoFar = listOfDrawableEdges.subList(
                0,
                index
            ).filter { drawable.from == it.from && drawable.to == it.to || drawable.from == it.to && drawable.from == it.to }
                .size
                .toFloat()

            drawable.draw(pow(-1f, howManyEdgesInTheSamePlaceSoFar) * howManyEdgesInTheSamePlaceSoFar * 10/2, hashMapOfEdge)
        }

        listOfDrawableNodes.forEach {
            val hashMapOfNode = graph.listOfNodes.first {it2 -> it.node == it2.num }.dict

            it.draw(hashMapOfNode)
        }
        save("thing_$imageNr.png")

    }

}

fun main(args: Array<String>) {
    PApplet.main(App::class.java)
}