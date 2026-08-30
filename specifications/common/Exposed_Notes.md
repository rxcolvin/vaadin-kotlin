JetBrains Exposed supports a broad spectrum of relational database column types and maps them directly to native Kotlin data types. [1]
When a column type is declared, the framework establishes a 1:1 type-safe mapping, allowing the associated Kotlin data types to be used seamlessly as column values without any manual adaptation or custom conversion wrappers.
------------------------------
## Native Kotlin Mappings (Supported Out-of-the-Box)
The following core Kotlin types require zero configuration and can be assigned directly to their respective column types: [2]

| Kotlin Data Type | Exposed Table Extension Function | Default SQL Representation |
|---|---|---|
| Int | integer(name) | INT |
| Long | long(name) | BIGINT |
| Short | short(name) | SMALLINT |
| Byte | byte(name) | TINYINT / SMALLINT |
| Float | float(name) | REAL / FLOAT |
| Double | double(name) | DOUBLE PRECISION |
| BigDecimal | decimal(name, precision, scale) | DECIMAL / NUMERIC |
| Boolean | bool(name) | BOOLEAN / BIT / CHAR(1) |
| String | varchar(name, length) or text(name) | VARCHAR / TEXT |
| UUID | uuid(name) | UUID / BINARY(16) |
| ByteArray | binary(name, length) or blob(name) | VARBINARY / BLOB |

------------------------------
## Date and Time Data Types
Exposed natively supports Kotlin date and time values via two optional companion modules: exposed-java-time (for Java 8 native times) and exposed-kotlin-datetime (for multiplatform projects). [3]
Using either module allows the framework to automatically handle these types without custom mappings:

| Kotlin / Java Data Type | Exposed Function | Default SQL Representation |
|---|---|---|
| java.time.LocalDate / kotlinx.datetime.LocalDate | date(name) | DATE |
| java.time.LocalTime / kotlinx.datetime.LocalTime | time(name) | TIME |
| java.time.LocalDateTime / kotlinx.datetime.LocalDateTime | datetime(name) | DATETIME / TIMESTAMP |
| java.time.Instant / kotlinx.datetime.Instant | timestamp(name) | TIMESTAMP |
| java.time.Duration | duration(name) | BIGINT (Interval in nanos/millis) |

------------------------------
## Specialised Advanced Mappings
Beyond basic scalars, JetBrains Exposed includes helper extensions to seamlessly consume complex types natively: [1]
## 1. Enumerations (Enum)
Standard Kotlin enum classes are supported automatically using two strategy extensions: [4]

*
* enumeration(name, Class): Maps the value to a database INT using the enum's .ordinal index.
* enumerationByName(name, length, Class): Maps the value to a database VARCHAR using the enum's .name string representation.
*

## 2. Collections and Arrays (List<T> / Array<T>)
    If you use database dialects that support array types (like PostgreSQL), you can map primitive arrays natively: [5]

    *
    * array<Int>(name) maps directly to IntArray / List<Int>.
        * array<String>(name) maps directly to Array<String> / List<String>. [5]
            *

            ## 3. JSON Configurations
            Using the exposed-json extension, any Kotlin data class compiled with @Serializable (kotlinx.serialization) can be saved and read directly into standard JSON columns: [6]

            *
            * json(name, serializer) maps to a native JSON string column.
            * jsonb(name, serializer) maps to a binary JSONB column (PostgreSQL optimized). [6]
            *

            ------------------------------
            ## A Note on Nullability
            Every data type listed above defaults to a NOT NULL constraint in SQL. To allow a nullable Kotlin type (e.g., String? or Int?), simply append .nullable() to the column definition:

            object UsersTable : Table("users") {
            val id = integer("id").autoIncrement() // Maps to Int
            val middleName = varchar("middle_name", 50).nullable() // Maps to String?
            }

            Are you currently building out a database schema or setting up a configuration for a specific target database (like PostgreSQL, MySQL, or SQLite)? I can provide a functional table definition template using the correct Gradle dependencies.

            [1] [https://www.jetbrains.com](https://www.jetbrains.com/help/exposed/frequently-asked-questions.html)
            [2] [https://www.jetbrains.com](https://www.jetbrains.com/help/exposed/numeric-boolean-string-types.html)
            [3] [https://www.jetbrains.com.cn](https://www.jetbrains.com.cn/en-us/help/exposed/date-and-time-types.html)
            [4] [https://www.youtube.com](https://www.youtube.com/watch?v=YOXWnM_8vz8&t=148)
            [5] [https://www.jetbrains.com](https://www.jetbrains.com/help/exposed/array-types.html)
            [6] [https://www.jetbrains.com](https://www.jetbrains.com/help/exposed/json-and-jsonb-types.html)
