# MVP Магазина Приложений на Android

Это MVP (Minimum Viable Product) магазина приложений для Android, разработанный на языке Kotlin с использованием архитектуры MVVM (Model-View-ViewModel).

## Описание проекта

Проект реализует базовый функционал магазина приложений, включая:
- Список доступных приложений с возможностью поиска и фильтрации
- Детальную карточку приложения с описанием, скриншотами и рейтингом
- Установку приложений через скачивание APK и использование PackageInstaller
- Удаление установленных приложений

## Архитектура

Приложение построено по архитектуре MVVM:
- **Model**: Данные приложения (App.kt), репозиторий для получения данных (AppRepository.kt)
- **View**: Фрагменты (AppListFragment.kt, AppDetailFragment.kt) и адаптеры (AppAdapter.kt)
- **ViewModel**: Логика бизнес-процессов (AppListViewModel.kt, AppDetailViewModel.kt)

## Технологии и зависимости

- **Язык**: Kotlin
- **Архитектура**: MVVM с использованием ViewModel и LiveData
- **UI**: ViewBinding, RecyclerView, Material Design 3
- **Навигация**: Navigation Component
- **Сетевые запросы**: Retrofit (для будущих API вызовов)
- **Изображения**: Glide
- **Зависимости**:
  - AndroidX (Core, Lifecycle, Activity, Fragment, AppCompat, Material)
  - RecyclerView, SwipeRefreshLayout
  - Navigation Fragment и UI
  - Retrofit и Gson Converter
  - Glide

## Структура проекта

```
app/
├── src/main/java/com/example/appstore/
│   ├── data/
│   │   ├── model/
│   │   │   └── App.kt                    # Модель данных приложения
│   │   └── repository/
│   │       └── AppRepository.kt          # Репозиторий для получения данных
│   ├── ui/
│   │   ├── activity/
│   │   │   └── MainActivity.kt           # Главная активность
│   │   ├── adapter/
│   │   │   └── AppAdapter.kt             # Адаптер для списка приложений
│   │   ├── fragment/
│   │   │   ├── AppListFragment.kt        # Фрагмент списка приложений
│   │   │   └── AppDetailFragment.kt      # Фрагмент детальной карточки
│   │   └── viewmodel/
│   │       ├── AppListViewModel.kt       # ViewModel для списка
│   │       └── AppDetailViewModel.kt     # ViewModel для деталей
│   └── utils/                            # Утилиты (пусто)
├── src/main/res/
│   ├── layout/
│   │   ├── activity_main.xml             # Layout главной активности
│   │   ├── fragment_app_list.xml         # Layout списка приложений
│   │   ├── fragment_app_detail.xml       # Layout карточки приложения
│   │   └── item_app.xml                  # Layout элемента списка
│   ├── navigation/
│   │   └── nav_graph.xml                 # Граф навигации
│   ├── values/
│   │   ├── strings.xml                   # Строки
│   │   ├── colors.xml                    # Цвета
│   │   └── themes.xml                    # Темы
│   └── xml/
│       └── file_paths.xml                # Пути для FileProvider
└── src/main/AndroidManifest.xml          # Манифест приложения
```

## Сборка и запуск

1. **Требования**:
   - Android Studio Arctic Fox или новее
   - JDK 17
   - Android SDK API 21+

2. **Клонирование репозитория**:
   ```bash
   git clone https://github.com/Lassa30/android-task.git
   cd android-task
   ```

3. **Открытие в Android Studio**:
   - Откройте Android Studio
   - Выберите "Open an existing Android Studio project"
   - Выберите папку android-task

4. **Синхронизация Gradle**:
   - Android Studio автоматически синхронизирует зависимости
   - Если нет, нажмите "Sync Project with Gradle Files"

5. **Запуск**:
   - Подключите устройство или запустите эмулятор
   - Нажмите "Run" (зеленая стрелка) или Shift+F10

## Функционал

### Список приложений
- Отображение списка приложений в RecyclerView
- Поиск по названию приложения
- Pull-to-refresh для обновления списка
- Переход к детальной карточке по клику

### Карточка приложения
- Детальная информация: название, описание, версия, размер, рейтинг
- Галерея скриншотов (показывается первый скриншот)
- Кнопка "Установить/Удалить" в зависимости от статуса

### Установка приложений
- Скачивание APK по URL с помощью DownloadManager
- Установка через PackageInstaller (API 21+) или Intent (старые версии)
- Отображение прогресса установки
- Обработка ошибок

### Удаление приложений
- Использование Intent.ACTION_DELETE для удаления
- Обновление статуса приложения после удаления

## Декомпозиция проекта

Проект декомпозирован на 6 эпиков согласно PLAN.MD:

1. **Настройка проекта и базовая структура** (1 неделя)
2. **Реализация списка приложений** (1 неделя)
3. **Реализация карточки приложения** (1 неделя)
4. **Реализация установки приложений** (1 неделя)
5. **Реализация удаления приложений** (1 неделя)
6. **Финализация и тестирование MVP** (1 неделя)

Каждая задача оценивается не более чем в 1 день.

## Mock данные

Для MVP используются mock-данные в AppRepository.kt. В реальном приложении данные будут загружаться из API.

## Разрешения

Приложение запрашивает следующие разрешения:
- INTERNET: для скачивания APK
- REQUEST_INSTALL_PACKAGES: для установки приложений
- REQUEST_DELETE_PACKAGES: для удаления приложений

## Будущие улучшения

- Интеграция с реальным API магазина приложений
- Аутентификация пользователей
- Отзывы и рейтинги
- Категории и фильтры
- Push-уведомления об обновлениях
- Кэширование изображений и данных
- Unit и интеграционные тесты

## Лицензия

Этот проект создан в образовательных целях и не предназначен для коммерческого использования.
