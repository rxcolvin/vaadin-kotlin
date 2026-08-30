# Introduction

This goal of this document is to introduce patterns which can be applied to building a certain class of applications using
 kotlin-based technologies

This class of applications typically has the following characteristics - not all of these are necessarily present in a given 
application:
1. It manages data of one or more entity types.
2. It has a storage and retrieval mechanism, typically a database, although it could be a single serialised file (like spreadsheet model)
3. It has a UI for searching for, viewing and editing the data. (CRUD operations)
4. It could have an authenication and/or authorization mechanisms and UI feaures which support them.
5. It doesn't expose inbound or outbound public APIs 
6. Data maybe bulk imported or exported.
7. It may require an audit trail of changes to the data
8. Applications that require polymophic data types are not supported currently

# Data Structures

This section describes how entities and the fields they are composed of are defined.

## Entity Types
An entity is a complex type typically used to represent a primary data object in the application and stored
in a database. The entity is constructed of one or more fields, one of which must be the primary key/unique id, often a UUID.

The following technical fields are included in all entities unless otherwise specified and are not editable and automatically
enabled for search and ordering:
* _createdTimestamp: a UTCTimestamp of when the entity was created
* _updatedTimestamp: a UTCTimestamp the entity was last updated
* _createdBy: User ID of the user who created the entity, if the users are supported by the application
* _updatedBy: User ID of the user who last updated the entity, if the users are supported by the application

The following informal definition shows a complete entity definition with all the allowed properties
```
<TypeName> : Entity {
    fields=[
        <fieldName>: <TypeSpec>
    ]
    primaryKey=<pkFfieldName> //Must be a field in the entity
    description:<entityDescription>
    label:<entityLabel> 
    defaultSearch:<searchExpression>
    defaultOrder:[<orderElement>] 
}
```

where:
* <fieldName>: is the name of the field.
* <TypeSpec>: is a specification of the field type - see below for specfic type constructs.
* <TypeName>: is the name of the entity type. It must start with a capital letter and otherwise only contain alphanumrical characters and is limited in length to be ten less than what a variable name in kotlin can be.
* <pkFfieldName>: is the name of the primary key field. If no primary key is specified, then the first field in the list is used as the primary key, if it is of an appropriate type, like a UUID; otherwise highlighted and an error is reported.
* <entityDescription>: is a free-form text block that describes the purpose of the entity.
* <entityLabel>: is a short text line that is used in the UI to label the entity. If not specified, then the entity name is used.
* <searchExpression>: specifies a default search criteria expression for the entity type. If not specified, then all entities are returned.
* <orderElement>: <fieldName>:<ASC|DESC>, where <fieldName> is the name of a field in the entity and <ASC|DESC> is the order direction.

## Field Types

There are the following types of fields:
* Scalar Types
* Enumerated Types
* Struct Types
* References to other Entity Types
* Lists of other types
* Sets of other types

## Scalar Types

Scalar types can be speficed in terms of a Kotlin scalar type and a set of properties as shown in the informal 

```
<TypeName> : <Kotlin Class>  {
    <property1>: <value 1>
    ...
    <propertyN>: <value N>
}
```

where:
* <TypeName>: is the name of the type. It must start with a capital letter and otherwise only contain alphanumrical characters and is limited in length to be ten less than what a variable name in kotlin can be. Note that all type names must be unique
* <Kotlin Class>: The Kotlin class that is used to represent the type.
* <property1>, <propertyN>: property names. 

Example:
```
EmailAddress: String {
    minlength: 3
    maxlength: 255
    format: valid email address
    description: Email address
    label: Email
    columnWidth: 10
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
A structure is a complex type that is typically used to construct a complex entity, typically where a field is a list
of Structs or embedded similarly in other Structs.

NB Nested Entities/Structs cannot contain circular references and should be highlighted before code generation.
```
<TypeName> : Struct {
    fields=[
        <fieldName>: <FieldType> {
            <property1>: <value1>
            ...
            <propertyN>: <valueN>
        }
    ]
    description:<textblock|textline>
    label:<textline>    
}
```


## Field and Type Properties

Types and Fields can have the following properties. Field level properties override type level properties, if set:
```
defaultSearch: defines the default search criteria boolean expression for a given entity type used when the user opens the Entity View - default value is "TRUE", meaning all entities are returned.
defaultOrder: defines the default order for a given entity type used when the user opens the Entity View - default value is "_createdTimestamp:ASC" which is the default order for entities.

```

# Predefined Field Types

The following block specifies some common types which can be used in the entity and struct types in applications without
being declared explicitly.
```


UUID: String {
    format: Base36 encoded UUID
}

UTCTimestamp: Instant {
    format: ISO 8601 to milliseconds precision
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

Boolean: BooleanEnum {
    trueName: "true"
    falseName: "false"
}

Gender: StringEnum {
    values: Male, Female
}
```

# Example Entity and Struct Types

For reference the following are some example entity and struct types.
```
Bar: Struct {
    fields=[
        barName: Name {
            label: Bar Name
        }
    ]
    description: A bar
    label: Bar
}

User: Entity {
    fields=[
        id: UUID
        name: Name {
            label: User Name
            isName: true
            textSearchable: true
            searchable: true
        }
        email: EmailAddress {
            required: false
            textSearchable: true
            searchable: true
        }
        gender: Gender {
            searchable: true
        }
        active: YesOrNo {
            searchable: true
        }
    ]
    description: User
    label: User
}

Foo: Entity {
    fields=[
        id: UUID
        name: Name
        tags: Set<String> {
            description: |
              Example of multi-line 
              description
            |
        }
        bars: List<Bar>
        owner: User
        others: Set<User>           
    ]
    primaryKey=id,
    defaultSearch: TRUE
    defaultOrder: _createdTimestamp
}
```


## Standard Entity CRUD Behaviour

For each entity type the following operations are supported:
1. A new entity for a can be created. If the unique id of the entity is a UUID then it is generated automatically.
2. A new entity can be saved or abandoned
2. An entity can be viewed
3. An entity when viewed can be opened for editing
4. A modified entity can be saved or abandoned
5. An entity being edited can be reset to its original state
6. An entity can be reloaded from the database/data store
3. An entity when viewed can be deleted if they are not referenced by other data
4. Entities can be searched using criteria defined for a given entity type (could be none in which case all entities are returned)
5. If no criteria are specified, then all entities are returned
5. Search results can be ordered by one or more fields and order defined by the given type and/or a text search applied  one or more fields defined for a given entity type
6. Search results can be paginated with a default page size of 20 (or a user-defined value)
7. AIDE-MÉMOIRE: Bulk deletes

## Authentication Methods
This section describes one or more authentication and authorization methods that can be used in applications.

### None
The application does not require any authentication or authorization and any one can access the application if they
have pysical access to the client application (or standalone application).

Usage scenarios include verysimple applications or prototypes where the application is not intended to be used by
a large number of users.

### Email+Password
Users are created by a User with an Admin Role by assigning unique email address and a password which are used by a user
to log in.

An administrator can force a user to reset their password the next time they log in.

An administrator can change the password of a user.

Users are forced to login before they can access the application.

A User may logout and be returned to the login screen.

A user may update their password in a secure manner.


## User Interface (UI)

The UI for the application will support the requirements of any Authenticaton Methods and Authorization Methods - see later.

The UI for the application will support a View for each entity type defined in the application.

The UI for a given application allows for a user to interact with all the entities that are defined in the application
and where the given user has the appropriate permissions to access them, if any.

AIDE-MEMOIR: Other operations for consideration:
* email generation
* website generation
* Notifications

The UI provides a way of navigating between features and views.


The sections below describe different approaches to the presentation of UI. (Only one for now)

### Entity View
The standard UI implements the operations in the Standard Entity CRUD Operations section.

The Entity View consists of a number of sub-views noted below:


### Search ResultsView
The Search Results View is a list of entities that match the search criteria and can be sorted and paginated. When the 
Entity view is first opened, then a default search criteria and order is applied as specified for the Entity type.










# Code Generation




