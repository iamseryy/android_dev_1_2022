## Урок 14. Работа с сетью

---
## Цель практической работы:
- Применить знания по работе с сетью в Android.
- Поработать с REST API, используя библиотеку Retrofit.

## Что нужно сделать
Cоздать приложение для загрузки и отображения данных о пользователе. В интерфейсе также должна быть предусмотрена кнопка «Обновить», которая загрузит новые данные случайного пользователя и отобразит их на экране.
1. Создайте приложение и проинициализируйте в нём библиотеку Retrofit.
2. Попробуйте выполнить запрос https://randomuser.me/api/ в браузере и определите поля, которые приходят в ответ.
3. Опишите модель данных пользователя согласно данным из запроса.
4. Создайте запрос загрузки данных случайного пользователя для Retrofit.
5. Сверстайте экран с необходимыми полями.
6. Добавьте первичную инициализацию экрана — при запуске приложения пользователь должен сразу видеть результат работы программы.
7. Добавьте кнопку «Перезагрузить», при нажатии на которую приложение обновляет данные на экране.
8. Добавьте библиотеку Glide для загрузки изображений. Для этого вам необходимо познакомиться с инструкциями по [подключению](https://bumptech.github.io/glide/doc/download-setup.html) и [использованию](https://bumptech.github.io/glide/doc/getting-started.html).

## Советы и рекомендации
- Попробуйте самостоятельно узнать, какие параметры присутствуют в ответе Randomuser Api. Если возникнут проблемы, можно [подсмотреть результаты](https://randomuser.me/documentation#results).
- Для подключения Glide можете воспользоваться [видеоинструкцией](https://www.youtube.com/watch?v=hFVuMAuFSOs).
- Можно предусмотреть дополнительные состояния приложения: загрузку данных (лоадер) и состояние ошибки (текст сообщения и доступная кнопка «Обновить»).
## Что оценивается
- Выполнены все пункты задания.
- Приложение работает без крашей и ошибок.
- Код чистый, у переменных и компонентов понятные названия, соблюдаются принципы ООП.
- В приложении реализован подход MVVM.

## Как отправить работу на проверку
1. Используйте репозиторий android_dev_1_2022.
2. Скачайте изменения в репозитории на ваш компьютер.
3. Выполните практическую работу в папке m14_retrofit. Отправьте коммиты в удалённый репозиторий.

## Решение: m14_retrofit