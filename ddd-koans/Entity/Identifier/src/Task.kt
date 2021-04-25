import java.util.*

// Simplified examples

interface Identifiable<T> {
    val id: T
}

data class Book(private val isbn: ISBN, private val pages: List<Page>) : Identifiable<Book.ISBN> {
    override val id: ISBN
        get() = isbn

    data class ISBN(private val value: String)
}

data class Page(private val pageNumber: PageNumber) : Identifiable<Page.PageNumber> {
    override val id: PageNumber
        get() = pageNumber

    data class PageNumber(private val value: Int)
}

data class Task(private val taskId: UUID) : Identifiable<UUID> {
    override val id: UUID
        get() = taskId
}

data class Person(
    private val nationalInsuranceNumber: String,
    private val name: String,
    private val surname: String
) : Identifiable<String> {
    override val id: String
        get() = nationalInsuranceNumber
}

fun main() {
    println("Nothing to do here!")
}
