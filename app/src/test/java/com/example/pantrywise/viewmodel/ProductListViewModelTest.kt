package com.example.pantrywise.viewmodel

import app.cash.turbine.test
import com.example.pantrywise.TestDataFactory
import com.example.pantrywise.data.repository.FakeProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.Assert.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ProductListViewModelTest {
    private lateinit var repository: FakeProductRepository
    private lateinit var viewModel: ProductListViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = FakeProductRepository()
        viewModel = ProductListViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testAddProduct() = runTest(testDispatcher) {
        viewModel.products.test {
            awaitItem()
            viewModel.addProduct(TestDataFactory.prepareProduct())
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("Milk", list[0].name)
        }
    }

    @Test
    fun testUpdateProduct() = runTest(testDispatcher) {
        viewModel.products.test {
            awaitItem()
            viewModel.addProduct(TestDataFactory.prepareProduct())
            val list = awaitItem()
            val updated = list[0].copy(quantity = 3.0)
            viewModel.updateProduct(updated)
            val updatedList = awaitItem()
            assertEquals(3.0, updatedList[0].quantity, 0.01)
        }
    }

    @Test
    fun testDeleteProduct() = runTest(testDispatcher) {
        viewModel.products.test {
            awaitItem()
            viewModel.addProduct(TestDataFactory.prepareProduct())
            val list = awaitItem()
            viewModel.deleteProduct(list[0])
            val afterDelete = awaitItem()
            assertEquals(0, afterDelete.size)
        }
    }
} 