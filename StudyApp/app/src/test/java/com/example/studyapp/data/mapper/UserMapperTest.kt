package com.example.studyapp.data.mapper

import com.example.studyapp.data.local.room.entity.UserEntity
import com.example.studyapp.data.remote.model.UserApiModel
import com.example.studyapp.domain.model.User
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class UserMapperTest {
    @Test
    fun `toUser must convert Entity to Domain keeping the fields integrity`() {
        val entity = UserEntity(
            id = 10,
            name = "Mateus Antonio",
            email = "mateustoin@gmail.com",
            created_at = "2020-03-23"
        )

        val domainFromEntity = entity.toUser()

        assertThat(domainFromEntity.id).isEqualTo(entity.id)
        assertThat(domainFromEntity.name).isEqualTo(entity.name)
        assertThat(domainFromEntity.email).isEqualTo(entity.email)
    }

    @Test
    fun `toApiModel must convert User to Api Model keeping the fields integrity`() {
        val user = User(
            id = 10,
            name = "Mateus Antonio",
            email = "mateustoin@gmail.com",
            created_at = "2020-03-23"
        )

        val apiModelFromDomain = user.toApiModel()

        assertThat(apiModelFromDomain.id).isEqualTo(user.id)
        assertThat(apiModelFromDomain.name).isEqualTo(user.name)
        assertThat(apiModelFromDomain.email).isEqualTo(user.email)
    }

    @Test
    fun `toEntity must convert Api Model to Entity keeping the fields integrity`() {
        val apiModel = UserApiModel(
            id = 10,
            name = "Mateus Antonio",
            email = "mateustoin@gmail.com",
            created_at = "2020-03-23"
        )

        val entityFromApiModel = apiModel.toEntity()

        assertThat(entityFromApiModel.id).isEqualTo(apiModel.id)
        assertThat(entityFromApiModel.name).isEqualTo(apiModel.name)
        assertThat(entityFromApiModel.email).isEqualTo(apiModel.email)
    }
}