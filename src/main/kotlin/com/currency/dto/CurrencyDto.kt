package com.currency.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import java.math.BigDecimal


@JsonSerialize
@JsonDeserialize
data class CurrencyDto(

    @JsonProperty("ID")
    val id:String,

    @JsonProperty("NumCode")
    val numCode:String,

    @JsonProperty("CharCode")
    val charCode:String,

    @JsonProperty("Nominal")
    val nominal:Int,

    @JsonProperty("Name")
    val name:String,

    @JsonProperty("Value")
    val value: BigDecimal
)