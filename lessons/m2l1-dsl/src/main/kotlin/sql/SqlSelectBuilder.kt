package ru.otus.otuskotlin.m1l5.sql

class SqlSelectBuilder {
    var columns: String = "*"
    var whereExp: String? = null
    var groupExp: String? = null
    var table: String? = null

    fun build(): String {
        preBuildChecking()

        val whereBlock = whereExp?.let { " where $it" } ?: ""
        val groupBlock = groupExp?.let { " group by $it" } ?: ""

        return "select $columns from $table$whereBlock$groupBlock"
    }

    private fun preBuildChecking() {
        checkNotNull(table) { "Table name must be specified before building the query." }
    }

    fun from(table: String) = apply {
        this.table = table
    }

    fun groupBy(groupBlock: String) = apply {
        this.groupExp = groupBlock
    }

    fun from(block: SqlSelectBuilder.() -> String) = apply {
        this.table = "(${this.block()}) as subquery"
    }

    fun select(vararg cols: String) = apply {
        columns = cols.joinToString(", ")
    }

    fun where(whereSql: String) = apply {
        whereExp = whereSql
    }

    fun where(block: SqlSelectBuilder.() -> Unit) = also { sql ->
        sql.also(block)
    }

    fun or(vararg exps: String) = "(${exps.joinToString(" or ")})"

    infix fun String.eq(value: String) = "$this = '$value'"
    infix fun String.eq(value: Number?) = if (value == null) "$this is null" else "$this = $value"
    infix fun String.nonEq(value: String) = "$this != '$value'"
    infix fun String.nonEq(value: Number?) = if (value == null) "$this is not null" else "$this != $value"

    fun subquery(sql: SqlSelectBuilder = SqlSelectBuilder(), block: SqlSelectBuilder.() -> Unit): String {
        return sql.apply(block).build()
    }
}


fun query(sql: SqlSelectBuilder = SqlSelectBuilder(), block: SqlSelectBuilder.() -> Unit): SqlSelectBuilder = sql.apply(block)