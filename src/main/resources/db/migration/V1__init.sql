-- create schema
CREATE SCHEMA tbot;

--------------------------------------------------------------
-- УСЛУГИ
--------------------------------------------------------------

create sequence tbot.services_id_seq;

CREATE TABLE IF NOT EXISTS tbot.services (
                                             id              bigint not null constraint services_id_pk primary key,
                                             title           varchar,
                                             description     varchar,
                                             price           bigint
);


insert into tbot.services(id, title, description, price)
values(1, 'Обслуживаие 1С', 'Обслуживаие 1С', 5000);

insert into tbot.services(id, title, description, price)
values(2, 'Разработка сайтов и интернет-магазинов на 1С-Битрикс',
       'Разработка сайтов и интернет-магазинов на 1С-Битрикс', 80000);

insert into tbot.services(id, title, description, price)
values(3, 'Разработка мобильных приложений', 'Разработка мобильных приложений', 25000);

SELECT setval('tbot.services_id_seq', 3, true);

--------------------------------------------------------------
-- ПОСЛЕДНИЕ ДЕЙСТВИЯ
--------------------------------------------------------------

CREATE TABLE IF NOT EXISTS tbot.telegram_action (
                                                    chat_id           bigint not null constraint telegram_action_id_pk primary key,
                                                    action            varchar,
                                                    context           varchar
);

--------------------------------------------------------------
-- ЗАКАЗЫ
--------------------------------------------------------------

create sequence tbot.orders_id_seq;

CREATE TABLE IF NOT EXISTS tbot.orders (
                                           id               bigint not null constraint orders_id_pk primary key,
                                           status           varchar,
                                           service_id       bigint not null  constraint orders_service_ref_fk references tbot.services,
                                           user_name        varchar,
                                           user_phone       varchar
);

insert into tbot.orders(id, status, service_id, user_name, user_phone)
values(1, 'CREATED', 1, 'Петров Петр Петрович', '+79823456123');

insert into tbot.orders(id, status, service_id, user_name, user_phone)
values(2, 'CREATED', 2, 'Иванов Иван Иванович', '+79813457623');

insert into tbot.orders(id, status, service_id, user_name, user_phone)
values(3, 'CREATED', 3, 'Федосеева мария Николаевна', '+79345682397');

insert into tbot.orders(id, status, service_id, user_name, user_phone)
values(4, 'IN_PROGRESS', 3, 'Петрова Кавдия Ивановна', '+79578630765');

insert into tbot.orders(id, status, service_id, user_name, user_phone)
values(5, 'FINISHED', 3, 'Маркина Мария Сергеевна', '+79675695674');

SELECT setval('tbot.orders_id_seq', 5, true);

--------------------------------------------------------------
-- КАТЕГОРИИ
--------------------------------------------------------------

create sequence tbot.categories_id_seq;

CREATE TABLE IF NOT EXISTS tbot.categories (
                                               id              bigint not null constraint categories_id_pk primary key,
                                               title           varchar
);

insert into tbot.categories(id, title)
values(1, 'Отчетность');

insert into tbot.categories(id, title)
values(2, 'Финансовый учет');

insert into tbot.categories(id, title)
values(3, 'Штрафные санкции');

SELECT setval('tbot.categories_id_seq', 3, true);

--------------------------------------------------------------
-- Полезный контент
--------------------------------------------------------------

create sequence tbot.useful_content_id_seq;

CREATE TABLE IF NOT EXISTS tbot.useful_content (
                                                   id              bigint not null constraint useful_content_id_pk primary key,
                                                   category_id     bigint not null  constraint useful_content_categories_ref_fk references tbot.categories,
                                                   url             varchar,
                                                   title           varchar
);

insert into tbot.useful_content(id, category_id, title, url)
values(1, 1, 'Новая форма 6-НДФЛ с 2021 года',
       'https://www.1cbit.ru/blog/novaya-forma-6-ndfl-s-2021-goda/');

insert into tbot.useful_content(id, category_id, title, url)
values(2, 1, 'Сплошное наблюдение: новая отчетность для компаний и ИП в 2021 году',
       'https://www.1cbit.ru/blog/sploshnoe-nablyudenie-novaya-otchetnost-dlya-kompaniy-i-ip-v-2021-godu/');

insert into tbot.useful_content(id, category_id, title, url)
values(3, 1, 'Сроки сдачи отчетности в 2021 году',
       'https://www.1cbit.ru/blog/sroki-sdachi-otchetnosti-v-2021-godu/');

insert into tbot.useful_content(id, category_id, title, url)
values(4, 2, 'Как избежать кассового разрыва',
       'https://www.1cbit.ru/blog/kassovyy-razryv-opredelenie-prichiny-raschet-likvidatsiya/');

insert into tbot.useful_content(id, category_id, title, url)
values(5, 2, 'Что такое отчётность по МСФО',
       'https://www.1cbit.ru/blog/chto-takoe-otchyetnost-po-msfo/');

insert into tbot.useful_content(id, category_id, title, url)
values(6, 2, 'Отчёт о движении денежных средств (отчет ДДС)',
       'https://www.1cbit.ru/blog/otchyet-o-dvizhenii-denezhnykh-sredstv-odds/');

insert into tbot.useful_content(id, category_id, title, url)
values(7, 3, 'За нарушение карантина - административный штраф',
       'https://www.1cbit.ru/blog/za-narushenie-karantina-administrativnyy-shtraf/');

insert into tbot.useful_content(id, category_id, title, url)
values(8, 3, 'Новые штрафы для бизнеса в 2020 году',
       'https://www.1cbit.ru/blog/novye-shtrafy-dlya-biznesa-v-2020-godu/');

insert into tbot.useful_content(id, category_id, title, url)
values(9, 3, 'Штрафы для бизнеса могут вырасти в десятки раз',
       'https://www.1cbit.ru/blog/shtrafy-dlya-biznesa-mogut-vyrasti-v-desyatki-raz/');

SELECT setval('tbot.useful_content_id_seq', 9, true);

--------------------------------------------------------------
-- Докладчик
--------------------------------------------------------------

create sequence tbot.speakers_id_seq;

CREATE TABLE IF NOT EXISTS tbot.speakers (
                                             id              bigint not null constraint speakers_id_pk primary key,
                                             name            varchar,
                                             company         varchar,
                                             description     varchar
);


insert into tbot.speakers(id, name, company, description)
values(1, 'Роман Шапошник', 'ZEDEDA Inc.',
       'Роман — специалист по ПО с открытым исходным кодом, в настоящий момент входит в совет директоров The Apache Software Foundation и LF Edge. Лично внес вклад во множество проектов с открытым кодом: от Linux Kernel до Hadoop и ffmpeg. Также он соучредителем и вице-президентом по продукту и стратегии в стартапе по edge virtualization, Zededa. На протяжении своей карьеры Роман занимал руководящие должности в таких известных компаниях, как Sun Microsystems, Yahoo!, Cloudera и Pivotal Software. Имеет степень магистра по математике и информатики СПбГУ. Любит немецкий крафтовый лагер.');

insert into tbot.speakers(id, name, company, description)
values(2, 'Барух Садогурский', 'JFrog',
       'Барух Садогурский (a.k.a. JBaruch) — Head of DevOps Advocacy и Developer Advocate в компании JFrog. Больше всего любит рассказывать о технологиях — то есть просто поболтать любит, но человек, говорящий о технологиях, имеет умный вид, да и 18 лет опыта в сфере высоких технологий никуда не делись. Когда он не выступает (ну, или не летит к месту следующего выступления), то изучает технологии, людей и то, как они работают, точнее, не работают вместе.

       Барух — соавтор книги «Liquid Software», CNCF ambassador и профессиональный спикер по таким темам, как DevOps, DevSecOps, Go, Java и др. Он регулярно выступает на таких известных конференциях, как Joker, JPoint, DevOops, Heisenbug, DockerCon, GopherCon, Devoxx, DevOps Days, OSCON, Qcon, JavaOne и др. Некоторые его доклады можно посмотреть здесь: jfrog.com/shownotes');

insert into tbot.speakers(id, name, company, description)
values(3, 'Леонид Игольник', null ,
       'Леонид хорошо знаком и с разработкой, и с управлением, и с администрированием масштабных проектов. До недавней продажи SignalFx в Splunk он занимал должность Executive Vice President of Engineering в SignalFx. Всю свою карьеру он занимается онлайн-приложениями, начав её в одном из первых интернет-провайдеров Израиля. Первый опыт работы с масштабными приложениями на Java у него произошёл в 2002-м, когда он работал во втором по величине на тот момент доменном регистраторе.');

insert into tbot.speakers(id, name, company, description)
values(4, 'Матвей Кукуй', 'Amixr Inc.',
       'CEO в Amixr Inc., в прошлом — DevOps и Software Engineer. Ex. Kaspersky, Cisco, Constructor.IO.');


SELECT setval('tbot.speakers_id_seq', 4, true);

--------------------------------------------------------------
-- События
--------------------------------------------------------------

create sequence tbot.events_id_seq;

CREATE TABLE IF NOT EXISTS tbot.events (
                                           id               bigint not null constraint events_id_pk primary key,
                                           title            varchar,
                                           status           varchar,
                                           description      varchar,
                                           date_time        timestamp,
                                           duration         varchar

);


insert into tbot.events(id, title, description, date_time, duration, status  )
values(1, 'Edge Computing: a trojan horse of DevOps tribe infiltrating the IoT industry',
       ' Роман проведет краткий обзор IoT-индустрии, облачных и мобильных вычислений и расскажет, как их слияние привело к появлению парадигмы Edge Computing.',
       '2021-04-04 19:30:00', '2ч', 'NEW');

insert into tbot.events(id, title, description, date_time, duration, status)
values(2, 'Устраиваем DevOps без полномочий: Даже «DevOps-инженер» может помочь',
       ' Этот доклад для тех, кто понимает, что DevOps — это история про культуру, коллаборацию и общение, но не очень представляет, как, будучи скромным исполнителем или тимлидом, повлиять на целый энтерпрайз и сдвинуть организацию в сторону ДевОпса.',
       '2021-04-17 13:30:00', '2ч 30 мин', 'NEW');

insert into tbot.events(id, title, description, date_time, duration, status)
values(3, 'Как запилить DevOps-инструмент и превратить его в бизнес в США с клиентами и инвесторами',
       'Матвей покажет, как DevOps-инструменты превращаются в продуктовый бизнес и что нужно изучить, имея инженерный бэкграунд.',
       '2021-04-19 16:30:00', '3ч', 'NEW');

insert into tbot.events(id, title, description, date_time, duration, status)
values(4, 'Как запилить DevOps-инструмент',
       'Матвей покажет, как DevOps-инструменты превращаются в продуктовый бизнес',
       '2021-03-19 16:30:00', '3ч', 'FINISHED');

SELECT setval('tbot.events_id_seq', 4, true);

--------------------------------------------------------------
-- Докладчики по событиям
--------------------------------------------------------------

CREATE TABLE IF NOT EXISTS tbot.events_speakers (
                                                    event_id         bigint not null  constraint events_speakers_events_ref_fk references tbot.events,
                                                    speaker_id       bigint not null  constraint events_speakers_speakers_ref_fk references tbot.speakers
);

insert into tbot.events_speakers(event_id, speaker_id)
values(1, 1);

insert into tbot.events_speakers(event_id, speaker_id)
values(2, 2);

insert into tbot.events_speakers(event_id, speaker_id)
values(2, 3);

insert into tbot.events_speakers(event_id, speaker_id)
values(3, 4);

insert into tbot.events_speakers(event_id, speaker_id)
values(4, 4);

--------------------------------------------------------------
-- Регистрация на события
--------------------------------------------------------------

create sequence tbot.event_registrations_id_seq;

CREATE TABLE IF NOT EXISTS tbot.event_registrations (
                                                        id               bigint not null constraint event_registrations_id_pk primary key,
                                                        event_id         bigint not null  constraint event_registrations_events_ref_fk references tbot.events,
                                                        user_name        varchar,
                                                        user_phone       varchar
);

insert into tbot.event_registrations(id, event_id, user_name, user_phone)
values(1, 1, 'Петров Петр Петрович', '+79823456123');

insert into tbot.event_registrations(id, event_id, user_name, user_phone)
values(2, 2, 'Иванов Иван Иванович', '+79813457623');

insert into tbot.event_registrations(id, event_id, user_name, user_phone)
values(3, 3, 'Федосеева мария Николаевна', '+79345682397');

insert into tbot.event_registrations(id, event_id, user_name, user_phone)
values(4, 4, 'Петрова Кавдия Ивановна', '+79578630765');

insert into tbot.event_registrations(id, event_id, user_name, user_phone)
values(5, 4, 'Маркина Мария Сергеевна', '+79675695674');

SELECT setval('tbot.event_registrations_id_seq', 5, true);

