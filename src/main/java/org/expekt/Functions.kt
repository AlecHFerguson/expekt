package org.expekt


fun <T> T?.should(): ExpectAny<T> {
    return ExpectAny(this)
}

fun <T> expect(subject: T?): ExpectAny<T?> {
    return ExpectAny(subject)
}

fun expect(subject: Boolean): ExpectBoolean {
    return ExpectBoolean(subject)
}

fun expect(subject: Int): ExpectInt {
    return ExpectInt(subject)
}

fun expect(subject: String): ExpectString {
    return ExpectString(subject)
}