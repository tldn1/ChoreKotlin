package com.example.tldn1.chorekotlin.model

/**
 * Created by tldn1 on 1/7/2018.
 */
class ChoreModel() {

    var id: Int? = null
    var choreName: String? = null
    var choreAssignedBy: String? = null
    var choreAssignedTo: String? = null
    var timeAssigned: Long? = null

    constructor(id: Int, choreName: String, choreAssignedBy: String, choreAssignedTo: String, timeAssigned: Long) : this() {
        this.id = id
        this.choreName = choreName
        this.choreAssignedBy = choreAssignedBy
        this.choreAssignedTo = choreAssignedTo
        this.timeAssigned = timeAssigned
    }


}