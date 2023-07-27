package com.currency.service.impl

import com.currency.dto.CurrencyDto
import com.currency.entity.CurrencyEntity
import com.currency.repository.ICurrencyRepository
import com.currency.service.ICurrencyService
import jakarta.inject.Singleton
import org.w3c.dom.Element
import org.w3c.dom.Node
import java.io.ByteArrayInputStream
import java.net.URL
import java.nio.charset.Charset
import javax.xml.parsers.DocumentBuilderFactory

@Singleton
class CurrencyServiceImpl(private val ICurrencyRepository: ICurrencyRepository):ICurrencyService{

    override fun getAll(): List<CurrencyDto> {

        return ICurrencyRepository.findAll().map { it.toDto() }

    }

    override fun getOneById(id: String): CurrencyDto {
        return ICurrencyRepository.findById(id).get().toDto()
    }

    override fun create(dto: CurrencyDto): String {
        return ICurrencyRepository.save(dto.toEntity()).id
    }

    override fun update(id: String, dto: CurrencyDto) {
        val currentCurrency = ICurrencyRepository.findById(id).get()

        currentCurrency.updateDataFrom(dto)
        ICurrencyRepository.update(currentCurrency)
    }

    override fun updateFromNet(web:String) {
//        val xml = URL("https://cbr.ru/scripts/XML_daily.asp").readText(Charset.forName("Windows-1251"))
        val currencyList = web.toCurrencyEntityList()
        for(currency in currencyList){
            if(ICurrencyRepository.existsById(currency.id)){
                ICurrencyRepository.update(currency)
            }
            else{
                ICurrencyRepository.save(currency)
            }
        }
    }

    override fun delete(id: String) {
        val currentCurrency = ICurrencyRepository.findById(id).get()
        ICurrencyRepository.delete(currentCurrency)
    }

    private fun CurrencyEntity.toDto():CurrencyDto = CurrencyDto(
        id = this.id,
        numCode = this.numCode,
        charCode = this.charCode,
        nominal = this.nominal,
        name = this.name,
        value = this.value
    )

    private fun CurrencyEntity.updateDataFrom(dto: CurrencyDto){
        this.numCode = dto.numCode
        this.charCode = dto.charCode
        this.nominal = dto.nominal
        this.name = dto.name
        this.name = dto.name
        this.value = dto.value
    }

    private fun CurrencyDto.toEntity(): CurrencyEntity {
        return CurrencyEntity(
            id = this.id,
            numCode = this.numCode,
            charCode = this.charCode,
            nominal = this.nominal,
            name = this.name,
            value = this.value
        )
    }

    private fun String.toCurrencyEntityList():List<CurrencyEntity>{
        val factory = DocumentBuilderFactory.newInstance().newDocumentBuilder()
        val document = factory.parse(ByteArrayInputStream(this.toByteArray(Charset.forName("Windows-1251"))))
        document.documentElement.normalize()

        val currencyList = mutableListOf<CurrencyEntity>()
        val nodeList = document.getElementsByTagName("Valute")

        for (i in 0 until nodeList.length) {
            val node = nodeList.item(i)

            if (node.nodeType == Node.ELEMENT_NODE) {
                val element = node as Element

                val id = element.getAttribute("ID")
                val numCode = element.getElementsByTagName("NumCode").item(0).textContent
                val charCode = element.getElementsByTagName("CharCode").item(0).textContent
                val nominal = element.getElementsByTagName("Nominal").item(0).textContent.toInt()
                val name = element.getElementsByTagName("Name").item(0).textContent
                val value = element.getElementsByTagName("Value").item(0).textContent.replace(',', '.').toBigDecimal()

                currencyList.add(CurrencyEntity(id, numCode, charCode, nominal, name, value))
            }
        }

        return currencyList
        /**
         * Variant of xml parsing using regular expressions
         */
        //        val pattern = ("<Valute ID=\"(.*?)\">[^<]*" +
//                "<NumCode>(.*?)</NumCode>[^<]*" +
//                "<CharCode>(.*?)</CharCode>[^<]*" +
//                "<Nominal>(.*?)</Nominal>[^<]*" +
//                "<Name>(.*?)</Name>[^<]*" +
//                "<Value>(.*?)</Value>[^<]*").toRegex()
//
//        val currencyList = mutableListOf<CurrencyEntity>()
//
//        pattern.findAll(xml).forEach {
//            val (id, numCode, charCode, nominal, name, value) = it.destructured
//            currencyList.add(CurrencyEntity(
//                id,
//                numCode,
//                charCode,
//                nominal.toInt(),
//                name,
//                value.replace(",", ".").toBigDecimal()))
//        }
//
//        return currencyList
    }
}