package com.kokolex.sampleapp.storage

import com.kokolex.sampleapp.data.Foo_Order_Item
import com.kokolex.sampleapp.data.Foo_Query
import com.kokolex.sampleapp.data.Order
import com.kokolex.sampleapp.type.Foo
import com.kokolex.sampleapp.type.UUID

interface Foo_DAO {
    fun getById(id: UUID): Foo?
    fun insert(foo: Foo)
    fun update(foo: Foo)
    fun delete(id: UUID)
    fun textSearch(
        query: String,
        pageNo: Int,
        pageSize: Int,
        order: Set<Order<Foo_Order_Item>>
    ): List<Foo>
    fun query(
        query: Foo_Query,
        pageNo: Int,
        pageSize: Int,
        order: Set<Order<Foo_Order_Item>>
    ): List<Foo>
}