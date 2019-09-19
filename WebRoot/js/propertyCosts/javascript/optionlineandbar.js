export default {
	optionlinecharts: {
	    backgroundColor: 'rgba(34,28,54,.8)',
	    color: ['#804eb1', '#32a093'],
	    title: {
	        text: '',
	        textStyle: {
	            color: '#ffffff',
	            fontWeight: 100,
	            fontSize: 16,
	        },
	        left: 20,
	        top: 20
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            dataZoom: {
	                yAxisIndex: 'none'
	            },
	            dataView: {readOnly: false},
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        },
	        right: 26,
	        top: 60,
	        iconStyle: {
	            borderColor: 'rgba(255,255,255,.8)'
	        }
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'line',
	            label: {
	                backgroundColor: '#6a7985'
	            }
	        }
	    },
	    legend: {
	        data:['',''],
	        top: 63,
	        textStyle: {
	            color: '#ffffff'
	        }
	    },
	    grid: {
	        left: '3%',
	        right: 85,
	        bottom: '3%',
	        top: 110,
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            boundaryGap : false,
	            data : [''],
	            nameTextStyle: {
	                color: '#ffffff'
	            },
	            axisLine: {
	                lineStyle: {
	                    color: '#ffffff'
	                }
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            nameTextStyle: {
	                color: '#ffffff'
	            },
	            axisLine: {
	                lineStyle: {
	                    color: '#ffffff'
	                }
				},
				axisLabel: {
					formatter: ''
				}
	        }
	    ],
	    series : [
	        {
	            name:'',
	            type:'line',
	            areaStyle: {
	                color: '#804eb1'
	            },
	            data:[0]
	        },
	        {
	            name:'',
	            type:'line',
	            areaStyle: {
	                color: '#32a093'
	            },
	            data:[0]
	        },
	    ]
	},
	optionbarcharts: {
	    backgroundColor: 'rgba(34,28,54,.8)',
	    color: ['#33d7b5'],
	    title: {
	        text: '',
	        textStyle: {
	            color: '#ffffff',
	            fontWeight: 100,
	            fontSize: 16,
	        },
	        left: 20,
	        top: 20
	    },
	    toolbox: {
	        show: true,
	        feature: {
	            dataZoom: {
	                yAxisIndex: 'none'
	            },
	            dataView: {readOnly: false},
	            magicType: {type: ['line', 'bar']},
	            restore: {},
	            saveAsImage: {}
	        },
	        right: 26,
	        top: 60,
	        iconStyle: {
	            borderColor: 'rgba(255,255,255,.8)'
	        }
	    },
	    tooltip : {
	        trigger: 'axis',
	        axisPointer: {
	            type: 'shadow',
	            label: {
	                backgroundColor: '#6a7985'
	            }
	        }
	    },
	    grid: {
	        left: '3%',
	        right: 85,
	        bottom: '3%',
	        top: 110,
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'category',
	            data : [''],
	            nameTextStyle: {
	                color: '#ffffff'
	            },
	            axisLine: {
	                lineStyle: {
	                    color: '#ffffff'
	                }
	            }
	        }
	    ],
	    yAxis : [
	        {
	            type : 'value',
	            nameTextStyle: {
	                color: '#ffffff'
	            },
	            axisLine: {
	                lineStyle: {
	                    color: '#ffffff'
	                }
				},
				axisLabel: {
					formatter: ''
				}
	        }
	    ],
	    series : [
	        {
	            name:'',
	            type:'bar',
	            data:[0],
	            markPoint : {
	                data : [
	                    {type : 'max', name: '最大值'},
	                    {type : 'min', name: '最小值'}
	                ]
	            },
	            markLine : {
	                data : [
	                    {type : 'average', name: '平均值'}
	                ]
	            }
	        }
	    ]
	}
}