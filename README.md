# 7quizzes

## Description

Приложение представляет из себя онлайн викторину без ведущего. 
Игровые сессии разделены по комнатам на несколько человек. Комната создаётся любым игроком вручную. При создании комнаты игрок может выбрать её название, ограничение по количеству игроков и количество вопросов в игре. После создания комната появляется в глобальном списке комнат, где её видят другие игроки. Игроки могут присоединиться к любой комнате, в которой есть место. Игра начинается, когда любой игрок в комнате нажмёт “старт”. После этого кнопка пропадает и игра начнется для всех игроков в комнате.
Игрокам в комнате одновременно задается вопрос и даётся 30 секунд чтобы ответить. Принимается ответ игрока, который первый выбрал вариант ответа. Правильный ответ может быть только один. В случае, если ответ оказался правильным, ответивший игрок получает баллы, а остальные игроки не получают ничего, при этом видят правильный ответ и ответившего. Если ответ оказался неверным, тогда ответивший не получает баллы и теряет возможность выбрать вариант ответа в этом раунде, ждёт ответов остальных. Раунд заканчивается, когда один из игроков даст правильный ответ или выйдет время, отведенное на ответ в этом раунде. Игра заканчивается по истечении количества вопросов.

## Stack technology

Backend:

- Java 8
- Spring Boot
- PostgreSQL
- REST API, WebSocket

Frontend;

- React.JS
- Redux

## Vocabulary

**Solo** — реализация приложения, в которой в отдельной игре участвует только один игрок.
Базовая игра уже должна работать.
Несколько игроков могут играть одновременно, независимо друг от друга.

**Multiplayer** — реализация приложения, в которой несколько игроков могут участвовать в одной игре.
**User** — пользователь / игрок.
В случае Solo — анонимный пользователь, никаких данных о игроке не хранится.
В случае Multiplayer — есть регистрация. Хранится имя/nickname, логин и хэш пароля.
После аутентификации,  без входных данных (Solo) или по логину/паролю (Multiplayer) клиент получает userToken, который используется как олицетворение пользователя в системе.

**Room** — игровая комната. 
В игровой комнате собираются один (Solo) или более (Multiplayer) пользователей.
User создает свою комнату (Solo/Multiplayer) или заходит в существующую (Multiplayer).

**Game** — игра, происходящая в комнате.
Игра создается в комнате.
После завершения игры, в комнате может быть начата следующая игра.
Процесс игры — отвечать на вопросы.
За правильные/неправильные ответы определенным образом меняется число очков у игрока.

**Question** — вопрос в игре. 
Как объект для клиента содержит в себе текст вопроса и список Answer. 
Как объект для сервера еще содержит id правильного ответа.

**Answer** — вариант ответа на вопрос игры.

**Сервис игры** — основной сервис игры, содержащий движок игры-викторины, менеджер игр (Game), менеджер комнат (Room) и т.д. 
Запрашивает список вопросов для игры (Game) из некоторого репозитория.

## Stories

**Solo**

1. Я как игрок хочу войти в комнату, чтобы иметь возможность начать игру
2. Я как игрок хочу нажать на кнопку “Старт”, чтобы начать игру
3. Я как игрок хочу увидеть вопрос и варианты ответа, чтобы иметь возможность выбрать ответ
4. Я как игрок хочу отправить выбранный мной вариант ответа, чтобы побороться за баллы
5. Я как игрок хочу видеть правильный ответ на вопрос, баллы за вопрос и общий балл в конце раунда, чтобы узнать, сколько баллов я получил
6. Я как игрок хочу видеть правила игры, чтобы знать, как играть

**Multiplayer**

7. Я как игрок хочу увидеть список игровых комнат, чтобы понять, в какие я могу сейчас войти
(Модификация истории из Solo) Я как игрок хочу войти в комнату, чтобы дождаться других игроков и начать игру
8. Я как игрок хочу видеть список всех игроков, которые со мной в одной комнате, чтобы знать, с кем я соревнуюсь
9. Я как игрок хочу знать, сколько времени у меня осталось на ответ на текущий вопрос, чтобы успеть ответить вовремя 
10. Я как игрок хочу видеть баллы других игроков, чтобы сравнивать себя с другими в процессе игры 
11. Я как игрок хочу видеть финальное распределение баллов после окончания игры, чтобы узнать, на каком я месте

