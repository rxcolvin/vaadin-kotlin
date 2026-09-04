package com.kokolex.sampleapp.type

typealias UUID=String

object UUID_Type: String_Type {
    override val minLength = 27
    override val maxLength = 27
    override val format = "Base64 encoded UUID"
    override val formatRegex = ""
}

abstract class UUID_Field: String_Field {
    final override val type = UUID_Type
}