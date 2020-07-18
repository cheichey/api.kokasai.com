package com.gitlab.nitgc.kokasai.the23rd.routes.template

import io.ktor.html.Placeholder
import io.ktor.html.Template
import io.ktor.html.insert
import kotlinx.css.*
import kotlinx.css.properties.*
import kotlinx.html.*

object HeaderFooterTemplate: Template<HTML> {
    val body = Placeholder<BODY>()
    val THEME_COLOR = Color.green.withAlpha(0.6).blend(Color.white)

    override fun HTML.apply() {
        head {
            link(rel = "stylesheet", href = "/header.css", type = "text/css")
        }
        body {
            header {
                h1 {
                    a(href = "/") {
                        +"工華祭"
                    }
                }
                ul {
                    li {
                        a(href = "#") {
                            +"Page1"
                        }
                    }
                    li {
                        a(href = "#") {
                            +"Page2"
                        }
                    }
                }
            }
            insert(body)
            footer {
                span {
                    //+"Footer"
                }
            }
        }
    }

    val headerCss: CSSBuilder.() -> Unit = {
        "body" {
            margin(0.px)
        }
        "li" {
            listStyleType = ListStyleType.none
        }
        "header" {
            display = Display.flex
            backgroundColor = THEME_COLOR
            padding(0.5.em, 0.px)
            height = 3.em
            boxShadow(Color.black.withAlpha(0.4), offsetY = 0.1.em, blurRadius = 0.2.em)
        }
        "header h1" {
            fontSize = 2.em
            width = 4.em
            margin(LinearDimension.auto, 1.em)
            paddingTop = 0.1.em
        }
        "header h1 a" {
            color = Color.white
            textAlign = TextAlign.left
            textDecoration = TextDecoration.none
        }
        "header ul" {
            textAlign = TextAlign.right
            width = 80.pct
            margin(LinearDimension.auto)
            padding(0.px)
        }
        "header li" {
            fontSize = 1.4.em
            padding(0.px, 2.em)
            display = Display.inline
        }
        "header li+ li" {
            borderLeft(2.px, BorderStyle.solid, Color.white)
        }
        "header li a" {
            fontWeight = FontWeight.w400
            color = Color.white
            textAlign = TextAlign.center
            textDecoration = TextDecoration.none
            position = Position.relative
            display = Display.inlineBlock
        }
        "header li a::after" {
            position = Position.absolute
            bottom = (-2).px
            left = 0.px
            content = "".quoted
            width = 100.pct
            height = 2.px
            backgroundColor = Color.white
            transform {
                scale(0, 1)
            }
            transition("transform", .2.s)
        }
        "header li a:hover::after" {
            transform {
                scale(1, 1)
            }
        }
    }
}