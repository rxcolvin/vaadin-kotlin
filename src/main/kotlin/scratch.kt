typealias Name=String

interface String_Type {
    val minLength: Int
    val maxLength: Int
    val format: String
    val formatRegex: String
}

object Name_Type: String_Type {
    override val minLength: Int = 2
    override val maxLength: Int = 30
    override val format: String = "All characters except CR and tab"
    override val formatRegex: String = ""
}

