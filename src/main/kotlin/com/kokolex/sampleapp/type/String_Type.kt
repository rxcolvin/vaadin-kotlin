package com.kokolex.sampleapp.type

interface String_Type : Type {
    val minLength: Int
    val maxLength: Int
    val format: String
    val formatRegex: String
}

interface String_Field: Field<String_Type> {
    val label: String?
    val description: String?
    val searchable: Boolean?
    val textSearchable: Boolean?
    val minColumnWidth: Int?
    val maxColumnWidth: Int?
}

sealed interface String_Query {
    data class Equals(val value: String) : String_Query
    data class Contains(val value: String) : String_Query
}