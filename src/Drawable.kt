import processing.core.PApplet
import processing.core.PVector

interface Drawable {
    fun draw()
}

class DrawableNode(val node: Int, var color: Triple<Int, Int, Int>, val position: PVector) : Drawable {
    override fun draw() {
        p.pushStyle()

        p.rectMode(PApplet.CENTER)

        p.fill(color.first.toFloat(), color.second.toFloat(), color.third.toFloat())
        p.ellipse(position.x, position.y, 50f, 50f)

        p.textAlign(PApplet.CENTER)
        p.textSize(20f)
        p.fill(0)
        p.text("$node", position.x, position.y)

        p.popStyle()
    }

    fun detectIfClicked(): Boolean = PVector.dist(position, PVector(p.mouseX.toFloat(), p.mouseY.toFloat())) < 50
    fun detectIfArea(node: PVector): Boolean = PVector.dist(position, node) < 50
}

class DrawableEdge(val from: PVector, val to: PVector, val value: Int, var color: Triple<Int, Int, Int>): Drawable {
    override fun draw() {
        println("${from.x} ${from.y} ${to.x} ${to.y}")

        p.pushStyle()

        p.rectMode(PApplet.CENTER)

        p.strokeWeight(5f)
        p.stroke(color.first.toFloat(), color.second.toFloat(), color.third.toFloat())
        p.line(from.x, from.y, to.x, to.y)

        p.textAlign(PApplet.CENTER)
        p.textSize(20f)
        p.fill(0)

        val position = PVector.add(from, to).div(2f)

        p.text("$value", position.x, position.y)

        p.popStyle()
    }

}