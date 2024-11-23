package br.com.ahi.aula01.core

class MessageSerializer (
    val code: Int,
    val messages: List<String>
)

data class CollectionResponseSerializer<T>(
    var data: ArrayList<T>? = ArrayList<T>(),
    var meta: MetaSerializer = MetaSerializer()
)

data class MetaSerializer(
    var pagination: MetaPaginationSeriualizer = MetaPaginationSeriualizer()
)

data class MetaPaginationSeriualizer(
    var currentPage: Int? = null,
    var limit: Int =50,
    var pages: Int = 0,
    var total: Int = 0,
)