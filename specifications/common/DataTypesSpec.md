# Overview
This page describes an informal notation that allows Entity data type to be defined, including the component
types that an Entity Type can be composed of.

It also describes the generated code that will be generated for each type.

# Properties
Type defintions consist of one or more properties that are defined using the following syntax:
```
    <propertyName>: <PropertyType>[?] [ = <defaultValue>]
```
where:
* propertyName is the name of the property that must be a valid kotlin identifier but with no underscores ("_") or dollar signs ("$")
* PropertyType is the type of the property that must be a valid kotlin identifier but with no underscores ("_") or dollar signs ("$")
  * Types are limited to kotlin primitives (for now)
* an optional ? indicates that the property is optional
* = indicates that the property optiona lhas a default value
* defaultValue is the default value of the property
* 

# Scalar Types

Scalar types are formed from kotlin primitives or other scalar types, typically from standard libraries. 
Each scalar type has a number of properties that can be used to configure new types based on the scalar type.
Typically, these properties are used to constrain the values that can be assigned to the type or are useful
in the UI or database layers. 

## String

Types that are represented as Kotlin Strings are defined in the syntax below
```
  <TypeName>: String {
    minLength: Int? = null
    maxLength: Int? = null
    formatDesc: String? = null
    formatRegex: String? = null
    dbType: String?= null
  }
```

where:
* TypeName is the unique name of the type that must be a valid kotlin identifier but with no underscores ("_") or dollar signs ("$")
* minLength is the minimum length of the string
* maxLength is the maximum length of the string
* formatDesc is a human-readable description of the format
* formatRegex is a regular expression that the string must match, if provided
* dbHType allows the database type to be specified: either VARCHAR or TEXT. If maxLength is set, then the default is VARCHAR, else it is TEXT

New types can be defined as indicated by the example below.
```
Name: String {
    minLength=2
    maxLength=30
    format=All characters except CR and tab
    formatRegex = null
  }
```

Generated Code for the Example
```
//NB Only generated once, for all String based types, if required
interface String_Type {
    val minLength: Int?
    val maxLength: Int?
    val format: String?
    val formatRegex: String?
}

typealias Name=String

object Name_Type: String_Type {
    override val minLength: Int = 2
    override val maxLength: Int = 30
    override val format: String = "All characters except CR and tab"
    override val formatRegex: String = ""
}
```
Note that the generated code does not include the dbType property. This wil just
be used for the database layer code generation directly.

## Integer
Types that are represented as a Kotlin Int are defined in the syntax below
```
  <TypeName>: Int {
    min: Int? = null
    max: Int? = null
  }
```

where:
* TypeName is the unique name of the type that must be a valid kotlin identifier but with no underscores ("_") or dollar signs ("$")
* min is the minimum value of the integer between Int.MIN_VALUE && Int.MAX_VALUE. If not provided, then the default is Int.MIN_VALUE
* max is the maximum value of the integer between Int.MIN_VALUE && Int.MAX_VALUE. If not provided, then the default is Int.MAX_VALUE
* min must be less than or equal to max

New types can be defined as indicated by the example below.
```
Age: Int {
    min=0
    max=120
  }
```

Generated Code for the Example
```
//NB Only generated once, for all String based types, if required
interface Int_Type {
    val min: Int?
    val max: Int?
}

typealias Age=Int

object Age_Type: Int_Type {
    override val min = 0
    override val max = 120
}
```

## Enumerated Types


## Struct Types

## Collection Types

## Reference Types

## Entity Types

Example
```
Foo: Entity {
    fields: {
        fooId: UUID {
            isPrimaryKey = true 
            label = Id          
        }
        name: Name {
            label = Foo Name
            description = The name of the Foo
            searchable =  true
            textSearchable =  true
            minColumnWidth = 10
            maxColumnWidth = 20
        }
        age: Age {
            label = "Foo Age" 
            description = The age of the Foo
            searchable = true
        }    
    }
    label = Foo
    description = A Foo
    defaultSearch=*
    defaultSort=name:asc   
}
```

Example Generated Code of the Entity example above with all its components.

NB The generated code would be created in separate files as noted in comments. 

The base package would be something like com.kokolex.sampleapp

```kotlin
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

```



