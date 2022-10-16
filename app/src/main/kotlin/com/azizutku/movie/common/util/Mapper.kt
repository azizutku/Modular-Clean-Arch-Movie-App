package com.azizutku.movie.common.util

interface Mapper<F, T> {
    suspend fun map(from: F): T
}
