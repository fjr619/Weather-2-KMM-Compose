package data.remote.response.failed

class ResponseException(override val message: String?) : Exception(message)