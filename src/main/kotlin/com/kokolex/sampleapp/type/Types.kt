package com.kokolex.sampleapp.type

interface Type

interface Field<T: Type> {
    val type: T
}

interface Entity_Type {
    val primaryKey: Field<*>
}




typealias Name=String

object Name_Type: String_Type {
    override val minLength = 2
    override val maxLength = 30
    override val format = "All characters except CR and tab"
    override val formatRegex = ""
}

abstract class Name_Field: String_Field {
    final override val type = Name_Type
}

typealias Name_Query=String_Query

typealias Age=Int

object Age_Type: Int_Type {
    override val lowerBound = 0
    override val upperBound = 150
}

typealias Age_Query=Int_Query

abstract class Age_Field: Int_Field {
    final override val type = Age_Type
}

object Foo: Entity_Type {
    object fields {
        object fooId : UUID_Field() {
            override val label = "Id"
            override val description = "The Id of the Foo"
            override val searchable = true
            override val textSearchable = true
            override val minColumnWidth = 10
            override val maxColumnWidth = 20
        }

        object name : Name_Field() {
            override val label = "Foo Name"
            override val description = "The name of the Foo"
            override val searchable = true
            override val textSearchable = true
            override val minColumnWidth = 10
            override val maxColumnWidth = 20
        }

        object age : Age_Field() {
            override val label = "Foo Age"
            override val description = "The age of the Foo"
            override val searchable = true
        }
    }

    override val primaryKey = fields.fooId
    val label = "Foo"
    val description = "A Foo"
    val defaultSearch = "*"
    val defaultSort = "name:asc"
}
