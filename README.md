# Приложение погода

## Функционал

- **Поиск погоды**: Выполняйте поиск местоположений и просматривайте подробную информацию о погоде, такую как
  температура, ощущения от нее и погодные условия.
- **Responsive Дизайн**: Оптимизированный под мобильные и десктопные устройства
- **API интеграция**: используется OpenWeather Api
- **Регистрация**

## Как начать

### Клонирование репозитория

```bash
git clone https://github.com/mak7im-05/WeatherApp
```

### Настройка конфигурации

1. **Properties files**:
    - Создай файл в директории src\main\resources с названием postgres.properties
    - помести в него следующее и заполни поля, название бд.

```angular2html
hibernate.driver_class=org.postgresql.Driver
hibernate.connection.url=jdbc:postgresql://localhost:5432/weatherAppDatabase
hibernate.connection.username=postgres
hibernate.connection.password=postgres
hibernate.hbm2ddl.auto=update

hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
hibernate.show_sql=true

api.key=apikey
```
- Вместо apikey введи свой апи ключ
2. **Настрой конфигурацию запуска через томкат версии 10>**
3. **Там же в VM options добавь строку ```-ea -Dspring.profiles.active=postgres```**
##### Теперь ты можешь запустить приложение

#### Для тестов:

##### Настройка конфигурации

1.В настройках конфигурации запуска, добавь переменные:
```-ea -Dspring.profiles.active=h2```

2.**Готово**

