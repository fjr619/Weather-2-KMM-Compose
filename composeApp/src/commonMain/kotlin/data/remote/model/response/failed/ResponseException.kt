package data.remote.model.response.failed

class ResponseException(override val message: String?) : Exception(message)