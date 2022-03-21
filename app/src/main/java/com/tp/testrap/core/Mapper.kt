package com.tp.testrap.core

interface Mapper<I, O> {
    suspend fun map(input: I): O
}