$(function() {
	var EchartsOptionUtil=function(){
		this.getOption=function getOption(title,yAxisName,xdata,ydata){
			return  {
				    title : {
				        text: title,
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    grid:{
				    	x:100
				    },
				    dataZoom : {
				        show : true,
				        realtime : false
				      
				    },
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : true,
				            data : xdata
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            scale: true,
				            power:1,
				            boundaryGap:[0.1,0.1],
				            name : yAxisName,
				            min:0,
				            splitNumber:10
				        }
				    ],
				    series : [
				        {
				            name:yAxisName,
				            type:'line',
				            itemStyle: {
				                normal: {
				                    // areaStyle: {type: 'default'},
				                    lineStyle: {
				                        shadowColor : 'rgba(0,0,0,0.4)'
				                    }
				                }
				            },
				            data:ydata
				        }
				    ]
				};
		 
		};
		this.getProjectChartOption=function getProjectChartOption(title, yLeftAxisName,yRightAxisName,yThirdAxisName,xData,yLeftData,yRightData,yThirdData){
			return  {
			    title : {
			        text: title,
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'axis'
			    },
			    legend: {
			        data:[yLeftAxisName,yRightAxisName,yThirdAxisName],
			        x:'right'
			    },
			    dataZoom : {
			        show : true,
			        realtime : false
			      
			    },
			    xAxis : [
			        {
			            type : 'category',
			            boundaryGap : true,
			            data : xData
			        }
			    ],
			    yAxis : [
			        {
			            type : 'value',
			            name : yLeftAxisName,
			            scale: true,
			            power:1,
			            boundaryGap:[0.1,0.1],
			            position:'left',
			            min:0,
			            splitNumber:10
			        },
			        {
			            type : 'value',
			            name : yRightAxisName,
			            scale: true,
			            power:1,
			            boundaryGap:[0.1,0.6],
			            position:'right'
			        }
			    ],
			    series : [
			        {
			            name:yLeftAxisName,
			            type:'line',
			            itemStyle: {
			                normal: {
			                    // areaStyle: {type: 'default'},
			                    lineStyle: {
			                        shadowColor : 'rgba(0,0,0,0.4)'
			                    }
			                }
			            },
			            data:yLeftData
			        },
			        {
			            name:yRightAxisName,
			            type:'line',
			            data:yRightData,
			            yAxisIndex:1
			        },
			        {
			            name:yThirdAxisName,
			            type:'line',
			          
			            data:yThirdData
			        }
			    ]
			};
		};
		this.getAlbumChartOption=function getAlbumChartOption(title, yAxisName,y1Name,y2Name,xData,y1Data,y2Data){
			return  {
				title : {
					text: title,
					x:'center'
				},
				tooltip : {
					trigger: 'axis'
				},
				legend: {
					data:[y1Name,y2Name],
					x:'right'
				},
				 dataZoom : {
				        show : true,
				        realtime : false
				      
				    },
				xAxis : [
				         {
				        	 type : 'category',
				        	 boundaryGap : true,
				        	 data : xData
				         }
				         ],
				         yAxis : [
				                  {
				                	  type : 'value',
				                	  name : yAxisName,
				                	  scale: true,
				                	  power:1,
				                	  boundaryGap:[0.1,0.1],
				                	  min:0,
				                	  splitNumber:10
				                  }
				                  ],
				                  series : [
				                            {
				                            	name:y1Name,
				                            	type:'line',
				                            	itemStyle: {
				                            		normal: {
				                            			lineStyle: {
				                            				shadowColor : 'rgba(0,0,0,0.4)'
				                            			}
				                            		}
				                            	},
				                            	data:y1Data
				                            },
				                            {
				                            	name:y2Name,
				                            	type:'line',
				                            	itemStyle: {
				                            		normal: {
				                            			lineStyle: {
				                            				shadowColor : 'rgba(0,0,0,0.4)'
				                            			}
				                            		}
				                            	},
				                            	data:y2Data
				                            }
				                            ]
			};
		};
		//带图例的多条曲线
		this.getLegenOption=function getLegenOption(title,legenddata, yAxisName,xdata,ydata){
			
			return  {
				    title : {
				        text: title,
				        x:'center'
				    },
				    tooltip : {
				        trigger: 'axis'
				    },
				    legend: {
				        data:legenddata,
				        x:'left' 
				    }, dataZoom : {
				        show : true,
				        realtime : false
				      
				    },
				    dataZoom : {
				        show : false,
				        realtime: true,
				        start : 50,
				        end : 100
				    },
				    xAxis : [
				        {
				            type : 'category',
				            boundaryGap : true,
				            data : xdata
				        }
				    ],
				    yAxis : [
				        {
				            type : 'value',
				            scale: true,
				            precision:1,
				            power:1,
				            name : yAxisName,
				            boundaryGap: [0.2, 0.2],
				            splitArea : {show : true},
				            min:0,
				            splitNumber:10
				        }
				    ],
				    series : [
                      {
				            name:legenddata[0],
				            type:'line',
				            itemStyle: {
				                normal: {
				                    // areaStyle: {type: 'default'},
				                    lineStyle: {
				                        shadowColor : 'rgba(0,0,0,0.4)'
				                    }
				                }
				            },
				            data:ydata[0]
				        },
				        {
				            name:legenddata[1],
				            type:'line',
				            itemStyle: {
				                normal: {
				                    // areaStyle: {type: 'default'},
				                    lineStyle: {
				                        shadowColor : 'rgba(135,206,205,0.4)'
				                    }
				                }
				            },
				            data:ydata[1]
				        }
				    ]
				};
				 
		};

		this.showLoading=function(chart){
			chart.showLoading({
				    text: '读取数据中...',
				    effect:"whirling"
				});
		};
	};
	var echartsOptionUtil=new EchartsOptionUtil();
	window.echartsOptionUtil=echartsOptionUtil;
	
	
});