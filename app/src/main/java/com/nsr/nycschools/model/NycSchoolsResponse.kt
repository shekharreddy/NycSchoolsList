package com.nsr.nycschools.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
class NycSchoolsResponse : ArrayList<NycSchoolsResponseItem>()