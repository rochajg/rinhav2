package dev.rochajg.domain.type

interface LowerCaseNamedType {
    val name: String

    fun value(): String = this.name.lowercase()
}
