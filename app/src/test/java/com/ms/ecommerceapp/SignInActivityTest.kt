package com.ms.ecommerceapp // Should be in your test source set (e.g., app/src/test/java/com/ms/ecommerceapp)

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.ms.ecommerceapp.activity.SignInActivity
import com.ms.ecommerceapp.activity.SignUpActivity
import com.ms.ecommerceapp.dependencyInjection.DatabaseAdapter
import com.ms.ecommerceapp.network.NetworkManager
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.verify
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config

@HiltAndroidTest
//@RunWith(AndroidJUnit4::class)
@RunWith(RobolectricTestRunner::class)
@Config(application = HiltTestApplication::class, sdk = [30])
class SignInActivityTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    lateinit var mockAuth: FirebaseAuth

    @BindValue
    lateinit var mockDatabaseAdapter: DatabaseAdapter

    private lateinit var activityScenario: ActivityScenario<SignInActivity>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        mockDatabaseAdapter = mockk(relaxed = true)
        hiltRule.inject()

        mockkObject(NetworkManager)
        every { NetworkManager.initialize(any()) } just runs
        every { NetworkManager.getConnectionStatus(any()) } returns true
        every { NetworkManager.unRegisterNetworkCallback() } just runs

        FirebaseApp.initializeApp(ApplicationProvider.getApplicationContext())

        activityScenario =
            ActivityScenario.launch(SignInActivity::class.java).onActivity { activity ->
                activity.auth = mockAuth
                activity.databaseAdapter = mockDatabaseAdapter
            }
    }

    @After
    fun tearDown() {
        activityScenario.close()
    }

    @Test
    fun `onCreate initializes NetworkManager and logs with DatabaseAdapter`() {
        activityScenario.onActivity { activity ->
            verify { NetworkManager.initialize(activity) }
            verify { mockDatabaseAdapter.log("Hello Hilt") }
        }
    }

    @Test
    fun `navigateToSignUp when network is available launches SignUpActivity`() {
        every { NetworkManager.getConnectionStatus(any()) } returns true
        activityScenario.onActivity { activity ->
            activity.navigateToSignUp()
            val shadowActivity = shadowOf(activity)
            val startedIntent = shadowActivity.nextStartedActivityForResult?.intent
            Assert.assertNotNull(startedIntent)
            Assert.assertEquals(SignUpActivity::class.java.name, startedIntent?.component?.className)

//            assert(startedIntent != null)
//            assert(startedIntent?.component?.className == SignUpActivity::class.java.name)
        }
    }
}