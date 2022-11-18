## Intro

This is a picture share platform made by **Jetpack Compose**. You can record and share your daily life or beauty pictures here. It's a record corner of our life.

BTW, you can deploy your own picbed in your server, just look at [PicBedCore](https://github.com/CoderWDD/PicBedCore). Once you deploy successfully, set your server url and token in PicSingularApp. Then you can save your images to your server by one click.

## Key technology stack used

- Jetpack Compose
- MVI
- ViewModel
- DataStore
- Hilt
- Retrofit
- Paging3
- Coil

## MVI Pattern

### What is MVI?

*MVI* stands for *Model-View-Intent*. MVI is one of the newest architecture patterns for Android, inspired by the unidirectional and cyclical nature of the *Cycle.js* framework.

MVI works in a very different way compared to its distant relatives, MVC, MVP or MVVM. The role of each MVI components is as follows:

- *Model* represents a state. Models in MVI should be immutable to ensure a unidirectional data flow between them and the other layers in your architecture.
- Like in MVP, Interfaces in MVI represent *Views*, which are then implemented in one or more Activities or Fragments.
- *Intent* represents an intention or a desire to perform an action, either by the user or the app itself. For every action, a View receives an Intent. The Presenter observes the Intent, and Models translate it into a new state.

### How MVI was used in this project?

- **Intent**: The intent of action, just define the action as intent, like this:

  ```kotlin
  sealed class LoginViewAction{
      class Login(val username: String,val password: String) : LoginViewAction()
      class SaveLoginUser(val username: String,val password: String) : LoginViewAction()
      object Logout : LoginViewAction()
      class UploadAvatar(val avatarPath: String) : LoginViewAction()
      object GetUserInfo : LoginViewAction()
      class NavBack(val navHostController: NavHostController) : LoginViewAction()
      object InitData : LoginViewAction()
      class UpdateUserInfo(val username: String, val password: String, val signature: String): LoginViewAction()
  }
  ```

- **Model**: The `state set` of the page, the state of page should be define here, we can use `data class` in `kotlin` to do this easily.

  ```kotlin
  data class LoginViewState(
      val isSuccess: Boolean = false,
      val isError: Boolean = false,
      val isLogin: Boolean = false,
      val navBack: Boolean = false,
      val errorMessage: String? = null,
      val user: User? = null
  )
  ```

- **View**: The view that produce `action intent`, and pass it into the function `actionHandler` in `viewmodel`, and `viewmodel` will deal the `intent`, and update the `state of page`, which would cause the view update where ref the state automatically.

  ```kotlin
  @Composable
  fun LoginPage(
      navHostController: NavHostController,
      viewModel: LoginViewModel = hiltViewModel()
  ) {
      var username by remember { mutableStateOf("") }
      var password by remember { mutableStateOf("") }
      var showPassword by remember { mutableStateOf(false) }
      val keyboardController = LocalSoftwareKeyboardController.current
      ....
      val loginState = viewModel.viewState
      // 如果登录成功，就返回
      if (loginState.isLogin) {
          viewModel.intentHandler(LoginViewAction.NavBack(navHostController = navHostController))
      }
      
      ....
      Button(onClick = {
          viewModel.intentHandler(LoginViewAction.Login(username = username, password = password))
      })
  }
  ```

- **ViewModel**: This is the layer that do logical processing. The update of `state of page` and handler the `action intent` happens here.

  ```kotlin
  @HiltViewModel
  class LoginViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {
      var viewState by mutableStateOf(LoginViewState())
          private set
      private val _viewEvent = Channel<LoginEvent> (capacity = Channel.BUFFERED)
      val viewEvent = _viewEvent.receiveAsFlow()
      fun intentHandler(action: LoginViewAction){
          when (action){
              is LoginViewAction.Login -> login(username = action.username, password = action.password)
              is LoginViewAction.SaveLoginUser -> saveLoginUser(username = action.username, password = action.password)
              is LoginViewAction.Logout -> logout()
              is LoginViewAction.UploadAvatar -> uploadAvatar(avatarPath = action.avatarPath)
              is LoginViewAction.GetUserInfo -> getUserInfo()
              is LoginViewAction.NavBack -> navBack(action.navHostController)
              is LoginViewAction.InitData -> initData()
              is LoginViewAction.UpdateUserInfo -> updateUserInfo(action.username, action.password, action.signature)
          }
      }
      private fun navBack(navHostController: NavHostController){ ... }
  
      private fun uploadAvatar(avatarPath: String){
          viewModelScope.launch {
              ...
              viewState.copy(isError = true, errorMessage = res.message)
              ...
          }
      }
      private fun logout(){ ... }
  
      private fun login(username: String, password: String){ ... }
  
      private fun getUserInfo(){ ... }
  
      private fun saveLoginUser(username: String,password: String){ ... }
  
      private fun updateUserInfo(username: String, password: String, signature: String){ ... }
  }
  ```

## APP Preview

![image-20221118211031352](http://picbed.coderwdd.top/picbed/image-20221118211031352.png)

![image-20221118210953734](http://picbed.coderwdd.top/picbed/image-20221118210953734.png)

![image-20221118211113949](http://picbed.coderwdd.top/picbed/image-20221118211113949.png)

![image-20221118211130576](http://picbed.coderwdd.top/picbed/image-20221118211130576.png)
