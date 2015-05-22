/**
 * 
 */

$( document ).ready(function() {
	
    console.log( "Music Page ready!" );
    
    GomlogDB('selectMusicTop100', makeMusicTop100List);
    
});