package dev.andrey.forum.mapper

interface Mapper<T, U> {
    fun map(input: T): U
}
