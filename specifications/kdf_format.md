# Overiew
This document is a rough guide to the informal Kokolex Data Format (KDF), which is a simple text file format defining data structures 
and configurations.

It is not intended to be formally parsed or validated by any software, but read by human or AI engineers.

The format is a block structure that is similar to JSON, but with the following differences:
* All property elements are strings, arrays or compound objects. There are no formal types, like string, number, boolean or null.
These can still be expressed as strings, if desired. It is up to the reader to interpret
* simple string elements need not be quoted unless the leading or trailing space is significant, in which case a single or double quote,
used as the first or last character, denotes the beginning or end of the string. Otherwise leading and trailing spaces are stripped
* A multi-line string can be indicated by starting the line with % and ending with %% (as per KSON) or by opening and closing |
  ** Space in front of the first line can be ignored, and the first line is used to align the following lines
* Empty lines are ignored, unless they are part of a multi-line string
* If a leading space on the first line is required, the first line can be quoted with a single or double quote 
* Java comments are supported, but not in multi-line strings.
* In line comments are not supported
* properties and values can be separated by a colon or by an equal sign ('=')
* property names cannot contain spaces and must be valid Java identifiers.
* Compound objects can be indicated by {} bloc.  The opening brace must be on the same line as the property 
name, and the closing brace must be on its own line
* Arrays elements can be indicated by a [...] or {...} bloc. But not that a block using {...} must only contain properties
* (ie be a compund object bloc) or array elements
* Simple property values can have sub properties as shown below. This allows a property name to be assigned to
something that looks like an instance of a class
* A file could represent a compound object or an array as if the file where wrapped in {} bloc.

The example below attempts to higlight all features of the format.

```
aprop: 123 //This inline comment is not allowed
aname=John 
a_name:' leading space in name
b_name: trailing space in name '
c_name: quote in ' string
quoted: "'in quotes' 
//Below value maps to ""
blank: 
xyz=equals sign not colon
multiline: |
   This is a multi-line string
     with a leading space here
    and a trailing space here '
|

multilineWithLeadingSpace: |
   '  This is a multi-line string with leading space 
   and a trailing space here '
|

/*
This is a Java style block comment

Comopund object 
*/  

foo: Foo {
  bar: Bar 
  array: [
     Xyz {
        abc= 123
     }
     qpr
  ]
}

//Array using {}
//NB Commas are just part of the value - not a separator
array: {
    123
    456,
    789
}

// NB Blank lines are ignored
someChars: [
    a
    b
    
    c
]

//use "" or "" or " or ' to indicate a blank string
someCharsFixed: [
    a
    b
    ""
    c
]


 
```
