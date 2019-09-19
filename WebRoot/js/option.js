/**
 * Created by QDHL on 2018/12/29.
  */
//饼图
var pie_option = {
	backgroundColor:'#232323',
	color: [
		"#FFF973",
		"#99CE7D",
		"#42B281",
		"#32B2EB",
		"#6C609E",
		"#A56CA3",
		"#EB77A6",
		"#F2AE6F"
	],
	title : {
		text: '',
		left:'20',
		top:'10',
		textStyle:{
			fontSize: '14px',
			color:'#DEDEDE',
		}
	},
	tooltip : {
		trigger: 'item',
		formatter: "{a} <br/>{b} : {c} ({d}%)"
	},
	legend: [
		{
			top: '10',
			right: '10',
			itemWidth: 8,
			itemHeight: 8,
			textStyle: {
				color: '#DEDEDE',
			}
		},
		{
			top: '30',
			right: '23',
			itemWidth: 8,
			itemHeight: 8,
			textStyle: {
				color: '#DEDEDE',
			}
		}
	],
	series : [
		{
			name:'',
			type: 'pie',
			radius : '55%',
			center: ['50%', '60%'],
			data:[{value:0, name:'饼图'}],
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}
	]
};

//堆叠柱状图
var stack_bar_option = {
	backgroundColor:'#232323',
	color: [
		"#4974D2",
		"#63AE67",
		"#6139CF",
		"#01a8c8",
		"#576379"
	],
	title : {
		text: '堆叠柱状图',
		left:'20',
		top:'10',
		textStyle:{
			fontSize: '14px',
			color:'#DEDEDE',
		}
	},
	tooltip : {
		trigger: 'axis',
		axisPointer : {
			type : 'shadow'
		},
		textStyle:{
			align:'left'
		},
	},
	legend: {
		data: ['夜行人','朝九晚五','晚归','居家族','无规律'],
		top:'10',
		right:'10',
		itemWidth: 8,
		itemHeight: 8,
		textStyle:{
			color:'#DEDEDE'
		}
	},
	grid: {
		// top:'10%',
		left: '10',
		right: '10',
		bottom: '20',
		containLabel: true
	},
	xAxis: {
		type : 'category',
		splitLine: {
			show: false,
		},
		data : [],
		axisLine: {
			lineStyle: {
				color: '#6D6D6D',
				style:'solid',
			},
		},
		axisLabel: {
			interval:0,
			textStyle: {
				color: '#FEFEFE',
				fontSize: '12',
			},
		},
		nameTextStyle:{
			color: '#FEFEFE',
			fontSize: '12',
		},
	},
	yAxis : [
		{
			type : 'value',
			axisLabel: {
				textStyle: {
					color: '#939393',
					fontSize: '12',
				},
			},
			nameTextStyle:{
				color: '#939393',
				fontSize: '12',
			},
			splitLine: {
				show: true,
				lineStyle: {
					type: 'solid',
					color: '#6D6D6D',
				}
			},
			axisLine: {
				show: true,
				lineStyle: {
					type: 'solid',
					color: '#979797',
				}
			},
		}
	],
	series : [
		{
			name:'夜行人',
			type:'bar',
			stack: '夜行人',
			data:[0,0,0,0,0,0]
		},
		{
			name:'朝九晚五',
			type:'bar',
			stack: '夜行人',
			data:[0,0,0,0,0,0]
		},
		{
			name:'晚归',
			type:'bar',
			stack: '夜行人',
			data:[0,0,0,0,0,0]
		},
		{
			name:'居家族',
			type:'bar',
			stack: '夜行人',
			data:[0,0,0,0,0,0]
		},
		{
			name:'无规律',
			type:'bar',
			stack: '夜行人',
			barWidth:'50%',
			data:[0,0,0,0,0,0]
		}
	]
};

//柱状图
var bar_option = {
	backgroundColor:'#232323',
	color: [
		"#F2AE6F"
	],
	title : {
		text: '',
		left:'20',
		top:'10',
		textStyle:{
			fontSize: '14px',
			color:'#DEDEDE',
		}
	},
	tooltip : {
		trigger: 'axis',
		axisPointer : {
			type : 'shadow'
		},
		textStyle:{
			align:'left'
		},
		formatter: ''
	},
	legend: {
		orient: 'horizontal',
		top:'10',
		right:'10',
		itemWidth: 8,
		itemHeight: 8,
		data: [''],
		textStyle:{
			color:'#DEDEDE'
		}
	},
	grid: {
		// top:'10%',
		left: '20',
		right: '10',
		bottom: '10',
		containLabel: true
	},
	xAxis: {
		type : 'category',
		splitLine: {
			show: false,
		},
		data : [''],
		// boundaryGap: false,
		axisLine: {
			show:true,
			lineStyle: {
				color: '#6D6D6D',
				style:'solid',
			},
		},
		axisLabel: {
			interval:0,
			rotate:30,
			textStyle: {
				color: '#FEFEFE',
				fontSize: '12',
			},
		},
		nameTextStyle:{
			color: '#FEFEFE',
			fontSize: '12',
		},
	},
	yAxis : [
		{
			type : 'value',
			axisLabel : {
				textStyle: {
					color: '#939393',
					fontSize: '12',
				},
				formatter: '{value}'
			},
			nameTextStyle:{
				color: '#939393',
				fontSize: '12',
			},
			splitLine: {
				show: true,
				lineStyle: {
					type: 'solid',
					color: '#6D6D6D',
				}
			},
			axisLine: {
				show: true,
				lineStyle: {
					color: '#6D6D6D',
				},
			}
		}
	],
	series : [
		{
			name:'柱状图',
			type:'bar',
			barWidth:30,
			data:[0,0,0,0,0,0]
		}
	]
};

