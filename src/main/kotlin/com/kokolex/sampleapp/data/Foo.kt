package com.kokolex.sampleapp.data

import com.kokolex.sampleapp.type.Age
import com.kokolex.sampleapp.type.Age_Query
import com.kokolex.sampleapp.type.Name
import com.kokolex.sampleapp.type.Name_Query
import com.kokolex.sampleapp.type.UUID

data class Foo(
    val id: UUID, val name: Name, val age: Age
)

data class Foo_Query_Result_Item(
    val id: UUID,
    val name: Name,
    val age: Age,
    val _createdTimestamp: Long,
)

data class Foo_Query(
    val name: Name_Query? = null, val age: Age_Query? = null
)

interface Order_Item


enum class Foo_Order_Item : Order_Item {
    NAME, AGE, _CREATED_TIMESTAMP
}

data class Order<X : Order_Item>(val item: X, val isAscending: Boolean = true)


val defaultOrder = Order(Foo_Order_Item.NAME)
val defaultQuery = Foo_Query()

