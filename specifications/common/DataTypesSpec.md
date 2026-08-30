# Overview
This page 

# Scalar Types

## String 

```
  <TypeName>: String {
    minLength: Int? = null
    maxLength: Int? = null
    formatDesc: String? = null
    formatRegex: String? = null
  }
```

where:
* TypeName is the unique name of the type that must be a valid kotlin identifier but with no underscores ("_") or dollar signs ("$")
* minLength is the minimum length of the string
* maxLength is the maximum length of the string
* formatDesc is a human-readable description of the format
* formatRegex is a regular expression that the string must match

Example
```
Name: String {
    minLength=2
    maxLength=30
    format=All characters except CR and tab
    formatRegex = null
  }
```

Generated Code Example
```
typealias Name=String

object Name_Type {
    const val minLength: Int = 2
    const val maxLength: Int = 30
    const val format: String = "All characters except CR and tab"
    const val formatRegex: String = ""
}
```


