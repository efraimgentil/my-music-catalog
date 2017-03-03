
INSERT INTO ARTIST(id , name ,normalized_name ) VALUES ( 1 , 'Rangom GUY' , 'randomguy');
INSERT INTO ARTIST(id , name ) VALUES ( 2 , 'Mario');
INSERT INTO ARTIST(id , name ) VALUES ( 3 , 'Luigi');
INSERT INTO ARTIST(id , name ) VALUES ( 4 , 'Peach');
INSERT INTO ARTIST(id , name ) VALUES ( 5 , 'Toad');
INSERT INTO ARTIST(id , name ) VALUES ( 6 , 'Koppa');
INSERT INTO ARTIST(id , name ) VALUES ( 7 , 'Kamus');
INSERT INTO ARTIST(id , name ) VALUES ( 8 , 'Alucard');
INSERT INTO ARTIST(id , name ) VALUES ( 9 , 'Richard');
INSERT INTO ARTIST(id , name ) VALUES ( 10 , 'Maria');
INSERT INTO ARTIST(id , name ) VALUES ( 11 , 'Link');
INSERT INTO ARTIST(id , name ) VALUES ( 12 , 'Zelda');


INSERT INTO album(id , name , normalized_name , artist_id  )
 values ( 1 , 'Random album name' , 'randomalbumname' , 1 );
INSERT INTO album(id , name , normalized_name , artist_id  )
 values ( 2 , 'Random 2' , 'random2' , 1 );
INSERT INTO album(id , name , normalized_name , artist_id  )
 values ( 3 , 'Dark side of the moon' , 'darksideofthemoon' , 2 );
INSERT INTO album(id , name , normalized_name , artist_id  )
 values ( 4 , 'Symphony of the night' , 'symphonyofthenight' , 8 );
INSERT INTO album(id , name , normalized_name , artist_id  )
 values ( 5 , 'Ocarina of Time' , 'ocarinaoftime' , 11 );