package com.winterbe.expekt

import java.math.BigDecimal
import java.math.BigInteger
import java.util.*

private fun <T: Any> generateScalarComparison(propertyName: String, subject: T?, expected: T?): List<String> {
    if (subject == expected) {
        return listOf("  $propertyName = $subject")
    }
    return listOf(
            "+ $propertyName = $subject",
            "- $propertyName = $subject"
    )
}

private fun <T: Any> generateObjectComparison(subject: T?, expected: T?): List<String> {
    val propertyResults = mutableListOf<String>()
    if (subject != null && expected != null) {
        val subjectFields = subject.javaClass.declaredFields
        subjectFields.forEach { property ->
            propertyResults.addAll(getDiffArray(property.name, subject, expected))
            null
        }
    }
    return propertyResults
}

fun <T: Any> getDiffArray(propertyName: String, subject: T?, expected: T?): List<String> {
    return when {
        subject.isScalar() -> generateScalarComparison("value", subject, expected)
        // TODO: Iterable
        subject is Any -> generateObjectComparison(subject, expected)
        else -> emptyList()
    }
}

/**
 * Determines if this object should not be broken down further for pretty printing.
 */
private fun Any?.isScalar(): Boolean =
        this == null ||
                this is Char ||
                this is Number ||
                this is Boolean ||
                this is BigInteger ||
                this is BigDecimal ||
                this is UUID

fun <T: Any> prettyPrintDiff(subject: T?, expected: T?): String {
    return getDiffArray(subject, expected).joinToString("\n")
}
