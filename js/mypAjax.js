/* ----- Ajax  -----*/

var ctxPath = ""; // 현재 주소로 수정

function commonAjaxFunction(urlStr, params, callBack) {
	var defUrlSurfix = ".php";

	var callUrl = ctxPath + urlStr + defUrlSurfix;

	$.ajax({
		type : "GET",
		url : callUrl,
		data : params,
		contentType : 'application/json',
		
		success : function(response) {
			callBack($.parseJSON(response));
//			alert(response);
		},
		error : function() {
			// offLoading();
			/*alert('이클립스 consol log를 확인하세요.');*/
		}
	});
}


function GomlogDB(query, UICallBackFun, ip, date, time, title, artist, album, duration, size, utc, version) {
	var params = {
		"query" : query,
		"ip" : ip ,
		"date" : date ,
		"time" : time ,
		"title" : title ,
		"artist" : artist ,
		"album" : album ,
		"duration" : duration ,
		"size" : size ,
		"utc" : utc ,
		"version" : version 
	};
	commonAjaxFunction('db/GomLog', params, UICallBackFun);
}

function Response(returnData) {
	
	$.each( returnData, function( key, value ) {
			console.log(value);
	});
}

