# Standard Specifications
This document describes some standard specifications that can be applied to some of the entity types in applications. 

Other entity types may have different requirements which are described in the entity type specific specifications.

## Standard Entity CRUD Operations

For each entity type the following operations are supported:
1. A new entity for a can be created. If the unique id of the entity is a UUID then it is generated automatically.
2. A new entity can be saved or abandoned
2. An entity can be viewed
3. An entity when viewed can be opened for editing
4. A modified entity can be saved or abandoned
5. An entity being edited can be reset to its original state
6. An entity can be reloaded from the database
3. An entity when viewed can be deleted if they are not referenced by other data
4. Entities can be searched using criteria defined for a given entity type (could be none in which case all entities are returned)
5. If no criteria are specified, then all entities are returned
5. Search results can be ordered by one or more fields and order defined by the given type and/or a text search applied  one or more fields defined for a given entity type
6. Search results can be paginated with a default page size of 20 (or a user-defined value)
7. AIDE-MÉMOIRE: Bulk deletes

## Standard Entity UI

1. The standard UI implements the operations in the Standard Entity CRUD Operations section.
2. The UI view is divided int

## Scalar Field Types

Some fields are scalar types that are specified using informal structure definition.

```
<TypeName> : <Kotlin Class>  {
    <property1>: <value 1>
    ...
    <propertyN>: <value N>
}
```

where the properties are defined in the Field and Type Properties section below.

These could be used in the UI or DB or just for documentation.


## Enumerated Types
There are two types of enumerated types and a special Boolean Enumeration type.
```
<TypeName> : StringEnum {
    values=<value1>, <value2>, ... , <valueN>
}

<TypeName> : IntEnum {
    base=<number>
    values=<value1>,<value2>,... , <valueN>
}

<TypeName> : BooleanEnum {
    trueName=<value>
    falseName=<value>
}
```

## Struct Types
```
<TypeName> : Struct {
    fields=[
        <fieldName>: <FieldType> {
            <property1>: <value1>
            ...
            <propertyN>: <valueN>
        }
    ]
    description:<description>
    label:<label>    
}
```

## Entity Types
```
<TypeName> : Entity {
    fields=[
        <fieldName>: <FieldType> {
            <property1>: <value1>
            ...
            <propertyN>: <valueN>
        }
    ]
    primaryKey=<fieldName>
    description:<description>
    label:<label>    
}
```

## Field and Type Properties

Types and Fields can have the following properties. Field level properties override type level properties, if set:
```
minlength: the minimum length of the string used to size the database column and UI field. 0 if not set
maxlength: the maximum length of the string used to size the database column and UI field. If not set then the database column would be an unlimted length like a blob
format:describes the format of a string either as a regular expression or a set of constraints or reference to a known type (ie email address
description: description: describes  the purpose of the field
label: description: Label used in the UI view. Could be overridden by a specific Field or View
columnWidth: description
dbType: the database compatible type of the that a field of this type must be converted to when stored in the database, if it is not obvious
notes: free-form text used to add any missing behaviour or constraints
required: true if the field is required. (Field Only)
baseValue: (IntEnum Only) the base value - like 0 or 1  used to calculate the value of the enumerated type.
values: (StringEnum or IntEnum Only) the list of values for the enumerated type.
trueName: (BooleanEnum Only) the name of the value that represents true
falseName: (BooleanEnum Only) the name of the value that represents false
```

# Predefined Field Types

The following block specifies some common types.
```

UUID: String {
    format: Base36 encoded UUID
}
    
Name: String {
    minlength: 3
    maxlength: 20
    format: {
        characters: Letters+Spaces+"."+"-"+"'"
        startsWith: Letter
        endsWith: Letter
    }
    description: "A name"
    label: "Name"
    columnWidth: 10
}

EmailAddress: String {
    minlength: 3
    maxlength: 255
    format: valid email address
    description: "Email address"
    label: "Email"
    columnWidth: 10
}

YesOrNo: BooleanEnum {
    trueName: "Yes"
    falseName: "No"
}

Gender: StringEnum {
    values: Male, Female
}
```

# Example Entity and Struct Types

For reference the following are some example entity and struct types.
```
Bar: Struct {
    barName: Name {
        label: Bar Name
    }
}


/**
 NB Here primaryKey is infered from the id field name and UUID type
*/
User: Entity {
    fields=[
        id: UUID,
        name: Name
        email: EmailAddress {
            required: false
        }
        gender: Gender
        active: YesOrNo
    ]
}

Foo: Entity {
    fields=[
        id: UUID,
        name: Name
        tags: Set<String>
        bars: List<Bar>
        owner: &User
        others: Set<&User           
    ]
    primaryKey=id
}
```

# Code Generation




