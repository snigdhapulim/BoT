package com.example.bot

data class AlertResponse(
    val links: Links,
    val data: List<Data>
) {
    data class Links(
        val self: String,
        val prev: String,
        val next: String,
        val last: String,
        val first: String
    )

    data class Data(
        val type: String,
        val relationships: Relationships,
        val links: Links,
        val id: String,
        val attributes: Attributes
    ) {
        data class Relationships(
            val facility: Facility
        ) {
            data class Facility(
                val links: Links,
                val data: FacilityData
            ) {
                data class Links(
                    val self: String,
                    val related: String
                )

                data class FacilityData(
                    val type: String,
                    val id: String
                )
            }
        }

        data class Attributes(
            val url: String,
            val updated_at: String,
            val timeframe: String,
            val short_header: String,
            val severity: Int,
            val service_effect: String,
            val lifecycle: String,
            val informed_entity: List<InformedEntity>,
            val header: String,
            val effect_name: String,
            val effect: String,
            val description: String,
            val created_at: String,
            val cause: String,
            val banner: String,
            val active_period: List<ActivePeriod>
        ) {
            data class InformedEntity(
                val trip: String,
                val stop: String,
                val route_type: Int,
                val route: String,
                val facility: String,
                val direction_id: Int,
                val activities: List<String>
            )

            data class ActivePeriod(
                val start: String,
                val end: String
            )
        }
    }
}
