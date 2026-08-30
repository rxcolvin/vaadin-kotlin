package com.kokolex.lib.type

interface Type {

}

interface Field_Type : Type {}

interface String_Type : Field_Type {
    val length: Int?
}

interface Int_Type : Type {
    val lowerBound: Int?
    val upperBound: Int?
}

interface Field {
   val type: Field_Type
}

interface Entity_Type<E: Any> : Type {
    val fields: List<Field>
    val primaryKey: Field
}

interface Struct_Type : Type {
    val fields: List<Field>
}

object Name_Type : String_Type {
    override val length: Int = 32
}

object Email_Type : String_Type {
    override val length: Int = 255
}







