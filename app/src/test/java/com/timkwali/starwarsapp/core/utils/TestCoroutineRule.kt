package com.timkwali.starwarsapp.core.utils

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class TestCoroutineRule : TestRule, TestCoroutineScope by TestCoroutineScope() {
    override fun apply(base: org.junit.runners.model.Statement, description: org.junit.runner.Description): org.junit.runners.model.Statement {
        return object : org.junit.runners.model.Statement() {
            override fun evaluate() {
                base.evaluate()
                cleanupTestCoroutines()
            }
        }
    }
}