/**
 * 观测台option
 */

var line_option = {
	color: [
		"#FC2969",
		"#4C84FF",
	],
	title: {
		text: ''
	},
	tooltip : {
		trigger: 'axis',
		axisPointer : {            // 坐标轴指示器，坐标轴触发有效
			type : 'shadow',        // 默认为直线，可选为：'line' | 'shadow'
		},
		textStyle:{
			align:'left',
		},

	},
	legend: {
		show:true,
		data:['本期','上期'],
		align:'right',
		top:20,
		right:20,
		itemWidth: 16,
		itemHeight: 10,
		textStyle: {
			color: '#CCCACA',
			fontSize: 12,
		},
		selectedMode: true,//是否支持点击图例切换显示状态
	},
	grid: {
		left: '20px',
		right: '20px',
		bottom: '20px',
		containLabel: true
	},
	xAxis: {
		type: 'category',
		splitLine: {
			show: false,
		},
		data: [],
		axisLine: {
			lineStyle: {
				type: 'solid',
				color: '#EBF1F5',
			}
		},
		axisLabel: {
			interval:0,
			color:'#FEFEFE'
		},
		splitLine: {
			show: true,
			lineStyle: {
				type: 'solid',
				color: '#EBF1F5',
			}
		},
		axisTick: {
			show: false,
		},
	},
	yAxis: [
		{
			type: 'value',
			axisTick: {
				show: false,
			},
			axisLabel: {
				textStyle: {
					color: '#939393',
					fontSize: '12',
				},
				formatter: '{value}'
			},
			splitLine: {
				show: true,
				lineStyle: {
					type: 'solid',
					color: '#EBF1F5',
				}
			},
			axisLine: {
				show: false,
			}
		},

	],
	series: [
		{
			name:'本期',
			type:'line',
			data:[0],
		},
		{
			name:'上期',
			type:'line',
			data:[0],
		},
	]
};

var tree_option = {
    tooltip: {
        trigger: 'item',
        triggerOn: 'mousemove'
    },
    legend: {
        top: '2%',
        left: '3%',
        orient: 'vertical',
        data: [{
            name: 'tree1',
            icon: 'rectangle'
        }],
        borderColor: '#c23531'
    },
    series:[
        {
            type: 'tree',
            data: [],
            top: '5%',
            left: '8',
            bottom: '2%',
            right: '20%',
            symbolSize: 7,
            label: {
                normal: {
                    position: 'left',
                    verticalAlign: 'middle',
                    align: 'right',
                    fontSize: 12
                }
            },
            leaves: {
                label: {
                    normal: {
                        position: 'right',
                        verticalAlign: 'middle',
                        align: 'left'
                    }
                }
            },
            expandAndCollapse: true,
            initialTreeDepth:2,
            animationDuration: 550,
            animationDurationUpdate: 750
        }
    ]
};