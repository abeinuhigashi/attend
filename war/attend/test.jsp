<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="f" uri="http://www.slim3.org/functions"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="keywords" content="" />
<meta name="description" content="" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>トップページ</title>
<link href="/css/style.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" media="all" type="text/css" href="/css/jquery-ui-1.8.9.custom.css" />

<style type="text/css"> 
	.ui-timepicker-div .ui-widget-header{ margin-bottom: 8px; }
	.ui-timepicker-div dl{ text-align: left; }
	.ui-timepicker-div dl dt{ height: 25px; }
	.ui-timepicker-div dl dd{ margin: -25px 0 10px 65px; }
	.ui-timepicker-div td { font-size: 90%; }
</style> 
<script type="text/javascript" src="/js/jquery-1.5.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-1.8.9.custom.min.js"></script>
<script type="text/javascript" src="/js/jquery-ui-timepicker-addon.js"></script>

<script type="text/javascript">
	$(function(){
		$('.example-container > pre').each(function(i){
			eval($(this).text());
		});
	});

	$(function(){
	     $(".focus").focus(function(){
	          if(this.value == "途中参加の時間を入力"){
	               $(this).val("").css("color","#f39");
	          }
	     });
	     $(".focus").blur(function(){
	          if(this.value == ""){
	               $(this).val("途中参加の時間を入力").css("color","#969696");
	          }
	     });
	});

</script>

</head>
<body>
<div id="wrapper">
	<jsp:include page="/attend/common/header.jsp" />
	<div id="page">
		<div id="page-bgtop">
			<div id="page-bgbtm">
				<jsp:include page="/attend/common/sidebar.jsp" />
				<div id="content">
					<div class="post">
						<h2>TopPage</h2>
						<p>ここには説明書きが入ります。わたしもげたー。１８歳。。。。。。。。。あああああああああああああああああああああああああああああああああああああああああああああああああ。</p>
						<p>ここには説明書きが入ります。</p>
						<p>ここには説明書きが入ります。</p>
						<input type="text" class="focus" value="" />




<!-- ============= example -->

	<div class="example-container">

		<p>Add a simple timepicker to jQuery UI's datepicker</p>

		<div>

	 		<input type="text" name="example1" id="example1" value="" />

		</div>

	

<pre>

$('#example1').datetimepicker();

</pre>

	</div>



<!-- ============= example -->

	<div class="example-container">

		<p>Show time in AM/PM 12 hour format</p>

		<div>

		<input type="text" name="example2" id="example2" value="" />

		</div>

	

<pre>

$('#example2').datetimepicker({

	ampm: true

});

</pre>

	</div>



<!-- ============= example -->

	<div class="example-container">

		<p>Show only the time picker without date picker</p>

		<div>

			<input type="text" name="example3" id="example3" value="" />

		</div>

	

<pre>

$('#example3').timepicker({});

</pre>

	</div>

	

<!-- ============= example -->

	<div class="example-container">

		<p>Change the format (default is "hh:mm tt")</p>

		<dl>

			<dt>h</dt><dd>Hour with no leading 0</dd>

			<dt>hh</dt><dd>Hour with leading 0</dd>

			<dt>m</dt><dd>Minute with no leading 0</dd>

			<dt>mm</dt><dd>Minute with leading 0</dd>

			<dt>s</dt><dd>Second with no leading 0</dd>

			<dt>ss</dt><dd>Second with leading 0</dd>

			<dt>t</dt><dd>a or p for AM/PM</dd>

			<dt>tt</dt><dd>A or P for AM/PM</dd>

			<dt>T</dt><dd>am or pm for AM/PM</dd>

			<dt>TT</dt><dd>AM or PM for AM/PM</dd>

		</dl>

		<p>Also related to your timeFormat, but not required, is the separator option</p>

		<dl>

			<dt>separator</dt><dd>Place holder between date and time, default=" "</dd>

		</dl>

		<div>

			<input type="text" name="example4" id="example4" value="" />

		</div>

	

<pre>

$('#example4').datetimepicker({

	timeFormat: 'h:m',

	separator: ' @ '

});

</pre>

	</div>

	

<!-- ============= example -->

	<div class="example-container">

		<p>Show Seconds, Minutes, or Hours</p>

		<dl>

			<dt>showHour</dt><dd>Show the hour, default=true</dd>

			<dt>showMinute</dt><dd>Show the minute, default=true</dd>

			<dt>showSecond</dt><dd>Show the second, default=false</dd>

		</dl>

		<div>

			<input type="text" name="example5" id="example5" value="" />

		</div>

	

<pre>

$('#example5').datetimepicker({

	showSecond: true,

	timeFormat: 'hh:mm:ss'

});

</pre>

	</div>

	

	

<!-- ============= example -->

	<div class="example-container">

		<p>Hours, Minutes, Seconds in steps</p>



		<dl>

			<dt>stepHour</dt><dd>hour steps, default=1</dd>

			<dt>stepMinute</dt><dd>minute steps, default=1</dd>

			<dt>stepSecond</dt><dd>second steps, default=1</dd>

		</dl>

		<div>

			<input type="text" name="example6" id="example6" value="" />

		</div>

	

<pre>

$('#example6').datetimepicker({

	showSecond: true,

	timeFormat: 'hh:mm:ss',

	stepHour: 2,

	stepMinute: 10,

	stepSecond: 10

});

</pre>

	</div>

	

	

<!-- ============= example -->

	<div class="example-container">

		<p>Default Hours, Minutes, Seconds</p>

		<dl>

			<dt>hour</dt><dd>default=0</dd>

			<dt>minute</dt><dd>default=0</dd>

			<dt>second</dt><dd>default=0</dd>

		</dl>

		<div>

			<input type="text" name="example7" id="example7" value="" />

		</div>

	

<pre>

$('#example7').datetimepicker({

	hour: 13,

	minute: 15

});

</pre>

	</div>

	

<!-- ============= example -->

	<div class="example-container">

		<p>Set the time range.  For instance if you only want to allow the user to choose times between 8AM and 5PM.</p>

		<dl>

			<dt>hourMin</dt><dd>default=0</dd>

			<dt>hourMax</dt><dd>default=23</dd>

			<dt>minuteMin</dt><dd>default=0</dd>

			<dt>minuteMax</dt><dd>default=59</dd>

			<dt>secondMin</dt><dd>default=0</dd>

			<dt>secondMax</dt><dd>default=59</dd>

		</dl>



		<div>

			<input type="text" name="example8" id="example8" value="" />

		</div>

	

<pre>







$('#example8').timepicker({

	ampm: true,

	hourMin: 8,

	hourMax: 16

});

</pre>

	</div>

	

<!-- ============= example -->

	<div class="example-container">

		<p>There is also localization support.  Read up on <a href="http://docs.jquery.com/UI/Datepicker/Localization" title="localization for datepicker" target="_BLANK">localization for datepicker</a> for adding support for more languages.  There are a few new definitions you will need to add to your language file:</p>

		<dl>

			<dt>timeOnlyTitle</dt><dd>default="Choose Time"</dd>

			<dt>timeText</dt><dd>default="Time"</dd>

			<dt>hourText</dt><dd>default="Hour"</dd>

			<dt>minuteText</dt><dd>default="Minute"</dd>

			<dt>secondText</dt><dd>default="Second"</dd>

			<dt>currentText</dt><dd>default="Now"</dd>

			<dt>closeText</dt><dd>default="Done"</dd>

			<dt>ampm</dt><dd>default=false (true/false, Some regions do use this, some don't)</dd>

		</dl>

		<p>Or optionally you may add them as options as shown below with Russian.</p>

		<div>

			<input type="text" name="example9" id="example9" value="" />

		</div>

	

<pre>

$('#example9').timepicker({

	timeOnlyTitle: 'Выберите время',

	timeText: 'Время',

	hourText: 'Часы',

	minuteText: 'Минуты',

	secondText: 'Секунды',

	currentText: 'Теперь',

	closeText: 'Закрыть'

});

</pre>



		<div class="documentation">

			<p>The first approach (as documented in the datepicker documentation and mentioned above) is to create your own regional objects per region, then use the setDefaults method to tie these together.  Setting datepicker and timepicker regionals separtely will help ensure proper wording when only using datepicker or timepicker.  Here is an example:</p>

		

<pre>

$.datepicker.regional['ru'] = {

	closeText: 'Закрыть',

	prevText: '&#x3c;Пред',

	nextText: 'След&#x3e;',

	currentText: 'Сегодня',

	monthNames: ['Январь','Февраль','Март','Апрель','Май','Июнь',

	'Июль','Август','Сентябрь','Октябрь','Ноябрь','Декабрь'],

	monthNamesShort: ['Янв','Фев','Мар','Апр','Май','Июн',

	'Июл','Авг','Сен','Окт','Ноя','Дек'],

	dayNames: ['воскресенье','понедельник','вторник','среда','четверг','пятница','суббота'],

	dayNamesShort: ['вск','пнд','втр','срд','чтв','птн','сбт'],

	dayNamesMin: ['Вс','Пн','Вт','Ср','Чт','Пт','Сб'],

	weekHeader: 'Не',

	dateFormat: 'dd.mm.yy',

	firstDay: 1,

	isRTL: false,

	showMonthAfterYear: false,

	yearSuffix: ''

};

$.datepicker.setDefaults($.datepicker.regional['ru']);



$.timepicker.regional['ru'] = {

	timeOnlyTitle: 'Выберите время',

	timeText: 'Время',

	hourText: 'Часы',

	minuteText: 'Минуты',

	secondText: 'Секунды',

	currentText: 'Теперь',

	closeText: 'Закрыть',

	ampm: false

};

$.timepicker.setDefaults($.timepicker.regional['ru']);

</pre>



		</div>

	</div>

	

	

	

<!-- ============= example -->

	<div class="example-container">

		<p>To show numbered grids under the sliders you may use the following options:</p>

		<dl>

			<dt>hourGrid</dt><dd>default=0</dd>

			<dt>minuteGrid</dt><dd>default=0</dd>

			<dt>secondGrid</dt><dd>default=0</dd>

		</dl>

		<p>When using ampm option hours will be displayed with an "a" or "p": 04a, 12p, etc...</p>

		<div>

			<input type="text" name="example10" id="example10" value="" />

		</div>

	

<pre>

$('#example10').timepicker({

	hourGrid: 4,

	minuteGrid: 10

});

</pre>

	</div>

	

	<!-- ============= example -->

	<div class="example-container">

		<p>When showing more than one month:</p>

		<div>

			<input type="text" name="example11" id="example11" value="" />

		</div>

	

<pre>

$('#example11').datetimepicker({

	numberOfMonths: 3

});

</pre>

	</div>

	

		<!-- ============= example -->

	<div class="example-container">

		<p>With minDate and maxDate datepicker options:</p>

		<div>

			<input type="text" name="example12" id="example12" value="" />

		</div>

	

<pre>

$('#example12').datetimepicker({

	numberOfMonths: 2,

	minDate: 0,

	maxDate: 30

});

</pre>

	</div>

	

	<!-- ============= example -->

	<div class="example-container">

		<p>Get and Set Datetime:</p>

		<div>

			<input type="text" name="example13" id="example13" value="" /> 

			<button id="example13_setdt" value="1">Set Datetime</button> 

			<button id="example13_getdt" value="1">Get Datetime</button> 

		</div>

	

<pre>

var ex13 = $('#example13');



ex13.datetimepicker();



$('#example13_setdt').click(function(){

	ex13.datetimepicker('setDate', (new Date()) );

});



$('#example13_getdt').click(function(){

	alert(ex13.datetimepicker('getDate'));

});

</pre>

	</div>



	<!-- ============= example -->

	<div class="example-container">

		<p>Use the datepicker minDate/maxDate options: (note: these must use Date objects)</p>

		<div>

			<input type="text" name="example14" id="example14" value="" />

		</div>

	

<pre>

$('#example14').datetimepicker({

	minDate: new Date(2010, 12, 20, 8, 30),

	maxDate: new Date(2010, 12, 31, 17, 30)

});

</pre>

	</div>
			
					</div>
					<div style="clear: both;">&nbsp;</div>
				</div>
				<!-- end #content -->
				<div style="clear: both;">&nbsp;</div>
			</div>
		</div>
	</div>
	<!-- end #page -->
</div>
<jsp:include page="/attend/common/footer.jsp" />


</body>
</html>
