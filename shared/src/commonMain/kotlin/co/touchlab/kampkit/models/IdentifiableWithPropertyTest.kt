package co.touchlab.kampkit.models

import co.touchlab.example.Identifiable

@Identifiable(property = "name")
class IdentifiableWithPropertyTest(
    val name: String
)
