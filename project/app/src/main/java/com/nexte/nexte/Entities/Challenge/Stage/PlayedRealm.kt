package com.nexte.nexte.Entities.Challenge.Stage

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class PlayedRealm(@PrimaryKey var id: String = ""): RealmObject()