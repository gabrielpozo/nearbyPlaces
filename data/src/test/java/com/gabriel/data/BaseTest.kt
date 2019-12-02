package com.gabriel.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gabriel.data.utils.RxSchedulerRule
import org.junit.Rule
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

/**
 * Created by Gabriel Pozo Guzman on 2019-12-02.
 */

abstract class BaseTest {

    @get:Rule
    val mockitoRule: MockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()
}