<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!--[if lt IE 9]>
<script src="./assets/global/plugins/respond.min.js"></script>

<script src="./assets/global/plugins/ie8.fix.min.js"></script>
<![endif]-->
<!-- BEGIN CORE PLUGINS -->
<script src="./js/config.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/jquery.min.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/bootstrap/js/bootstrap.min.js"
	type="text/javascript"></script>

<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->

<script src="./assets/global/plugins/amcharts/amcharts/amcharts.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/amcharts/serial.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/amcharts/pie.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/amcharts/radar.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/amcharts/themes/light.js"
	type="text/javascript"></script>
<script
	src="./assets/global/plugins/amcharts/amcharts/themes/patterns.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/amcharts/themes/chalk.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/ammap/ammap.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/ammap/maps/js/worldLow.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/amcharts/amstockcharts/amstock.js"
	type="text/javascript"></script>
<script src="./assets/global/plugins/fullcalendar/fullcalendar.min.js"
	type="text/javascript"></script>
<script
	src="./assets/global/plugins/horizontal-timeline/horizontal-timeline.js"
	type="text/javascript"></script>
	
<%--
 <script src="./assets/global/plugins/bootstrap-table/bootstrap-table.min.js" type="text/javascript"></script>
--%>
<%--
 <script src="./assets/global/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>
--%>

<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="./assets/global/scripts/app.min.js" type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->
<!-- BEGIN PAGE LEVEL SCRIPTS -->
<script src="./assets/pages/scripts/dashboard.min.js"
	type="text/javascript"></script>
<!-- END PAGE LEVEL SCRIPTS -->
<!-- BEGIN THEME LAYOUT SCRIPTS -->
<script src="./assets/layouts/layout/scripts/layout.min.js"
	type="text/javascript"></script>
<script src="./assets/layouts/layout/scripts/demo.min.js"
	type="text/javascript"></script>
<script src="./assets/pages/scripts/components-date-time-pickers.min.js"
	type="text/javascript"></script>
<script src="./assets/layouts/global/scripts/quick-sidebar.min.js"
	type="text/javascript"></script>
<script src="./assets/layouts/global/scripts/quick-nav.min.js"
	type="text/javascript"></script>
	
        <script src="./assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
          <script src="./assets/global/plugins/bootstrap-sweetalert/sweetalert.min.js" type="text/javascript"></script>
<script type="javascript" src="./js/jquery.cookie.js"></script>
<!-- END THEME LAYOUT SCRIPTS -->

<script type='text/javascript'>
    $(function(){
        var realname = decodeURI(Cookies.get("realname"));
        $("#welcome").html("欢迎你," + realname + "!");
    });
</script>