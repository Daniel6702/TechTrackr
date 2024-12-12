// AppScaffold.kt
package com.example.techtrackr

import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.rememberCoroutineScope
import com.example.techtrackr.ui.navigation.CommonNavigationLayout
import com.example.techtrackr.ui.navigation.DrawerContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    showDrawer: Boolean,
    onProfileSelected: () -> Unit,
    onNavigateHome: () -> Unit,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp

    if (showDrawer) {
        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                Surface(modifier = androidx.compose.ui.Modifier.width(screenWidth * 0.8f)) {
                    DrawerContent(
                        onHomeClick = {
                            scope.launch {
                                drawerState.close()
                            }
                            onNavigateHome()
                        }
                    )
                }
            }
        ) {
            CommonNavigationLayout(
                onNavigateToProfile = onProfileSelected,
                title = "Home",
                openDrawer = {
                    scope.launch { drawerState.open() }
                },
                content = { contentPadding ->
                    content()
                }
            )
        }
    } else {
        // When drawer should not be shown, just show the content without the CommonNavigationLayout or a top bar
        // If you want no top bar on these screens, wrap content directly in a Surface:
        Surface {
            content()
        }
    }
}
