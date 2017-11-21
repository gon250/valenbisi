package com.gonzalo.valenbisi.pojo

data class Network(
	val license: License? = null,
	val name: String? = null,
	val company: List<String?>? = null,
	val location: Location? = null,
	val href: String? = null,
	val id: String? = null,
	val stations: List<StationsItem?>? = null
)
