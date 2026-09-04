package com.kokolex.sampleapp.type

interface Int_Type : Type {
    val lowerBound: Int?
    val upperBound: Int?
}

interface Int_Field: Field<Int_Type> {
    val label: String?
    val description: String?
    val searchable: Boolean?
}

sealed interface Int_Query {
    data class Equals(val value: Int) : Int_Query
}