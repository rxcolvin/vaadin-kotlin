import com.github.mvysny.karibudsl.v10.VaadinDsl
import com.github.mvysny.karibudsl.v10.init
import com.vaadin.flow.component.HasComponents
import com.vaadin.flow.component.textfield.TextField


//types.basetypes.kt
interface Type

interface Field<T: Type> {
    val type: T
}

interface Entity_Type {
    val primaryKey: Field<*>
}

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

interface Int_Type : Type {
    val lowerBound: Int?
    val upperBound: Int?
}

interface Int_Field: Field<Int_Type> {
    val label: String?
    val description: String?
    val searchable: Boolean?
}

//types.usertypes.kt
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

object UUID_Type: String_Type {
    override val minLength = 27
    override val maxLength = 27
    override val format = "Base64 encoded UUID"
    override val formatRegex = ""
}

abstract class UUID_Field: String_Field {
    final override val type = UUID_Type
}

object Age_Type: Int_Type {
    override val lowerBound = 0
    override val upperBound = 150
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

        object age : Int_Field {
            override val type = Age_Type
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









@VaadinDsl
fun (@VaadinDsl HasComponents).Name_UI(label: String? = null, block: (@VaadinDsl TextField).() -> Unit = {}): TextField
        = init(TextField(label), block)


