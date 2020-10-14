package com.gitlab.nitgc.kokasai.flowerkt.css

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import kotlinx.css.*

suspend inline fun ApplicationCall.respondCss(builder: RuleSet) {
    respondText(CSSBuilder().apply(builder).toString(), ContentType.Text.CSS)
}