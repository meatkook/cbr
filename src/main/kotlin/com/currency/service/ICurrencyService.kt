package com.currency.service

import com.currency.dto.CurrencyDto

interface ICurrencyService {
    fun getAll(): List<CurrencyDto>
    fun getOneById(id:String): CurrencyDto
    fun create(dto: CurrencyDto): String
    fun update(id: String, dto: CurrencyDto)
    fun updateFromNet(web:String)
    fun delete(id: String)
}