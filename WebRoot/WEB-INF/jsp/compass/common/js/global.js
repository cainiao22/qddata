/*
 公共静态资源
 */
var GUrl = "http://devbigdata.qdingnet.com/ds";//dev
//var GUrl = "/ds";//线上用这个,不需要增加域名

/*1 :日期 2018-05-09，
  2 :2018-05，
  3 :年 2018
*/
var GDateType={
	"DAY":1,
	"MONTH":2,
	"YEAR":3
};

/*8888	全国，
  huabei	华北片区(包含北京和成都)，
  dongnan	东南片区(包含上海、杭州、广州)，
  xinan	西南片区（包含西安、重庆），
  qita	其它（非七大城市），
  具体城市ID七大城市（北京1、成都5、上海9、杭州14、广州31、西安11、重庆3）
*/
var GAreaType={
	"ALL":8888,
	"HUABEI":"huabei",
	"DONGNAN":"dongnan",
	"XINAN":"xinan",
	"OTHER":"qita",
	"BJ":1,
	"CQ":3,
	"CD":5,
	"SH":9,
	"XA":11,
	"HZ":14,
	"GZ":31
};
