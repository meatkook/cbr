package com.currency.entity

import java.math.BigDecimal
import javax.persistence.*

//@MappedEntity(value = "currency_cbr")
@Entity()
@Table(name = "currency_cbr")
data class CurrencyEntity(

    @Id
    @Column(name = "valute_id")
    val id: String,

    @Column(name = "num_code")
    var numCode:String,

    @Column(name = "char_code")
    var charCode:String,

    @Column(name = "nominal")
    var nominal:Int,

    @Column(name = "name")
    var name:String,

    @Column(name = "value")
    var value: BigDecimal
)
