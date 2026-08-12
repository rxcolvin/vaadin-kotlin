# Predefined Types

The following blocks specifies some common types.
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
