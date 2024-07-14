package com.example.domain.entity.sourcesResponce


import com.example.domain.entity.sourcesResponce.Source
data class SourcesResponse(

    val sources: List<Source?>? = null,
    val status: String? = null,

    val message: String? = null,
    val code: String? = null
)