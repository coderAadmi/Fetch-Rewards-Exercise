package com.fetch.rewards.exercise.network

sealed class ApiResultFailure {
    object NoInternetConnection : ApiResultFailure()
    object Unknown : ApiResultFailure()
    object Error : ApiResultFailure()
    object Loading : ApiResultFailure()
    object Success : ApiResultFailure()
}