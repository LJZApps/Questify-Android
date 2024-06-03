package de.ljz.questify.data.api.core.exceptions

import java.io.IOException

class RequestFailedException(val errorCode: String?, val errorMessage: String?) : IOException()