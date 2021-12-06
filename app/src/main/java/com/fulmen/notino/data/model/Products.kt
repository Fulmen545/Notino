package com.fulmen.notino.data.model

data class Products (
    val productId: Int,
    val brand: BrandData,
    val attributes: AttributeData,
    val annotation: String,
    val masterId: Int,
    val url: String,
    val orderUnit: String,
    val price: PriceData,
    val imageUrl: String,
    val name: String,
    val productCode: String,
    val reviewSummary: SummaryData,
    val stockAvailability: AvailabilityData
)
