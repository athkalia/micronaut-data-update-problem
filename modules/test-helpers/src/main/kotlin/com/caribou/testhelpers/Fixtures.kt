package com.caribou.testhelpers

import com.appmattus.kotlinfixture.decorator.fake.javafaker.javaFakerStrategy
import com.appmattus.kotlinfixture.kotlinFixture

fun getTestFixturesWithJavaFaker() = kotlinFixture {
    javaFakerStrategy()
}
