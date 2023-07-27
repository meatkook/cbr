package com.currency.controller


import com.currency.dto.CurrencyDto
import com.currency.service.ICurrencyService
import io.micronaut.http.annotation.*
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client

@Controller("/api/currencies")
class CurrencyController (private val ICurrencyService: ICurrencyService, @Client("https://www.cbr.ru") private val client: HttpClient) {

    @Get
    fun getAll(): List<CurrencyDto>{

        return ICurrencyService.getAll()
    }

    @Put("/download")
    fun updateDbFromApiCbr(){
        ICurrencyService.updateFromNet(
            client.toBlocking().retrieve("/scripts/XML_daily.asp").toString()
        )
    }

    @Post
    fun create(dto:CurrencyDto): String{
        return ICurrencyService.create(dto)
    }

    @Put("/{id}")
    fun update(@PathVariable("id") id: String, @Body dto: CurrencyDto) {
        ICurrencyService.update(id, dto)
    }

    @Get("/{id}")
    fun getOneByID(@PathVariable("id") id: String): CurrencyDto {
        return ICurrencyService.getOneById(id)
    }

    @Delete("/{id}")
    fun delete(@PathVariable("id") id: String){
        ICurrencyService.delete(id)
    }

}