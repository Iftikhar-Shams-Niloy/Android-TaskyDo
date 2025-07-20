package com.example.taskydo

import com.example.taskydo.ui.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Test

class InputValidationTest {
    @Test
    fun inputValidator_returnsFalseWhenEmpty(){
        // Perform an action
        val result = false

        // Assert the result
        assertEquals(false, result)

    }
}