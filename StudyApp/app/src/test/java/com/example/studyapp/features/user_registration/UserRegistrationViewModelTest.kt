package com.example.studyapp.features.user_registration

import android.util.Log
import com.example.studyapp.domain.usecase.AddUserUseCase
import com.example.studyapp.util.MainDispatcherRule
import com.google.common.truth.Truth.assertThat
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserRegistrationViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val addUserUseCase = mockk<AddUserUseCase>(relaxed = true)
    private lateinit var viewModel: UserRegistrationViewModel

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.i(any(), any()) } returns 0
        every { Log.e(any(), any()) } returns 0
        viewModel = UserRegistrationViewModel(addUserUseCase)
    }

    @After
    fun tearDown() {
        unmockkStatic(Log::class)
    }

    @Test
    fun `addUser nao deve chamar o use case se o nome for vazio`() = runTest {
        viewModel.addUser("")

        coVerify(exactly = 0) { addUserUseCase(any()) }
    }

    @Test
    fun `addUser deve enviar evento Success quando o use case executa com sucesso`() = runTest {
        val nome = "Mateus"

        viewModel.addUser(nome)

        val event = viewModel.eventFlow.first()
        assertThat(event).isInstanceOf(UserRegistrationUiEvent.Success::class.java)
        assertThat((event as UserRegistrationUiEvent.Success).message).contains(nome)
        coVerify { addUserUseCase(any()) }
    }
}
