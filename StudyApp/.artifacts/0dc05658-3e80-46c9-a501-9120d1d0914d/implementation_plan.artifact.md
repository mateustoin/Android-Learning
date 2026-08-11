# Fix: FATAL EXCEPTION: Unsupported type: ImageVector in UserListScreen

The app crashes when attempting to display user avatars in `UserListScreen.kt`. The crash is caused by passing an `ImageVector` (`Icons.Default.Person`) to Coil's `rememberAsyncImagePainter`. Coil's machinery is designed for loading bitmapped images from external sources and does not support `ImageVector` directly.

To fix this, we'll use `rememberVectorPainter`, which is the standard Jetpack Compose way to convert an `ImageVector` into a `Painter` that can be used by `AsyncImage`.

## Proposed Changes

### User List Feature

#### [MODIFY] [UserListScreen.kt](file:///Users/mateusantonio/repositorios/Android-Learning/StudyApp/app/src/main/java/com/example/studyapp/features/user_list/UserListScreen.kt)

- Replace `coil.compose.rememberAsyncImagePainter` with `androidx.compose.ui.graphics.vector.rememberVectorPainter`.
- Update `AsyncImage` placeholder and error parameters to use `rememberVectorPainter(Icons.Default.Person)`.

## Verification Plan

### Automated Tests
- Run `./gradlew :app:assembleDebug` to ensure the project builds correctly.

### Manual Verification
- Deploy the app and navigate to the "User List" screen.
- Verify that avatars are displayed (or show the default person icon placeholder) without crashing the app.
