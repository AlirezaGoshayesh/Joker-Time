package com.test.jokertime.domain.exceptions

import com.test.jokertime.domain.exceptions.ErrorModel

interface IErrorHandler {
    fun handleException(throwable: Throwable?): ErrorModel
}