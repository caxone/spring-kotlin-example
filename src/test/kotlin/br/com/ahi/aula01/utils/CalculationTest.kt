package br.com.ahi.aula01.utils

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CalculationTest : DescribeSpec({

    val subject = Calculation()

    describe("#sum") {
        describe("when the params are invalid") {
            it("throws an exception") {
                shouldThrow<IllegalArgumentException> {
                    subject.sum(-10, 1)
                }
            }
        }

        describe("when the params are valid") {
            it("return the correct values") {
                subject.sum(1,1).shouldBe(2)
            }
        }
    }

})
