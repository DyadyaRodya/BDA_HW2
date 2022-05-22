# Задание 2
Программа должна подсчитывать статистику взаимодействия пользователей с новостной лентой для каждой новости за все время. Входные данные: справочник типов взаимодействия (1 - открыл и прочитал, 2 - открыл на предпросмотр, 3 - не взаимодействовал), идентификатор новости, идентификатор пользователя, время взаимодействия, идентификатор типа взаимодействия. Выходные данные: идентификатор новости, тип взаимодействия, количество типов взаимодействия с новостью.

### Технология подачи новых данных в систему: bash

### Технология хранения: Ignite Native Persistence

### Технология обработки данных: Ignite compute

## Схема взаимодействия компонент
![изображение](https://user-images.githubusercontent.com/55759699/169707300-b9c6f980-40ee-4628-91a0-1817edefc956.png)

## Требования и зависимости
1. Java 8
2. Maven
3. Docker
4. Ignite 2.7.0
5. springframework 1.5.18.RELEASE
6. commons-lang3
7. Python

## Для запуска приложения необходимо
1. Скомпилировать код через maven (например, `mvn -B package --file pom.xml`). После сборки в `target/` появится `lab2-1.0-SNAPSHOT.jar`
2. Собрать контейнер `docker build .`
3. Запустить полученный образ `docker run -d -p 8080:8080 <IMAGE_ID>` (или без `-d`)

## Скриншот успешно выполненных тестов
![изображение](https://user-images.githubusercontent.com/55759699/169707769-c65a4b25-85c5-47f9-a709-56789c11d5dc.png)

## Скриншот выполнения Job (логи)
![изображение](https://user-images.githubusercontent.com/55759699/169708980-9d62a802-c62a-418d-8bab-12328712b1c9.png)
![изображение](https://user-images.githubusercontent.com/55759699/169709040-eca441f6-04ad-4271-b888-72a5a6f3431e.png)

## Работа с приложением
### Создание записи:

POST http://localhost:8080/newsinteraction/create
{"news_id": 2,"user_id":1,"event_type":1,"seconds":200}
![изображение](https://user-images.githubusercontent.com/55759699/169709252-a59864ea-0b1c-417b-86f8-c21d3ac3dce2.png)
ответ 200

### Наполнение хранилища случайными событиями:
`./generate_and_send_data.sh`
Будет создано и отправлено на REST API 10 000 случайных записей о событиях с новостями

### Проверка наличия записи:
GET http://localhost:8080/newsinteraction/get/all
или
GET http://localhost:8080/newsinteraction/get/{id}
![изображение](https://user-images.githubusercontent.com/55759699/169709301-b4037f48-7d62-41c4-93c2-e8da408be565.png)

### Запуск подсчета статистики:
POST http://localhost:8080/newsinteraction/count
![изображение](https://user-images.githubusercontent.com/55759699/169709344-b21d1ae7-7927-4827-bd4e-8fd61a05ff61.png)

Также можно выполнить команду `curl -X POST "http://localhost:8080/newsinteraction/count"`

### Сбор статистики:
GET http://localhost:8080/newsinteraction/get/stats
![изображение](https://user-images.githubusercontent.com/55759699/169709551-93c9f847-7ef7-451d-8c9b-ce64ae357283.png)

