package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.Position

@Entity(tableName = "tbl_positions")
data class PositionEntity(
    val name: String,
    val userId: Int,
    @PrimaryKey
    val positionId: Int = 0
) {
    fun toPosition(): Position {
        return Position(
            name = name
        )
    }
}