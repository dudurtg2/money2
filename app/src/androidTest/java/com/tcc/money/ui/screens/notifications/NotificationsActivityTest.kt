package com.tcc.money.ui.screens.notification

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.tcc.money.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class NotificationsActivityTest {

    @Test
    fun elementosDevemEstarVisiveisNaTelaDeNotificacoes() {
        ActivityScenario.launch(NotificationsActivity::class.java)

        // Verifica se o botão de voltar está visível
        onView(withId(R.id.btnBack)).check(matches(isDisplayed()))

        // Verifica se o título está visível e com o texto correto
        onView(withId(R.id.txtTitulo))
            .check(matches(isDisplayed()))
            .check(matches(withText("Notificações")))

        // Verifica se a mensagem de "Nenhuma atualização" está visível
        onView(withId(R.id.txtNenhumaAtualizacao))
            .check(matches(isDisplayed()))
            .check(matches(withText("Nenhuma \n atualização :(".replace("\n", "\n"))))
    }

    @Test
    fun clicarNoBotaoVoltarFechaActivity() {
        val scenario = ActivityScenario.launch(NotificationsActivity::class.java)

        // Clica no botão de voltar
        onView(withId(R.id.btnBack)).perform(androidx.test.espresso.action.ViewActions.click())

        // Verifica se a Activity foi encerrada
        scenario.onActivity { activity ->
            assert(activity.isFinishing || activity.isDestroyed)
        }
    }
}
