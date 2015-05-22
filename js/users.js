$( document ).ready(function() {
	
    console.log( "Users Page ready!" );
    
    GomlogDB('selectUserTop10', makeUserTop10List);
    
});