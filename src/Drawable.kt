import processing.core.PApplet
import processing.core.PVector

class DrawableNode(val node: Int, var color: Triple<Int, Int, Int>, val position: PVector) {

    fun draw(dict: HashMap<String, Int>) {
        p.pushStyle()

        p.rectMode(PApplet.CENTER)

        p.fill(color.first.toFloat(), color.second.toFloat(), color.third.toFloat())
        p.ellipse(position.x, position.y, 50f, 50f)

        p.textAlign(PApplet.CENTER)
        p.textSize(20f)
        p.fill(0)
        p.text("$node", position.x, position.y)


        p.fill(0)
        p.textSize(20f)
        p.textAlign(PApplet.CENTER)
        p.rectMode(PApplet.CENTER)

        var posToDrawText = PVector.sub(position, PVector(0f, -20f))

        dict.entries.forEach {
            p.text("${it.key}: ${it.value}", posToDrawText.x, posToDrawText.y)
            posToDrawText = PVector.sub(posToDrawText, PVector(0f, -20f))
        }



        p.popStyle()
    }

    fun detectIfClicked(): Boolean = PVector.dist(position, PVector(p.mouseX.toFloat(), p.mouseY.toFloat())) < 50
    fun detectIfArea(node: PVector): Boolean = PVector.dist(position, node) < 50
}

class DrawableEdge(
    val num: Int,
    val from: PVector,
    val to: PVector,
    var color: Triple<Int, Int, Int> = Triple(0, 0, 0)
) {

    val position = PVector.add(from, to).div(2f)

    var posToDrawText = PVector.sub(position, PVector(0f, -20f))

    init {
        listClickableValues.add(ListenerForEdgeValue(
            posToDrawText,
            {
                graph.listOfEdges.first { it.index == num }.value++
            },
            {
                graph.listOfEdges.first { it.index == num }.value--
            }
        ))

    }

    fun draw(offset: Float, dict: HashMap<String, Int>) {

        val value = graph.listOfEdges.first { it.index == num }.value

        p.pushStyle()

        p.fill(0)
        p.textSize(20f)
        p.textAlign(PApplet.CENTER)
        p.rectMode(PApplet.CENTER)

        p.strokeWeight(2f)
        p.stroke(color.first.toFloat(), color.second.toFloat(), color.third.toFloat())


        val edge = graph.listOfEdges.first { it.index == num }



        if (edge.from == edge.to) {
            p.pushStyle()

            p.fill(backgroundColor, 0f)

            p.ellipseMode(PApplet.BOTTOM)
            p.ellipse(position.x, position.y, 100f + offset, 100f + offset)

            p.popStyle()
        } else {
            p.line(from.x + offset, from.y + offset, to.x + offset, to.y + offset)
        }


        dict.entries.forEach {
            p.text("${it.key}: ${it.value}", posToDrawText.x, posToDrawText.y)
            posToDrawText = PVector.sub(posToDrawText, PVector(0f, -20f))
        }


        if (directed) {
            p.text("$value", position.x, position.y)
        }

        p.popStyle()
    }

}

data class ListenerForEdgeValue(val pos: PVector? = null, var add: () -> Unit = {}, var sub: () -> Unit = {}) {
    fun clicked(): Boolean = pos?.dist(PVector(p.mouseX.toFloat(), p.mouseY.toFloat()))!! < 25
}