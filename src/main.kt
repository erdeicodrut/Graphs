import processing.core.PApplet
import processing.core.PVector
import java.lang.Exception

lateinit var p: PApplet

var lastNodeClicked = -1

val graph = Graph()


class App : PApplet() {
    var indexOfNodes = 0
    val listOfDrawableNodes = arrayListOf<DrawableNode>()
    val listOfDrawableEdges = arrayListOf<DrawableEdge>()

    override fun settings() {
        size(800, 600)
        p = this
    }

    override fun setup() {
        frameRate(20f)
        rectMode(CENTER)
    }

    override fun mouseClicked() {

        if (keyPressed && key == ' ') {
            println(keyPressed)
            for (it in listOfDrawableNodes) {
                val clicked = it.detectIfClicked()
                println("clicked ${it.node}")

                if (!clicked) continue

                if (lastNodeClicked == -1) lastNodeClicked = it.node
                else {
                    // Add value later
                    addEdge(lastNodeClicked, it.node, 1, Triple(0, 0, 0))
                    lastNodeClicked = -1
                }
            }
        } else {
            addNode(indexOfNodes++)
        }
    }

    private fun addNode(num: Int) {
        try {
            if (
                listOfDrawableNodes.any { it ->
                    it.detectIfArea(
                        listOfDrawableNodes
                            .firstOrNull { it2 ->
                                it2.node == num
                            }!!.position
                    )
                }
            ) return
        } catch (e: Exception) {}

        graph.listOfNodes.add(Node(num))
        listOfDrawableNodes.add(
            DrawableNode(
                num,
                Triple(255, 255, 255),
                PVector(p.mouseX.toFloat(), p.mouseY.toFloat())
            )
        )
    }

    private fun addEdge(from: Int, to: Int, value: Int = 0, color: Triple<Int, Int, Int>) {

        val fromN = listOfDrawableNodes.first { it.node == from }
        val toN = listOfDrawableNodes.first { it.node == to }

        listOfDrawableEdges.add(
            DrawableEdge(
                fromN.position,
                toN.position,
                value,
                color
            )
        )
        graph.createEdge(from, to, value)
    }

    override fun draw() {
        background(128)

        listOfDrawableNodes.forEach(DrawableNode::draw)
        listOfDrawableEdges.forEach(DrawableEdge::draw)

    }
}

fun main(args: Array<String>) {
    PApplet.main(App::class.java)
}