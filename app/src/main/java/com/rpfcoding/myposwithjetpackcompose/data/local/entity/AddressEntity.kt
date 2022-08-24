package com.rpfcoding.myposwithjetpackcompose.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rpfcoding.myposwithjetpackcompose.domain.model.Address

@Entity(tableName = "tbl_addresses")
data class AddressEntity(
    val userId: Int,
    val country: String,
    val region: String,
    val province: String,
    val city: String,
    val street: String,
    val contactNo: String,
    val isDefault: Boolean,
    @PrimaryKey
    val addressId: Int = 0
) {
    fun toAddress(): Address {
        return Address(
            country = country,
            region = region,
            province = province,
            city = city,
            street = street,
            contactNo = contactNo,
            isDefault = isDefault
        )
    }
}
