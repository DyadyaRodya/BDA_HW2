Создаем запись в кеше через запрос в приложение из Postman 

(Для CentOS 7 удобно загрузить и распаковать архив с официального сайта:
https://www.postman.com/downloads/
установить библиотеку yum install libXScrnSaver
запустить Postman, при первом запуске выбрать Sign in / Sign up through email instead внизу страницы, чтобы не логиниться с google-аккаунтом)

Создание записи:

POST http://localhost:8080/newsinteraction/create
{"news_id": 2,"user_id":1,"event_type":1,"seconds":200}

ответ 200


Проверка наличия записи:
GET http://localhost:8080/newsinteraction/get/all

POST http://localhost:8080/newsinteraction/count

GET http://localhost:8080/newsinteraction/get/stats