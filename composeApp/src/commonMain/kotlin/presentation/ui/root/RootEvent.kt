package presentation.ui.root

sealed interface RootEvent {
    data object onRequestPermission: RootEvent
    data object onCheckPermission: RootEvent
}