package co.touchlab.example

@Target(AnnotationTarget.CLASS)
annotation class Identifiable(
    val property: String = SELF_IDENTITY
) {
    companion object {
        const val SELF_IDENTITY = "<self>"
    }
}
