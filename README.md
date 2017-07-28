# SideNavigation
Test task using Navigation Drower

Сделать простое Android приложение с боковой навигацией. Пункты бокового меню формируются динамично из загружаемого с сервера JSON файла по ссылке ниже:
https://www.dropbox.com/s/fk3d5kg6cptkpr6/menu.json?dl=1

После загрузке по умолчанию должен быть выбран первый пункт меню. При перевороте экрана приложение должно запоминается последний выбранный пользователем.

JSON файл содержит список пунктов меню. Поле “name” содержит имя для отображения. Поле “function” может принимать следующие значения:
•	text
•	image
•	url

При выборе пользователем пункта меню, приложение должно отреагировать соответствующим типу пункта меню образом:
•	Для типа “message” отобразить на экране приложения текст из поля param.
•	Для типа “image” загрузить и отобразить на экране приложения картинку, URL которой задан в поле param.
•	Для типа “url” отобразить на экране приложения WebView и загрузить в него URL из поля param.
