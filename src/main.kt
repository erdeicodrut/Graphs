import processing.core.PApplet

class App : PApplet() {
    override fun settings() {
        size(800, 600)
    }

    override fun setup() {
        frameRate(60f)
        rectMode(CENTER)
    }

    override fun draw() {
        background(128)
    }
}

fun main(args: Array<String>) {
    PApplet.main(App::class.java)
}