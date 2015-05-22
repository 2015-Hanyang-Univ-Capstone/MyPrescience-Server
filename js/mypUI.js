
/* ----- User Top 10 출력 -----*/
function makeUserTop10List(returnData) {
	var target = $("#UserTop10List");
	target.text("");

	var tagStr = "";

	var itemLength = returnData.length;

	tagStr += "<table class='table'>";

	if (itemLength != 0) {

		$.each(returnData, function(i, result) {
			tagStr += "	<tr >";
			tagStr += "		<td class='center'> " + result.title + "</td>";
			tagStr += "		<td class='center'> " + result.artist + "</td>";
			tagStr += "		<td class='center'> " + result.album + "</td>";
			tagStr += "		<td class='center'> " + result.play_count + "</td>";
			tagStr += "	</tr>";
		});

	} else {
		// 조회된거 없을때의 처리
		tagStr += "		<p>검색된 내역이 없습니다.</p>";
	}

	tagStr += "</table>";
	target.html(tagStr);

}

/* ----- Music Top 100 출력 -----*/
function makeMusicTop100List(returnData) {
	var target = $("#MusicTop100List");
	target.text("");

	var tagStr = "";

	var itemLength = returnData.length;

	tagStr += "<table class='table'>";

	if (itemLength != 0) {

		$.each(returnData, function(i, result) {
			tagStr += "	<tr >";
			tagStr += "		<td class='center'> " + result.title + "</td>";
			tagStr += "		<td class='center'> " + result.artist + "</td>";
			tagStr += "		<td class='center'> " + result.album + "</td>";
			tagStr += "		<td class='center'> " + result.play_count + "</td>";
			tagStr += "	</tr>";
		});

	} else {
		// 조회된거 없을때의 처리
		tagStr += "		<p>검색된 내역이 없습니다.</p>";
	}

	tagStr += "</table>";
	target.html(tagStr);

}

/* ----- MySQL Usage 출력 -----*/
function makeMySQLUsage(returnData) {
	
	var MaxSize = 10000;
	var UsagePercent = (Math.round(returnData[0].MAXSize)/MaxSize)*100;
	
	$("#mysqlUsage").val(Math.round(UsagePercent));
}
