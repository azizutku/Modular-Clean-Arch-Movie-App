package com.azizutku.movie.util

interface Mapper<F, T> {
    suspend fun map(from: F): T
}
