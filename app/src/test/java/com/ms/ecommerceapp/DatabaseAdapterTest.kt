package com.ms.ecommerceapp

import android.content.Context
import com.ms.ecommerceapp.dependencyInjection.DatabaseAdapter
import com.ms.ecommerceapp.dependencyInjection.DatabaseService
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.ByteArrayOutputStream
import java.io.PrintStream

@RunWith(MockitoJUnitRunner::class)
class DatabaseAdapterTest {

    @Mock
    lateinit var mockContext: Context

    @Mock
    lateinit var mockService: DatabaseService

    @Test
    fun testDatabaseAdapter() {
        val adapter = DatabaseAdapter(mockContext,mockService)

        val bo = ByteArrayOutputStream()
        System.setOut(PrintStream(bo))

        adapter.log("unit testing")

        bo.flush()
        val lines = String(bo.toByteArray())

        assert(lines.contains("Database"))
        assert(lines.contains("Context available"))
        assert(lines.contains("unit testing"))
    }

}