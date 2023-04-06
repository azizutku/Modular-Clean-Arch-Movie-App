package com.azizutku.movie.core.common.util

interface Mapper<F, T> {
    suspend fun map(from: F): T
}
