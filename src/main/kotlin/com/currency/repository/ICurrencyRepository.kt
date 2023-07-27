package com.currency.repository

import com.currency.entity.CurrencyEntity
import io.micronaut.data.annotation.Repository
import io.micronaut.data.repository.CrudRepository

@Repository
interface ICurrencyRepository: CrudRepository<CurrencyEntity, String> {
}