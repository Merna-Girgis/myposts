INSERT INTO users (username, password, date_created)
VALUES ('user','user','2012-09-17 18:50:52.069'),
       ('admin','admin','2018-09-17 16:48:51.056'),
       ('root','root','2019-09-17 19:37:43.055');
    
INSERT INTO post (content, date_created, is_private, user_id)
VALUES ('The harder you work for something, the greater you’ll feel when you achieve it.','2019-05-20 18:50:52.069',true, 2),
       ('Do not wait, the time will never be just right. Start where you stand, and work with whatever tools you may have at your command, 
         and better tools will be found as you go along.','2019-03-09 16:48:51.056', true, 2),
       ('I am a java developer and proud.','2012-03-02 13:37:42.035',true, 2),
       ('Java developers never RIP, They just get garbage collected.','2019-04-03 19:37:43.055',false, 1),
       ('I married a java developer, I told him i could not open a jar then he told me to download Java.','2015-02-01 19:30:23.015',false, 2),
       ('I am a java developer and i do not know what i am doing.','2014-03-01 20:30:22.025',false, 3);
