


# General UI code patterns
The '../types/DataTypes.kspec.md' file defines a type model for the data types that will be used in the application.

In particular, it allows scalar types, enumerations and references to other types to be properties of a compound type.

So for each scalar type and enumeration type we would like to have a UI component that can be used to display and edit 
values of that type depending on the mode, edit or display.

## Scalar Types
Consider the following example, which uses Jetpack Compose UI components:

```koltin
@Composable
private fun String_UI(type: String_Type, state: String_State) {
// UI code
}

@Composable
fun Name_UI(state: String_State) {
   String_UI(type = Name_Type, state = state)
}

@Composable
fun UUID_UI(state: String_State) {
   String_UI(type = UUID_Type, state = state)
}



```

Here we have created a function that manages any String_Type that is specialised and parameterised for each defined
String Base Type using a clear naming convention.

> Note that the UI code is just demonstrating some principals and not mandating how the UI code should be implemented using 
> Compose. In particular, we assume that the mode is part of the state, but could be passed as a separate parameter.

## Enumeration Types
TODO

## Reference Types

Each entity referenced from another entity will require a complex UI component that allows a user to edit and display
the referenced entity. The complexities are:
* In display mode the UI needs access to the haum name of the referenced entity to display (although the reference could be null)
* In edit mode the UI also needs access to a search UI to find the referenced entity (and indirectly the functionality 
that is used to service search requests)

For an entity of type Person, the UI code would look like:

```koltin

@Composable
fun Person_Ref_UI(state: Person_Ref_State) {
// Complex UI code
}

```


## Collection Types
Work in progress.

```kotlin
@Composable
private fun Scalar_List(type: Scalar_Type, state: Scalar_List_State) {
// UI code
}

@Composable
private fun Compound_List(state: Compound_List_State) {
    // UI code
}

@Composable
fun Name_List_UI(state: Scalar_List_State) {
   Scalar_List(type = Name_Type, state = state)
}
```











