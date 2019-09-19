(function(root, factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as an anonymous module.
        define(['exports', 'echarts'], factory);
    } else if (typeof exports === 'object' && typeof exports.nodeName !== 'string') {
        // CommonJS
        factory(exports, require('echarts'));
    } else {
        // Browser globals
        factory({}, root.echarts);
    }
}(this, function(exports, echarts) {
    var log = function(msg) {
        if (typeof console !== 'undefined') {
            console && console.error && console.error(msg);
        }
    };
    if (!echarts) {
        log('ECharts is not Loaded');
        return;
    }
    echarts.registerTheme('qd-datacenter', {
        "color": [
            "#9C4DFF",
            "#3190FF",
            "#52FEFF",
            "#FF7C00",
            "#FF5AA9",
            "#FFF22C",
            "#30C8FB",
            "#7356FF",
            "#F2562E",
            "#07FFB5",
            "#C6FF7E",
            "#00DCD7",
        ],
        "backgroundColor": "rgba(1,6,24,0.06)",
        "textStyle": {},
        "title": {
            "textStyle": {
                "color": "#52feff"
            },
            "subtextStyle": {
                "color": "#4fb0ff"
            }
        },
        "line": {
            "itemStyle": {
                "normal": {
                    "borderWidth": "2"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "3"
                }
            },
            "symbolSize": "6",
            "symbol": "circle",
            "smooth": true
        },
        "radar": {
            "itemStyle": {
                "normal": {
                    "borderWidth": "2"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": "3"
                }
            },
            "symbolSize": "6",
            "symbol": "circle",
            "smooth": true
        },
        "bar": {
            "itemStyle": {
                "normal": {
                    "barBorderWidth": 0,
                    "barBorderColor": "#ccc"
                },
                "emphasis": {
                    "barBorderWidth": 0,
                    "barBorderColor": "#ccc"
                }
            }
        },
        "pie": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "scatter": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "boxplot": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "parallel": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "sankey": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "funnel": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "gauge": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                },
                "emphasis": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            }
        },
        "candlestick": {
            "itemStyle": {
                "normal": {
                    "color": "#ff5aa9",
                    "color0": "transparent",
                    "borderColor": "#ff5aa9",
                    "borderColor0": "#3190ff",
                    "borderWidth": "2"
                }
            }
        },
        "graph": {
            "itemStyle": {
                "normal": {
                    "borderWidth": 0,
                    "borderColor": "#ccc"
                }
            },
            "lineStyle": {
                "normal": {
                    "width": 1,
                    "color": "#757575"
                }
            },
            "symbolSize": "6",
            "symbol": "circle",
            "smooth": true,
            "color": [
                "#9c4dff",
                "#3190ff",
                "#52feff",
                "#9c4dff",
                "#ff5aa9",
                "#fff22c"
            ],
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#000000"
                    }
                }
            }
        },
        "map": {
            "itemStyle": {
                "normal": {
                    "areaColor": "rgba(82,254,255,0.13)",
                    "borderColor": "#4fb0ff",
                    "borderWidth": "0.5"
                },
                "emphasis": {
                    "areaColor": "rgba(79,176,255,0.78)",
                    "borderColor": "#52feff",
                    "borderWidth": 1
                }
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#010618"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#52feff"
                    }
                }
            }
        },
        "geo": {
            "itemStyle": {
                "normal": {
                    "areaColor": "rgba(82,254,255,0.13)",
                    "borderColor": "#4fb0ff",
                    "borderWidth": "0.5"
                },
                "emphasis": {
                    "areaColor": "rgba(79,176,255,0.78)",
                    "borderColor": "#52feff",
                    "borderWidth": 1
                }
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#010618"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#52feff"
                    }
                }
            }
        },
        "categoryAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#007db5"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#00b8d4"
                }
            },
            "splitLine": {
                "show": false,
                "lineStyle": {
                    "color": [
                        "#eeeeee",
                        "#333333"
                    ]
                }
            },
            "splitArea": {
                "show": true,
                "areaStyle": {
                    "color": [
                        "rgba(200,200,200,0.04)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "valueAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#007db5"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#00b8d4"
                }
            },
            "splitLine": {
                "show": false,
                "lineStyle": {
                    "color": [
                        "#eeeeee",
                        "#333333"
                    ]
                }
            },
            "splitArea": {
                "show": true,
                "areaStyle": {
                    "color": [
                        "rgba(200,200,200,0.04)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "logAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#007db5"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#00b8d4"
                }
            },
            "splitLine": {
                "show": false,
                "lineStyle": {
                    "color": [
                        "#eeeeee",
                        "#333333"
                    ]
                }
            },
            "splitArea": {
                "show": true,
                "areaStyle": {
                    "color": [
                        "rgba(200,200,200,0.04)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "timeAxis": {
            "axisLine": {
                "show": true,
                "lineStyle": {
                    "color": "#007db5"
                }
            },
            "axisTick": {
                "show": false,
                "lineStyle": {
                    "color": "#333"
                }
            },
            "axisLabel": {
                "show": true,
                "textStyle": {
                    "color": "#00b8d4"
                }
            },
            "splitLine": {
                "show": false,
                "lineStyle": {
                    "color": [
                        "#eeeeee",
                        "#333333"
                    ]
                }
            },
            "splitArea": {
                "show": true,
                "areaStyle": {
                    "color": [
                        "rgba(200,200,200,0.04)",
                        "rgba(200,200,200,0.02)"
                    ]
                }
            }
        },
        "toolbox": {
            "iconStyle": {
                "normal": {
                    "borderColor": "#52feff"
                },
                "emphasis": {
                    "borderColor": "#3190ff"
                }
            }
        },
        "legend": {
            "textStyle": {
                "color": "#00b8d4"
            }
        },
        "tooltip": {
            "axisPointer": {
                "lineStyle": {
                    "color": "#52feff",
                    "width": 1
                },
                "crossStyle": {
                    "color": "#52feff",
                    "width": 1
                }
            }
        },
        "timeline": {
            "lineStyle": {
                "color": "#52feff",
                "width": 1
            },
            "itemStyle": {
                "normal": {
                    "color": "#010618",
                    "borderWidth": 1
                },
                "emphasis": {
                    "color": "#4fb0ff"
                }
            },
            "controlStyle": {
                "normal": {
                    "color": "#52feff",
                    "borderColor": "#52feff",
                    "borderWidth": 0.5
                },
                "emphasis": {
                    "color": "#52feff",
                    "borderColor": "#52feff",
                    "borderWidth": 0.5
                }
            },
            "checkpointStyle": {
                "color": "#52feff",
                "borderColor": "#010618"
            },
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#00b8d4"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#00b8d4"
                    }
                }
            }
        },
        "visualMap": {
            "color": [
                "#9c4dff",
                "#3190ff",
                "#52feff"
            ]
        },
        "dataZoom": {
            "backgroundColor": "rgba(19,229,255,0.05)",
            "dataBackgroundColor": "rgba(0,184,212,0.75)",
            "fillerColor": "rgba(79,176,255,0.4)",
            "handleColor": "#52feff",
            "handleSize": "100%",
            "textStyle": {
                "color": "#52feff"
            }
        },
        "markPoint": {
            "label": {
                "normal": {
                    "textStyle": {
                        "color": "#000000"
                    }
                },
                "emphasis": {
                    "textStyle": {
                        "color": "#000000"
                    }
                }
            }
        }
    });
}));
