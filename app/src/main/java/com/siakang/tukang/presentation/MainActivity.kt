package com.siakang.tukang.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.siakang.tukang.presentation.screen.chat.ChatScreen
import com.siakang.tukang.presentation.screen.dashboard.DashboardScreen
import com.siakang.tukang.presentation.screen.data_completion.bank.BankInformationScreen
import com.siakang.tukang.presentation.screen.data_completion.file_upload.FileUploadScreen
import com.siakang.tukang.presentation.screen.data_completion.personal_information.PersonalInformationScreen
import com.siakang.tukang.presentation.screen.data_completion.skill.SkillScreen
import com.siakang.tukang.presentation.screen.login.LoginScreen
import com.siakang.tukang.presentation.screen.order_detail.OfferDetailScreen
import com.siakang.tukang.presentation.screen.pending_verification.PendingVerificationScreen
import com.siakang.tukang.presentation.screen.register.RegisterScreen
import com.siakang.tukang.presentation.screen.register_success.SuccessRegisterScreen
import com.siakang.tukang.presentation.screen.registration_detail.RegistrationDetailScreen
import com.siakang.tukang.presentation.theme.SiAkangTukangTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.FlowPreview

@ExperimentalPagerApi
@FlowPreview
@ExperimentalPermissionsApi
@ExperimentalMaterial3Api
@ExperimentalMaterialNavigationApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }
        setContent {
            SiAkangTukangTheme {
                val navController = rememberAnimatedNavController()
                AnimatedNavHost(navController = navController, startDestination = viewModel.destination.collectAsState(initial = "login").value) {
                    composable(
                        route = "login",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        LoginScreen(navController = navController)
                    }
                    composable(
                        route = "register",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        RegisterScreen(navController = navController)
                    }
                    composable(
                        route = "register_success",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        SuccessRegisterScreen(navController = navController)
                    }
                    composable(
                        route = "dashboard",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        DashboardScreen(navController)
                    }
                    composable(
                        route = "personal_information",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        PersonalInformationScreen(navController = navController)
                    }
                    composable(
                        route = "file_upload",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        FileUploadScreen(navController = navController)
                    }
                    composable(
                        route = "skill_list",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        SkillScreen(navController = navController)
                    }
                    composable(
                        route = "bank_info",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        BankInformationScreen(navController = navController)
                    }
                    composable(
                        route = "pending_verification",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        PendingVerificationScreen(navController = navController)
                    }
                    composable(
                        route = "registration_detail",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) {
                        RegistrationDetailScreen(navController = navController)
                    }
                    composable(
                        route = "offer_detail/{orderId}",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) { backStackEntry ->
                        OfferDetailScreen(navController, backStackEntry.arguments?.getString("orderId"))
                    }
                    composable(
                        route = "chat_detail/{friendId}",
                        enterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        exitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Left, animationSpec = tween(300))
                        },
                        popEnterTransition = {
                            slideIntoContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        },
                        popExitTransition = {
                            slideOutOfContainer(AnimatedContentScope.SlideDirection.Right, animationSpec = tween(300))
                        }
                    ) { backStackEntry ->
                        ChatScreen(navController, backStackEntry.arguments?.getString("friendId"))
                    }
                }
            }
        }
    }
}