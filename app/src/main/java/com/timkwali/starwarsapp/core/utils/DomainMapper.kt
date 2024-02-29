package com.timkwali.starwarsapp.core.utils

interface DomainMapper<DomainModel, Dto> {

    suspend fun mapToDomain(entity: DomainModel): Dto
}